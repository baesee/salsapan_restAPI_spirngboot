package com.billlog.rest.salsapan.controller.v1;

import com.billlog.rest.salsapan.advice.exception.CUserDeleteException;
import com.billlog.rest.salsapan.advice.exception.CUserModifyException;
import com.billlog.rest.salsapan.advice.exception.CUserNotFoundException;
import com.billlog.rest.salsapan.config.security.JwtTokenProvider;
import com.billlog.rest.salsapan.controller.common.FileUploadController;
import com.billlog.rest.salsapan.mapper.UserMapper;
import com.billlog.rest.salsapan.model.SalsaUser;
import com.billlog.rest.salsapan.model.response.CommonResult;
import com.billlog.rest.salsapan.model.response.ListResult;
import com.billlog.rest.salsapan.model.response.SingleResult;
import com.billlog.rest.salsapan.service.ResponseService;
import com.billlog.rest.salsapan.util.CustomUtils;
import com.billlog.rest.salsapan.util.MailHandler;
import com.billlog.rest.salsapan.util.TempKey;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Api(tags = {"2. User"})
@RestController
@RequestMapping(value = "/v1")
public class UserController {

    @Value("${spring.mail.username}")
    private String official_email;
    @Value("${spring.url.base}")
    private String baseUrl;

    @Autowired
    private UserMapper userMapper;
    private final UserDetailsService userDetailsService;
    @Autowired
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    private final ResponseService responseService; // 결과를 처리할 Service
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender javaMailSender;

    private final FileUploadController fileUploadController; // 결과를 처리할 Service

    public UserController(UserDetailsService userDetailsService, JwtTokenProvider jwtTokenProvider, ResponseService responseService, PasswordEncoder passwordEncoder, FileUploadController fileUploadController) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.responseService = responseService;
        this.passwordEncoder = passwordEncoder;
        this.fileUploadController = fileUploadController;
    }

    //클라이언트에서(현, 안드로이드) 로그인한 사용자가 헤더에 로그인 토큰(JWT)값을 담아 사용자 본인정보 객체를 받기위한 메소드
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "헤더값으로 사용자 정보 얻기", notes = "헤더에 담겨오는 토큰값을 이용하여 로그인한 사용자의 정보를 가져온다.")
    @GetMapping(value = "/user/requestMe")
    public SingleResult<SalsaUser> requestMeByToken(@RequestHeader("X-AUTH-TOKEN") String token)  {

        SalsaUser loginUser = (SalsaUser) userDetailsService.loadUserByUsername(jwtTokenProvider.getUserPk(token));

        if(CustomUtils.isEmpty(loginUser)){
            throw new CUserNotFoundException();
        }else{
            return responseService.getSingleResult(loginUser);
        }
    }

    //유효한 Jwt토큰을 설정해야만 User 리소스를 사용할 수 있도록 Header에 X-AUTH-TOKEN을 인자로 받도록 선언합니다.
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 전체 목록 조회", notes = "모든 회원을 조회한다")
    @GetMapping(value = "/users")
    public ListResult<SalsaUser> findAllUser() {
        return responseService.getListResult(userMapper.getAll());
    }

    //유효한 Jwt토큰을 설정해야만 User 리소스를 사용할 수 있도록 Header에 X-AUTH-TOKEN을 인자로 받도록 선언합니다.
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원정보 조회", notes = "user_idx값을 이용해 회원정보를 조회한다.")
    @GetMapping(value = "/user/{user_idx}")
    public SingleResult<SalsaUser> findUserByIdx(@ApiParam(value = "회원IDX", required = true)@PathVariable int user_idx) {
        SalsaUser user = userMapper.getUserById(user_idx);

        if(CustomUtils.isEmpty(user)){
            throw new CUserNotFoundException();
        }else{
            return responseService.getSingleResult(user);
        }
    }

    // 유저 개인 정보 변경
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 정보 수정", notes = "개인정보를 수정한다.")
    @PutMapping("/user/{user_idx}")
    public CommonResult modifyUserById(@ApiParam(value = "회원IDX", required = true)@PathVariable int user_idx,
                                       @ApiParam(value = "수정할 회원정보 Object", required = true) SalsaUser user,
                                       @RequestParam(value="files", required = false) MultipartFile[] files) {

        System.err.println("mmmmmmmmmmmmmmmm!!!");
        //유저 인덱스 번호(유저 고유정보)가 없을 경우 유저를 찾을수 없다는 에러 발생
        if("".equals(user.getUser_idx())){
            throw new CUserNotFoundException();
        }

        int result = userMapper.modifyUserById(user);

        fileUploadController.uploadMultipleFiles(files, user_idx);

        if(result == 1) {
            return responseService.getSuccessResult();
        }else{
            throw new CUserModifyException();
        }
    }

    // 유저 index로 유저 삭제(user_yn = 'N')
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "유저 삭제", notes = "유저정보 삭제(사용여부 변경)")
    @DeleteMapping("/user/{user_idx}")
    public CommonResult deleteSalsaUserById(@ApiParam(value = "회원IDX", required = true)@PathVariable int user_idx) {

        //유저 인덱스 번호(유저 고유정보)가 없을 경우 유저를 찾을수 없다는 에러 발생
        if("".equals(user_idx)){
            throw new CUserNotFoundException();
        }

        Boolean result = userMapper.deleteUserById(user_idx);

        if(result) {
            return responseService.getSuccessResult();
        }else{
            throw new CUserDeleteException();
        }
    }

    // 패스워드 변경
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "패스워드 변경", notes = "패스워드 변경")
    @PutMapping("/user/pwd/{user_idx}")
    public CommonResult modifyPasswordByIdx(@ApiParam(value = "회원IDX", required = true)@PathVariable int user_idx,
                                            @ApiParam(value = "기존 사용하던 패스워드", required = true) @RequestParam String current_pwd,
                                            @ApiParam(value = "새로운 패스워드", required = true) @RequestParam String new_pwd,
                                            @ApiParam(value = "새로운 패스워드 확인", required = true) @RequestParam String confirm_pwd) {

        SalsaUser user = userMapper.getUserById(user_idx);

        int chkVal = 0;

        if(CustomUtils.isEmpty(user)){
            throw new CUserNotFoundException();
        }else{
            if (passwordEncoder.matches(current_pwd, user.getUser_pwd())) { //기존 패스워드와 일치하는 경우.
                if (new_pwd.equals(confirm_pwd)) { // 변경할 패스워드와 Confirm 패스워드가 일치할 경우.
                    new_pwd = passwordEncoder.encode(new_pwd);
                    chkVal = userMapper.modifyPasswordByIdx(user.getUser_idx(), new_pwd);
                }else{
                    //변경할 패스워드와 Confirm 패스워드가 일치하지 않는 경우.
                    return responseService.getFailResult(99007,"변경하려는 패스워드가 서로 다릅니다.");
                }
            }else{
                //기존 패스워드와 입력한 현재 패스워드 다를 경우.
                return responseService.getFailResult(99008,"기존 패스워드가 다릅니다.");
            }

            if(chkVal > 0){
                return responseService.getSuccessResult();
            }else{
                return responseService.getFailResult(99008,"패스워드 변경중 오류가 발생하였습니다.");
            }
        }
    }


    /**
     *  이메일 인증 관련 시작
     */

    // 로그인한 회원 > 사용자 정보 > 이메일 본인인증 : 회원의 이메일로 본인인증 관련 키가 전송된다.
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "이메일 본인 인증", notes = "이메일인증 확인 절차 진행")
    @PostMapping(value = "/user/emailAuth")
    public CommonResult eamilAuth(@ApiParam(value = "회원 id", required = true) @RequestParam int user_idx,
                                  @ApiParam(value = "회원 email", required = true) @RequestParam String user_email) throws Exception {

        TempKey tempKey = new TempKey();

        String authKey = "";
        boolean chkVal = false;

        SalsaUser user = userMapper.getUserById(user_idx);

        if(user.getUser_email().equals(user_email)){
            authKey = tempKey.getKey(50, false);

            // 1. 사용자의 idx 값을 받아 사용자 인증키를 업데이트 한다.
            chkVal = userMapper.setEamilAuthTempKey(user_idx, authKey);

            if (chkVal) {
                // 2. 사용자의 이메일로 인증키 요정메일을 전송한다.
                MailHandler sendMail = new MailHandler(javaMailSender);
                sendMail.setSubject("[SALSAPAN 이메일 인증입니다.]");
                sendMail.setText(new StringBuffer().append("<h1>SALSAPAN 메일인증 서비스</h1>")
                        .append("<h3>안녕하세요. SALSAPAN 입니다. 회원님의 이메일인증 확인을 위하여 발송된 메일입니다.</h3>")
                        .append("<h3>이메일 인증을 하시려면 아래 링크를 클릭해주시기 바랍니다.</h3>")
                        .append("<a href=' ")
                        .append(baseUrl)
                        .append("/v1/user/emailConfirm?user_email=")
                        .append(user_email)
                        .append("&key=")
                        .append(authKey)
                        .append("&user_idx=")
                        .append(user_idx)
                        .append(" ' target='_blenk'>이메일 인증 확인</a>")
                        .toString());
                sendMail.setFrom(official_email, "salsapan");
                sendMail.setTo(user_email);
                sendMail.send();

                return responseService.getSuccessResult();
            } else {
                return responseService.getFailResult(99003, "이메일 인증 관련 작업중 에러가 발생하였습니다.");
            }
        }else{
            return responseService.getFailResult(99004, "사용자 정보의 이메일과 입력된 이메일 정보가 일치하지 않습니다.");
        }
    }

    @ApiOperation(value = "이메일 인증 확인", notes = "이메일로 전송된 키값을 확인한다.")
    @GetMapping(value = "/user/emailConfirm")
    public void eamilAuthConfirm(HttpServletResponse response,
                                 @ApiParam(value = "회원 id", required = true) @RequestParam String user_idx,
                                 @ApiParam(value = "메일 인증 key", required = true) @RequestParam String key) throws IOException {

        int chkVal = 0;
        int result = 0;

        // 1. 메일 인증 확인
        chkVal = userMapper.setEamilAuthConfirm(Integer.parseInt(user_idx), key.trim());

        // 2. 해당유저(user_idx)의 파라미터로 받은 key 값과 DB에 저장되어 있는 user_auth_key 의 같아서 chkVal == 1 일 경우 : 인증완료
        if(chkVal == 1) {
            SalsaUser user = new SalsaUser();
            user.setUser_idx(Integer.parseInt(user_idx));
            user.setUser_auth("Y");

            result = userMapper.modifyUserById(user);
        }

        if (result == 1) {
            response.sendRedirect("/web/common/emailConfirm?autyType=Y");
        } else {
            response.sendRedirect("/web/common/emailConfirm?autyType=N");
        }
    }

}


package com.billlog.rest.salsapan.controller.v1;

import com.billlog.rest.salsapan.advice.exception.*;
import com.billlog.rest.salsapan.config.security.JwtTokenProvider;
import com.billlog.rest.salsapan.mapper.UserMapper;
import com.billlog.rest.salsapan.model.KakaoProfile;
import com.billlog.rest.salsapan.model.SalsaUser;
import com.billlog.rest.salsapan.model.response.CommonResult;
import com.billlog.rest.salsapan.model.response.SingleResult;
import com.billlog.rest.salsapan.service.KakaoService;
import com.billlog.rest.salsapan.service.ResponseService;
import com.billlog.rest.salsapan.util.CustomUtils;
import com.billlog.rest.salsapan.util.MailHandler;
import com.billlog.rest.salsapan.util.TempKey;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Api(tags = {"1. Sign"})
@RestController
@RequestMapping(value = "/v1")
public class SignController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final KakaoService kakaoService;

    @Autowired
    private JavaMailSender javaMailSender;

    public SignController(JwtTokenProvider jwtTokenProvider, ResponseService responseService, PasswordEncoder passwordEncoder, KakaoService kakaoService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.responseService = responseService;
        this.passwordEncoder = passwordEncoder;
        this.kakaoService = kakaoService;
    }

    @ApiOperation(value = "로그인", notes = "사용자 ID 로그인을 한다.")
    @PostMapping(value = "/signin")
    public SingleResult<String> signin(@ApiParam(value = "회원ID", required = true) @RequestParam String id,
                                       @ApiParam(value = "비밀번호", required = true) @RequestParam String password) {

        SalsaUser user = userMapper.findByUsername(id);
        if (!passwordEncoder.matches(password, user.getUser_pwd()))
            throw new CEmailSigninFailedException();

        return responseService.getSingleResult(jwtTokenProvider.createToken(user.getUser_id(), user.getUser_role_nm()));
    }

    @ApiOperation(value = "ID중복확인", notes = "회원가입시 아이디 중복확인 처리")
    @GetMapping(value = "/signup/check")
    public CommonResult signupIdCheck(@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam String id) {


        // chkVal이 0 보다 클 경우 동일한 회원아이디가 있다는 뜻.
        int chkVal = userMapper.userIdCheck(id);

        if(chkVal > 0){
            return responseService.getFailResult(99001, "사용불가능한 아이디 입니다.");
        }else{
            return responseService.getSuccessResult();
        }
    }

    @ApiOperation(value = "E-mail 중복확인", notes = "회원가입시 사용되는 이메일 중복확인 처리")
    @GetMapping(value = "/signup/echeck")
    public CommonResult signupEmailCheck(@ApiParam(value = "E-mail 주소", required = true) @RequestParam String user_email) {

        // chkVal이 0 보다 클 경우 동일한 회원아이디가 있다는 뜻.
        int chkVal = userMapper.userEmailCheck(user_email);

        if(chkVal > 0){
            return responseService.getFailResult(99002, "사용불가능한 이메일 입니다.");
        }else{
            return responseService.getSuccessResult();
        }

    }

    @ApiOperation(value = "이름(닉네임) 중복확인", notes = "회원가입시 이름(닉네임) 중복확인 처리")
    @GetMapping(value = "/signup/ncheck")
    public CommonResult signupNameCheck(@ApiParam(value = "이름(닉네임)", required = true) @RequestParam String user_name) {


        // chkVal이 0 보다 클 경우 동일한 회원아이디가 있다는 뜻.
        int chkVal = userMapper.userNameCheck(user_name);

        if(chkVal > 0){
            return responseService.getFailResult(99001, "사용불가능한 이름(닉네임) 입니다.");
        }else{
            return responseService.getSuccessResult();
        }
    }

    @ApiOperation(value = "ID 찾기", notes = "회원 아이디 찾기")
    @GetMapping(value = "/signup/find/id")
    public SingleResult<String> signupFindId(@ApiParam(value = "회원 이름(닉네임)", required = true) @RequestParam String user_name,
                                     @ApiParam(value = "회원 E-mail") @RequestParam String user_email) {

        String findId = "";
        findId = userMapper.findUserId(user_name, user_email);

        if( !"".equals(findId) && findId != null ){

            //입력된 사용자 이메일로 아이디 전송
            sendEmail("id",user_email,findId);

            return responseService.getSingleResult(findId);
        }else{
            throw new CUserNotFoundException();
        }
    }

    @ApiOperation(value = "패스워드 찾기", notes = "회원 패스워드 찾기")
    @GetMapping(value = "/signup/find/pwd")
    public SingleResult<String> signupFindId(@ApiParam(value = "회원 ID", required = true) @RequestParam String user_id,
                                     @ApiParam(value = "회원 이름(닉네임)", required = true) @RequestParam String user_name,
                                     @ApiParam(value = "회원 E-mail", required = true) @RequestParam String user_email) {

        String findPwd = "";

        SalsaUser userFindById = userMapper.findByUsername(user_id);

        if(!CustomUtils.isEmpty(userFindById)){
            if(!userFindById.getUser_name().equals(user_name)){
                //해당 사용자의 계정은 올바르나 닉네임이 다를 경우
                throw new CUserNicknameNotFoundException();
            }else if(!userFindById.getUser_email().equals(user_email)){
                //해당 사용자의 계정은 올바르나 이메일이 다를 경우
                throw new CUserEmailNotFoundException();
            }else{
                findPwd = userMapper.findUserPwd(user_id, user_name, user_email);

                if(!"".equals(findPwd) && findPwd != null ){

                    SalsaUser user = new SalsaUser();

                    String uuid = UUID.randomUUID().toString().replaceAll("-","");
                    uuid = uuid.substring(0,12); // 12자리 임시비밀번호.

                    user.setUser_id(user_id);
                    user.setUser_name(user_name);
                    user.setUser_email(user_email);
                    user.setUser_pwd(passwordEncoder.encode(uuid));

                    int tmpPwdModifyCheck = userMapper.modifyUserByIdNameEmail(user);

                    if( tmpPwdModifyCheck > 0 ) {
                        sendEmail("pwd", user_email, uuid);

                        return responseService.getSingleResult(uuid);
                    }else{
                        /**
                         * 이건 패스워드 변경 실패하였다 로 변경해야함.
                         */
                        throw new CUserPasswordModException();
                    }
                }else{
                    throw new CUserNotFoundException();
                }
            }
        }else{
            throw new CUserNotFoundException();
        }
    }

    @ApiOperation(value = "회원가입", notes = "회원가입을 한다.")
    @PostMapping(value = "/signup")
    public CommonResult signin(@ApiParam(value = "회원ID", required = true) @RequestParam String id,
                               @ApiParam(value = "비밀번호", required = true) @RequestParam String password,
                               @ApiParam(value = "이름(닉네임)", required = true) @RequestParam String name,
                               @ApiParam(value = "이메일", required = true) @RequestParam String email,
                               @ApiParam(value = "성별", required = true) @RequestParam String gender,
                               @ApiParam(value = "활동지역") @RequestParam(value="city", required=false) int city,
                               @ApiParam(value = "팀명") @RequestParam(value="team", required=false) String team,
                               @ApiParam(value = "서비스 제공자") @RequestParam(value="provider", required=false) String provider) {

        SalsaUser salsaUser = new SalsaUser();
        salsaUser.setUser_id( id );
        salsaUser.setUser_name(name);
        salsaUser.setUser_pwd( passwordEncoder.encode(password) );
        salsaUser.setUser_email( email );
        salsaUser.setUser_gender( gender ); //F or M
        salsaUser.setUser_city( city ); // Only Number
        salsaUser.setUser_team( ("".equals(team) == true) ? null : team ); // String
        salsaUser.setUser_role( 3 ); // Default : 3 ( ROLE _USER )

        if(!"".equals(provider) && provider != null) {
            salsaUser.setProvider(provider);
        }


        userMapper.createUser(salsaUser);

        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "소셜 로그인", notes = "소셜 회원 로그인을 한다.")
    @PostMapping(value = "/signin/soical")
    public SingleResult<String> signinByProvider(
            @ApiParam(value = "소셜 고유 유저ID", required = true) @RequestParam String user_id) {

        SalsaUser user = userMapper.findByUsername(user_id);

        if(!CustomUtils.isEmpty(user)){
            return responseService.getSingleResult(jwtTokenProvider.createToken(user.getUser_id(), user.getUser_role_nm()));
        }else{
            throw new CUserNotFoundException();
        }
    }


    /*
    카카오 accessToken과 이름을 받아 가입하는 기능을 추가합니다.
    accessToken 외에 가입시 추가로 필요한 정보가 있으면 파라미터로 받으면 되며 이때 카카오 프로필 정보의 닉네임,
    프로필 이미지등 정보를 이용하여 가입 정보를 채우는것도 한 방법입니다.
     */
    @ApiOperation(value = "소셜 계정 가입", notes = "소셜 계정 회원가입을 한다.")
    @PostMapping(value = "/signup/{provider}")
    public CommonResult signupProvider(@ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao") @PathVariable String provider,
                                       @ApiParam(value = "소셜 access_token", required = true) @RequestParam String accessToken,
                                       @ApiParam(value = "이름(닉네임)", required = true) @RequestParam String name) {

        KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
        Optional<SalsaUser> user = Optional.ofNullable(userMapper.findByUidAndProvider(String.valueOf(profile.getId()), provider));


        if(user.isPresent()) {
            throw new CUserExistException();
        }else {
            /*
            SalsaUser salsaUser = new SalsaUser();
            salsaUser.setUser_id(id);
            salsaUser.setUser_name(name);
            salsaUser.setUser_pwd(passwordEncoder.encode(password));
            salsaUser.setUser_gender("F");
            salsaUser.setUser_role(3); //ROLE_USER
            userMapper.createUser(salsaUser);

            userJpaRepo.save(User.builder()
                    .uid(String.valueOf(profile.getId()))
                    .provider(provider)
                    .name(name)
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build());
             */
        }
        return responseService.getSuccessResult();
    }

    // 클라이언트에 카카오로 로그인 한 후 카카오 고유 ID 의 값이 DB에 값이 존재 하는지 확인한다.
    @ApiOperation(value = "카카오 고유 ID 중복확인", notes = "카카오 고유 ID가 존재하는지 확인한다.")
    @GetMapping(value = "/signup/providerIdCheck")
    public CommonResult kakaoIdCheck(@ApiParam(value = "카카오 고유ID", required = true) @RequestParam String kakao_id,
                                     @ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao") @RequestParam String provider) {


        // chkVal이 0 보다 클 경우 동일한 회원아이디가 있다는 뜻.
        int chkVal = userMapper.kakaoIdCheck(kakao_id, provider);

        if(chkVal > 0){
            return responseService.getFailResult(99901, "이미 가입된 카카오ID 입니다.");
        }else{
            return responseService.getSuccessResult();
        }
    }


    /**
     * Gmail SMTP 를 이용한 이메일 전송.
     * 아이디 찾기, 패스워드 찾기에서 사용자의 이메일 값을 받아 사용자에게 찾은 정보를 전송한다.
     * @param type
     * @param to
     * @param findValue
     */
    public void sendEmail(String type, String to, String findValue) {

        SimpleMailMessage msg = new SimpleMailMessage();

        String subject = "[살사판] ";
        String text = "\n\n 안녕하세요. 살사판입니다. \n\n 회원님의 ";

        if("id".equals(type)){
            subject += "아이디 찾기 입니다.";
            text += "아이디는 [ " + findValue + " ] 입니다. \n\n 이용해 주셔서 감사합니다.";

        }else if("pwd".equals(type)){
            subject += "패스워드 찾기 입니다.";
            text += "임시비밀번호는  [ " + findValue + " ] 입니다. \n\n 로그인하여 패스워드 변경을 권장드립니다. \n\n 이용해 주셔서 감사합니다.";
        }else{
            subject = "[살사판] 안녕하세요 살사판입니다.";
            text = "아이디 / 패스워드 찾기 중 예기치 못한 오류가 발생되었습니다. \n\n 살사판으로 직접 전화문의 부탁드리겠습니다. \n\n 감사합니다.";
        }

        msg.setTo(to);

        msg.setSubject(subject);
        msg.setText(text);

        javaMailSender.send(msg);
    }
}
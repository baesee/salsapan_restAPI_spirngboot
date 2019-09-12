package com.billlog.rest.salsapan.controller.v1;

import com.billlog.rest.salsapan.advice.exception.CCommonDeleteFailedException;
import com.billlog.rest.salsapan.advice.exception.CCommonUpdateFailedException;
import com.billlog.rest.salsapan.advice.exception.CCommonWriteFailedException;
import com.billlog.rest.salsapan.advice.exception.CInfoArticleNotFoundException;
import com.billlog.rest.salsapan.controller.common.FileUploadController;
import com.billlog.rest.salsapan.mapper.InfoMapper;
import com.billlog.rest.salsapan.model.SalsaInfo;
import com.billlog.rest.salsapan.model.file.FileUploadResponse;
import com.billlog.rest.salsapan.model.response.CommonResult;
import com.billlog.rest.salsapan.model.response.ListResult;
import com.billlog.rest.salsapan.model.response.SingleResult;
import com.billlog.rest.salsapan.service.ResponseService;
import com.billlog.rest.salsapan.util.CustomUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = {"3. SalsaInfo"})
@RestController
@RequestMapping(value = "/v1")
public class SalsaInfoController {

    @Autowired
    private InfoMapper infoMapper;
    @Autowired
    private final ResponseService responseService; // 결과를 처리할 Service
    private final FileUploadController fileUploadController; // 결과를 처리할 Service

    public SalsaInfoController(ResponseService responseService, FileUploadController fileUploadController) {
        this.responseService = responseService;
        this.fileUploadController = fileUploadController;
    }

    //유효한 Jwt토큰을 설정해야만 User 리소스를 사용할 수 있도록 Header에 X-AUTH-TOKEN을 인자로 받도록 선언합니다.
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "정보성 게시글 전체 목록 조회", notes = "class, party, club 구분없이 모든 목록을 조회온다.")
    @GetMapping("/infos/{type}")
    public ListResult<SalsaInfo> getAll(@ApiParam(value = "정보성글의 타입", required = true)@PathVariable String type,
                                        @ApiParam(value = "페이지 번호", required = true)@RequestParam int page,
                                        @ApiParam(value = "표시 글의 개수", required = true)@RequestParam int count) {

        //MariaDB LIMIT 페이지 계산식
        page = (page - 1) * count;

        return responseService.getListResult(infoMapper.getInfoArticleAll(type, page, count));
    }

    //유효한 Jwt토큰을 설정해야만 User 리소스를 사용할 수 있도록 Header에 X-AUTH-TOKEN을 인자로 받도록 선언합니다.
    @ApiImplicitParams({
//            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", dataType = "String", paramType = "header")
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "정보게시글 조회", notes = "info_idx를 이용해 정보 게시글를 조회한다.")
    @GetMapping("/info/{info_idx}")
    public SingleResult<SalsaInfo> getInfoArticleByIdx(@ApiParam(value = "정보게시글 IDX", required = true)@PathVariable int info_idx,
                                                       @ApiParam(value = "정보게시글 타입", required = true)@RequestParam String type) {

        SalsaInfo info = infoMapper.getInfoArticleByIdx(info_idx, type);

        if(CustomUtils.isEmpty(info)){
            throw new CInfoArticleNotFoundException();
        }else{
            return responseService.getSingleResult(info);
        }
    }

    // info 정보성 게시글 등록
    //유효한 Jwt토큰을 설정해야만 User 리소스를 사용할 수 있도록 Header에 X-AUTH-TOKEN을 인자로 받도록 선언합니다.
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "Content-Type", value = "폼데이터 속성", required = true, dataType = "String", paramType = "header", defaultValue = "multipart/form-data")
    })
    @ApiOperation(value = "정보게시글 등록", notes = "정보성 게시물을 작성한다.")
    @PostMapping("/info")
    public CommonResult createInfoArticle(@ApiParam(value = "정보게시글 작성 Object", required = true) SalsaInfo salsaInfo,
                                          @RequestPart(value="files", required = false) MultipartFile[] files,
                                          @ApiParam(value = "저장 디렉토리 명", required = true) String dir){
    /*
    public CommonResult createInfoArticle(@ApiParam(value = "정보게시글 작성 Object", required = true) SalsaInfo salsaInfo,
                                          @RequestParam(value="files", required = false) MultipartFile[] files){
    */

        int result = 0;

        if(!CustomUtils.isEmpty(salsaInfo)) {

            if(!CustomUtils.isEmpty(files)) {
                int file_manage_id = fileUploadController.returnFileManageId(0); // 새롭게 등록이 되는 경우 0
                fileUploadController.uploadMultipleFiles(files, dir, file_manage_id);
            }

            result = infoMapper.createInfoArticle(salsaInfo);
        }

        if(result == 1) {
            return responseService.getSuccessResult();
        }else{
            throw new CCommonWriteFailedException();
        }
    }

    //정보게시글 idx를 통해 해당 정보 게시글 내용 변경
    //유효한 Jwt토큰을 설정해야만 User 리소스를 사용할 수 있도록 Header에 X-AUTH-TOKEN을 인자로 받도록 선언합니다.
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "정보게시글 수정", notes = "정보성 게시물 수정한다.")
    @PutMapping("/info/{info_idx}")
    public CommonResult modifyInfoArticleByIdx(@ApiParam(value = "정보게시글 IDX", required = true)@PathVariable int info_idx,
                                               @ApiParam(value = "정보게시글 작성 Object", required = true) SalsaInfo salsaInfo){

        //정보게시글 인덱스 번호가 없을 경우 메시지와 함께 예외처리
        if("".equals(salsaInfo.getInfo_idx())){
            throw new CInfoArticleNotFoundException();
        }

        int result = infoMapper.modifyInfoArticleByIdx(salsaInfo);

        if(result == 1) {
            return responseService.getSuccessResult();
        }else{
            throw new CCommonUpdateFailedException();
        }
    }

    // info index로 info 게시글 삭제(use_yn = 'N')
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "정보 게시글 삭제", notes = "정보성게시글 삭제(사용여부 변경)")
    @DeleteMapping("/info/{info_idx}")
    public CommonResult deleteSalsaUserByIdx(@ApiParam(value = "정보게시글 IDX", required = true)@PathVariable int info_idx){

        if("".equals(info_idx)){ //값이 없을 경우
            throw new CInfoArticleNotFoundException();
        }

        Boolean result = infoMapper.deleteInfoArticleByIdx(info_idx);

        if(result) {
            return responseService.getSuccessResult();
        }else{
            throw new CCommonDeleteFailedException();
        }
    }
}

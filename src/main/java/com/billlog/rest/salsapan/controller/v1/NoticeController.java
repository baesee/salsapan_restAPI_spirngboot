package com.billlog.rest.salsapan.controller.v1;

import com.billlog.rest.salsapan.advice.exception.CCommonDeleteFailedException;
import com.billlog.rest.salsapan.advice.exception.CCommonUpdateFailedException;
import com.billlog.rest.salsapan.advice.exception.CCommonWriteFailedException;
import com.billlog.rest.salsapan.advice.exception.CInfoArticleNotFoundException;
import com.billlog.rest.salsapan.controller.FcmPushUtils;
import com.billlog.rest.salsapan.mapper.NoticeMapper;
import com.billlog.rest.salsapan.model.SalsaNotice;
import com.billlog.rest.salsapan.model.response.CommonResult;
import com.billlog.rest.salsapan.model.response.ListResult;
import com.billlog.rest.salsapan.model.response.SingleResult;
import com.billlog.rest.salsapan.service.AndroidFCMPushNotificationsService;
import com.billlog.rest.salsapan.service.ResponseService;
import com.billlog.rest.salsapan.util.CustomUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Api(tags = {"8. Notice"})
@RestController
@RequestMapping(value = "/v1")
public class NoticeController {

    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private final ResponseService responseService; // 결과를 처리할 Service
    @Autowired
    private final AndroidFCMPushNotificationsService androidFCMPushNotificationsService;

    public NoticeController(ResponseService responseService, AndroidFCMPushNotificationsService androidFCMPushNotificationsService) {
        this.responseService = responseService;
        this.androidFCMPushNotificationsService = androidFCMPushNotificationsService;
    }


    // 공지사항 전체 글 목록 가져오기
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "공지사항 전체 목록 조회", notes = "공지사항 전체 목록 조회")
    @GetMapping("/notices")
    public ListResult<SalsaNotice> getAll(@ApiParam(value = "페이지 번호", required = true)@RequestParam int page,
                                          @ApiParam(value = "표시 글의 개수", required = true)@RequestParam int count) {

        //MariaDB LIMIT 페이지 계산식
        page = (page - 1) * count;

        return responseService.getListResult(noticeMapper.getNoticeArticleAll(page, count));
    }

    // notice index로 특정 정보 게시글 가져오기.
    //유효한 Jwt토큰을 설정해야만 User 리소스를 사용할 수 있도록 Header에 X-AUTH-TOKEN을 인자로 받도록 선언합니다.
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "공지사항 게시글 조회", notes = "notice_idx 이용해 공지사항으ㅜㄹ 조회한다.")
    @GetMapping("/notice/{notice_idx}")
    public SingleResult<SalsaNotice> getNoticeByIdx(@ApiParam(value = "공지사항글 IDX", required = true)@PathVariable int notice_idx) {

        SalsaNotice notice = noticeMapper.getNoticeByIdx(notice_idx);

        if(CustomUtils.isEmpty(notice)){
            throw new CInfoArticleNotFoundException();
        }else{
            return responseService.getSingleResult(notice);
        }

    }

    // notice 게시글 등록
    //유효한 Jwt토큰을 설정해야만 User 리소스를 사용할 수 있도록 Header에 X-AUTH-TOKEN을 인자로 받도록 선언합니다.
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "공지사항 글 등록", notes = "공지사항 글 작성한다.")
    @PostMapping("/notice")
    public CommonResult createNoticeArticle(SalsaNotice salsaNotice ) throws UnsupportedEncodingException {

        int result = noticeMapper.createNoticeArticle(salsaNotice);

        if(result == 1) {

            String title = "[SALSAPAN] 공지사항이 등록되었습니다.";
            String content = "공지사항 확인 후 서비스를 이용해주시기 바랍니다.";

            title   = URLEncoder.encode(title  ,"UTF-8");
            content = URLEncoder.encode(content,"UTF-8");

            // 공지사항이 등록 되면 전체 푸쉬메시지를 날린다.
            HttpEntity<String> request = FcmPushUtils.createFcmAllMessage(title, content);
            CompletableFuture<String> pushNotification = androidFCMPushNotificationsService.send(request);
            CompletableFuture.allOf(pushNotification).join();

            return responseService.getSuccessResult();

        }else{
            throw new CCommonWriteFailedException();
        }
    }

    // notice idx를 통해 해당 공지사항 게시글 내용 변경
    //유효한 Jwt토큰을 설정해야만 User 리소스를 사용할 수 있도록 Header에 X-AUTH-TOKEN을 인자로 받도록 선언합니다.
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "공지사항 글 수정", notes = "공지사항 게시물 수정한다.")
    @PutMapping("/notice/{notice_idx}")
    public CommonResult modifyNoticeArticleByIdx(@ApiParam(value = "공지사항 IDX", required = true)@PathVariable int notice_idx,
                                                    @ApiParam(value = "공지사항 작성 Object", required = true) SalsaNotice salsaNotice){

        //notice 인덱스 번호가 없을 경우 메시지와 함께 예외처리
        if("".equals(salsaNotice.getNotice_idx())){
            throw new CInfoArticleNotFoundException();
        }

        int result = noticeMapper.modifyNoticeArticleByIdx(salsaNotice);

        if(result == 1) {
            return responseService.getSuccessResult();
        }else{
            throw new CCommonUpdateFailedException();
        }

    }



    // notice index로 notice 게시글 삭제(use_yn = 'N')
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "공지사항 게시글 삭제", notes = "공지사항 게시글 삭제(사용여부 변경)")
    @DeleteMapping("/notice/{notice_idx}")
    public CommonResult deleteNoticeByIdx(@ApiParam(value = "공지사항 IDX", required = true)@PathVariable int notice_idx){

        if("".equals(notice_idx)){ //값이 없을 경우
            throw new CInfoArticleNotFoundException();
        }

        Boolean result = noticeMapper.deleteNoticeArticleByIdx(notice_idx);

        if(result) {
            return responseService.getSuccessResult();
        }else{
            throw new CCommonDeleteFailedException();
        }

    }
}

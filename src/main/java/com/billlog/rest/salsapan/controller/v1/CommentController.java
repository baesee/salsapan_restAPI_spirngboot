package com.billlog.rest.salsapan.controller.v1;

import com.billlog.rest.salsapan.controller.FcmPushUtils;
import com.billlog.rest.salsapan.mapper.CommentMapper;
import com.billlog.rest.salsapan.mapper.DeviceMapper;
import com.billlog.rest.salsapan.model.comment.SalsaComment;
import com.billlog.rest.salsapan.model.response.CommonResult;
import com.billlog.rest.salsapan.model.response.ListResult;
import com.billlog.rest.salsapan.service.AndroidFCMPushNotificationsService;
import com.billlog.rest.salsapan.service.ResponseService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Api(tags = {"9-3. Comment"})
@RestController
@RequestMapping(value = "/v1")
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private final ResponseService responseService; // 결과를 처리할 Service
    @Autowired
    private final AndroidFCMPushNotificationsService androidFCMPushNotificationsService;

    public CommentController(ResponseService responseService, AndroidFCMPushNotificationsService androidFCMPushNotificationsService) {
        this.responseService = responseService;
        this.androidFCMPushNotificationsService = androidFCMPushNotificationsService;
    }

    //해당 글에 등록된 댓글 목록 가져오기.
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "게시글의 댓글 목록 조회", notes = "게시글의 댓글 목록 조회")
    @GetMapping("/comment/{comment_manage_idx}")
    public ListResult<SalsaComment> getCommentsByCommentIdx(@ApiParam(value = "댓글 관리 IDX", required = true)@PathVariable int comment_manage_idx) {
    /*
    public ListResult<SalsaComment> getCommentsByCommentIdx(@ApiParam(value = "댓글 관리 IDX", required = true)@PathVariable int comment_manage_idx,
                                                            @ApiParam(value = "페이지 번호", required = true)@RequestParam int page,
                                                            @ApiParam(value = "표시 글의 개수", required = true)@RequestParam int count) {
    */
        //MariaDB LIMIT 페이지 계산식
//        page = (page - 1) * count;

        return responseService.getListResult(commentMapper.getCommentsByCommentIdx(comment_manage_idx));
    }

    //해당 글에 등록된 댓글 목록 가져오기.
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "댓글 등록", notes = "해당 글에 댓글 등록")
    @PostMapping("/comment")
    public CommonResult createComment(@ApiParam(value = "댓글 정보 Object", required = true) SalsaComment salsaComment) {
        int result = 0;

        result = commentMapper.createComment(salsaComment);

        if (result == 1) {

            // 커뮤니티 게시글 작성자에게 댓글이 등록되었다고 FCM push를 전송한다.
            // FCM 코멘트 IDX 값을 이용하여 글 커뮤니티 글 작성자의 user_idx를 찾아내 토큰 값을 가져온다.
            String token = deviceMapper.findMsgReciverUserByCommentIdx(salsaComment.getComment_idx());


            Map<String, Object> fcm = new HashMap<>();

            fcm.put("token", token);
            fcm.put("title", "SALSAPAN");
            fcm.put("body", "add comment ! 작성한 게시글에 댓글이 등록되었습니다.");

            HttpEntity<String> request = FcmPushUtils.createFcmMessageTargetToWriter(fcm);
            CompletableFuture<String> pushNotification = androidFCMPushNotificationsService.send(request);
            CompletableFuture.allOf(pushNotification).join();

            return responseService.getSuccessResult();
        } else {
            return responseService.getFailResult(99010, "댓글 등록 작업중 에러가 발생하였습니다.");
        }
    }

    // 댓글 내용 수정
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "댓글 내용 수정", notes = "작성한 댓글을 수정한다.")
    @PutMapping("/comment/{comment_idx}/{comment_sn}")
    public CommonResult modifyCommentByIdxSn(@ApiParam(value = "댓글 IDX", required = true)@PathVariable int comment_idx,
                                             @ApiParam(value = "댓글 SN", required = true) @PathVariable int comment_sn,
                                             @ApiParam(value = "댓글 작성자", required = true) @RequestParam int writer_user_idx,
                                             @ApiParam(value = "댓글 내용") @RequestParam String content) {

        SalsaComment salsaComment = new SalsaComment();
        salsaComment.setComment_idx(comment_idx);
        salsaComment.setComment_sn(comment_sn);
        salsaComment.setWriter_user_idx(writer_user_idx);

        if(!"".equals(content) || content != null) {
            salsaComment.setContent(content);
        }

        int result = commentMapper.modifyCommentByIdxSn(salsaComment);

        if(result == 1) {
            return responseService.getSuccessResult();
        }else{
            return responseService.getFailResult(99011, "댓글 수정 작업중 에러가 발생하였습니다.");
        }

    }

    // 댓글 삭제(user_yn = 'N')
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "댓글 삭제", notes = "댓글(Comment) 삭제(사용여부 변경)")
    @DeleteMapping("/comment/{comment_idx}/{comment_sn}")
    public CommonResult deleteCommentByIdxSn(@ApiParam(value = "댓글 IDX", required = true)@PathVariable int comment_idx,
                                             @ApiParam(value = "댓글 SN", required = true) @PathVariable int comment_sn,
                                             @ApiParam(value = "댓글 작성자", required = true) @RequestParam int writer_user_idx) {


        Boolean result = commentMapper.deleteCommentByIdxSn(comment_idx, comment_sn, writer_user_idx);

        if (result) {
            return responseService.getSuccessResult();
        } else {
            return responseService.getFailResult(99010, "댓글 등록 작업중 에러가 발생하였습니다.");
        }
    }

}

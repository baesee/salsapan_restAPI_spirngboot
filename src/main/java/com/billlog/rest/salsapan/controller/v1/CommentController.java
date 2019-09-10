package com.billlog.rest.salsapan.controller.v1;

import com.billlog.rest.salsapan.mapper.CommentMapper;
import com.billlog.rest.salsapan.model.SalsaCommunity;
import com.billlog.rest.salsapan.model.comment.SalsaComment;
import com.billlog.rest.salsapan.model.response.CommonResult;
import com.billlog.rest.salsapan.model.response.ListResult;
import com.billlog.rest.salsapan.service.ResponseService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"9-3. Comment"})
@RestController
@RequestMapping(value = "/v1")
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private final ResponseService responseService; // 결과를 처리할 Service

    public CommentController(ResponseService responseService) {
        this.responseService = responseService;
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
            return responseService.getSuccessResult();
        } else {
            return responseService.getFailResult(99010, "댓글 등록 작업중 에러가 발생하였습니다.");
        }
    }

}

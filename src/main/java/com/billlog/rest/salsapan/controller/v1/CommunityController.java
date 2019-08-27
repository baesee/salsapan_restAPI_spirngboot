package com.billlog.rest.salsapan.controller.v1;


import com.billlog.rest.salsapan.advice.exception.CCommonDeleteFailedException;
import com.billlog.rest.salsapan.advice.exception.CCommonUpdateFailedException;
import com.billlog.rest.salsapan.advice.exception.CCommonWriteFailedException;
import com.billlog.rest.salsapan.advice.exception.CInfoArticleNotFoundException;
import com.billlog.rest.salsapan.mapper.CommunityMapper;
import com.billlog.rest.salsapan.model.SalsaCommunity;
import com.billlog.rest.salsapan.model.SalsaInfo;
import com.billlog.rest.salsapan.model.response.CommonResult;
import com.billlog.rest.salsapan.model.response.ListResult;
import com.billlog.rest.salsapan.model.response.SingleResult;
import com.billlog.rest.salsapan.service.ResponseService;
import com.billlog.rest.salsapan.util.CustomUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"4. Community"})
@RestController
@RequestMapping(value = "/v1")
public class CommunityController {

    @Autowired
    private CommunityMapper communityMapper;
    @Autowired
    private final ResponseService responseService; // 결과를 처리할 Service

    public CommunityController(ResponseService responseService) {
        this.responseService = responseService;
    }

    // 모든 커뮤니티 글 목록 가져오기
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "커뮤니티 게시글 전체 목록 조회", notes = "커뮤니티 전체 게시글 목록 조회")
    @GetMapping("/communitys")
    public ListResult<SalsaCommunity>  getAll() {

        return responseService.getListResult(communityMapper.getCommunityArticleAll());
    }

    // community index로 특정 정보 게시글 가져오기.
    //유효한 Jwt토큰을 설정해야만 User 리소스를 사용할 수 있도록 Header에 X-AUTH-TOKEN을 인자로 받도록 선언합니다.
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "커뮤니티 게시글 조회", notes = "community_idx 이용해 정보 게시글를 조회한다.")
    @GetMapping("/community/{community_idx}")
    public SingleResult<SalsaCommunity> getCommunityByIdx(@ApiParam(value = "커뮤니티글 IDX", required = true)@PathVariable int community_idx) {

        SalsaCommunity community = communityMapper.getCommunityByIdx(community_idx);

        if(CustomUtils.isEmpty(community)){
            throw new CInfoArticleNotFoundException();
        }else{
            return responseService.getSingleResult(community);
        }

    }

    // community 게시글 등록
    //유효한 Jwt토큰을 설정해야만 User 리소스를 사용할 수 있도록 Header에 X-AUTH-TOKEN을 인자로 받도록 선언합니다.
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "커뮤니티 게시글 등록", notes = "커뮤니티 게시글을 작성한다.")
    @PostMapping("/community")
    public CommonResult createCommunityArticle(SalsaCommunity salsaCommunity){

        int result = communityMapper.createCommunityArticle(salsaCommunity);

        if(result == 1) {
            return responseService.getSuccessResult();
        }else{
            throw new CCommonWriteFailedException();
        }
    }

    // 커뮤니티 idx를 통해 해당 커뮤니티 게시글 내용 변경
    //유효한 Jwt토큰을 설정해야만 User 리소스를 사용할 수 있도록 Header에 X-AUTH-TOKEN을 인자로 받도록 선언합니다.
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "커뮤니티글 수정", notes = "커뮤니티 게시물 수정한다.")
    @PutMapping("/community/{community_idx}")
    public CommonResult modifyCommunityArticleByIdx(@ApiParam(value = "정보게시글 IDX", required = true)@PathVariable int community_idx,
                                               @ApiParam(value = "정보게시글 작성 Object", required = true) SalsaCommunity salsaCommunity){

        //커뮤니티 인덱스 번호가 없을 경우 메시지와 함께 예외처리
        if("".equals(salsaCommunity.getCommunity_idx())){
            throw new CInfoArticleNotFoundException();
        }

        int result = communityMapper.modifyCommunityArticleByIdx(salsaCommunity);

        if(result == 1) {
            return responseService.getSuccessResult();
        }else{
            throw new CCommonUpdateFailedException();
        }

    }



    // community index로 community 게시글 삭제(use_yn = 'N')
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "커뮤니티 게시글 삭제", notes = "커뮤니티 게시글 삭제(사용여부 변경)")
    @DeleteMapping("/community/{community_idx}")
    public CommonResult deleteCommunityByIdx(@ApiParam(value = "커뮤니티 IDX", required = true)@PathVariable int community_idx){

        if("".equals(community_idx)){ //값이 없을 경우
            throw new CInfoArticleNotFoundException();
        }

        Boolean result = communityMapper.deleteCommunityArticleByIdx(community_idx);

        if(result) {
            return responseService.getSuccessResult();
        }else{
            throw new CCommonDeleteFailedException();
        }

    }

}


package com.billlog.rest.salsapan.controller.v1.manage;

import com.billlog.rest.salsapan.model.response.CommonResult;
import com.billlog.rest.salsapan.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"99. Manage"})
@RestController
@RequestMapping(value = "/v1/manage")
public class ManageController {
    /**
     * 관리자(ADMIN) 만 사용가능한 컨트롤러입니다.
     */

    @Autowired
    private final ResponseService responseService; // 결과를 처리할 Service

    public ManageController(ResponseService responseService) {
        this.responseService = responseService;
    }

    //유효한 Jwt토큰을 설정해야만 User 리소스를 사용할 수 있도록 Header에 X-AUTH-TOKEN을 인자로 받도록 선언합니다.
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "출력 테스트", notes = "관리자 권한 유저가 호출시 출력한다")
    @PostMapping(value = "/test")
    public CommonResult test() {

        return responseService.getSuccessResult();
    }

}

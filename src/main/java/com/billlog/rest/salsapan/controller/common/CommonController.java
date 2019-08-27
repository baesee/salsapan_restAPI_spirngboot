package com.billlog.rest.salsapan.controller.common;

import com.billlog.rest.salsapan.mapper.CommonMapper;
import com.billlog.rest.salsapan.model.SalsaRole;
import com.billlog.rest.salsapan.model.common.SalsaCity;
import com.billlog.rest.salsapan.model.response.ListResult;
import com.billlog.rest.salsapan.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"9 - 1. Common"})
@RestController
@RequestMapping(value = "/v1/common")
public class CommonController {

    @Autowired
    private CommonMapper commonMapper;

    private final ResponseService responseService;

    public CommonController(ResponseService responseService) {
        this.responseService = responseService;
    }

    @ApiOperation(value = "도시 정보 조회", notes = "모든 도시(City)를 조회한다")
    @GetMapping(value = "/citys")
    public ListResult<SalsaCity> getCitys() {
        // 결과데이터가 여러건인경우 getListResult를 이용해서 결과를 출력한다.

        return responseService.getListResult(commonMapper.getCitys());
    }


    /*
    //유효한 Jwt토큰을 설정해야만 User 리소스를 사용할 수 있도록 Header에 X-AUTH-TOKEN을 인자로 받도록 선언합니다.
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 권한 조회", notes = "모든 회원의 권한을 조회한다")
    @GetMapping(value = "/roles")
    public ListResult<SalsaRole> findAllRole() {

        return responseService.getListResult(userMapper.getAllRoles());

    }
    */

}

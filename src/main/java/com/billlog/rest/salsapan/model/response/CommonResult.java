package com.billlog.rest.salsapan.model.response;

import io.swagger.annotations.ApiModelProperty;

/*
// 기존 USER 정보
{
    "msrl": 1,
    "uid": "yumi@naver.com",
    "name": "정유미"
}
// 표준화한 USER 정보
{
  "data": {
    "msrl": 1,
    "uid": "yumi@naver.com",
    "name": "정유미"
  },
  "success": true
  "code": 0,
  "message": "성공하였습니다."
}

api의 실행 결과를 담는 공통 모델
 - api의 처리 상태 및 메시지를 내려주는 데이터로 구성됩니다.
 - success는 api의 성공 실패 여부를 나타내고 code
 - msg는 해당 상황에서의 응답 코드와 응답 메시지를 나타냅니다.

 */

// 결과가 단일건인 api를 담는 모델 : SingleResult Class
// 결과가 여러건인 api를 담는 모델 : ListResult Class
public class CommonResult {
    @ApiModelProperty(value = "응답 성공여부 : true/false")
    private boolean success;

    @ApiModelProperty(value = "응답 코드 번호 : >= 0 정상, < 0 비정상")
    private int code;

    @ApiModelProperty(value = "응답 메시지")
    private String msg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

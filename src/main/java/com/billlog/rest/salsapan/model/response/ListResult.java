package com.billlog.rest.salsapan.model.response;

import java.util.List;

/*
  결과가 여러건인 api를 담는 모델
  - api 결과가 다중건인 경우에 대한 데이터 모델입니다.
  - 결과 필드를 List 형태로 선언하고 Generic Interface에 <T>를 지정하여 어떤 형태의 List값도 넣을 수 있도록 구현하였습니다.
  - 또한 CommonResult를 상속받으므로 api요청 결과도 같이 출력됩니다.
 */
public class ListResult<T> extends CommonResult {
    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
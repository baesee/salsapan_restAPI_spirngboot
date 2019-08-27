package com.billlog.rest.salsapan.model.response;

/*
  결과가 단일건인 api를 담는 모델
   - Generic Interface에 <T>를 지정하여 어떤 형태의 값도 넣을 수 있도록 구현하였습니다.
   - 또한 CommonResult를 상속받으므로 api요청 결과도 같이 출력됩니다.
 */
public class SingleResult<T> extends CommonResult {
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
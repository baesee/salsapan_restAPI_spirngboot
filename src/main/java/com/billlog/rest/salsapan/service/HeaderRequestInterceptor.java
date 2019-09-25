package com.billlog.rest.salsapan.service;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

import java.io.IOException;

/**
 * HTTP통신을 가능하게 해주기위해서 Request객체를 만들어주어 설정하기 위함.
 */
public class HeaderRequestInterceptor implements ClientHttpRequestInterceptor {
    private final String headerName;
    private final String headerValue;

    public HeaderRequestInterceptor(String headerName, String headerValue) {
        this.headerName = headerName;
        this.headerValue = headerValue;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        HttpRequest wrapper = new HttpRequestWrapper(httpRequest);
        wrapper.getHeaders().set(headerName, headerValue);

        return clientHttpRequestExecution.execute(wrapper, bytes);
    }
}

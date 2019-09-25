package com.billlog.rest.salsapan.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

/**
 * 구글 FCM서버에 데이터를 전송하는 역할을 함.
 */
@Service
public class AndroidFCMPushNotificationsService {

    private static final String FIREBASE_SERVER_KEY = "AAAAs8DUX4M:APA91bHgJR5TCFNRIHgb85RklqLuau-FxWvohpqxMTBjfczq250A2SlcbVMFXo4NBEQhRwcbcYDTdH0rVy4l6BNoPenZLNvG3pYxRP01F93NoonPyyn89Xe8M4WnCbQiC9cud6ikfec9";
    private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";

    @Async
    public CompletableFuture<String> send(HttpEntity<String> entity) {

        RestTemplate restTemplate = new RestTemplate();

        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();

        interceptors.add(new HeaderRequestInterceptor("Authorization",  "key=" + FIREBASE_SERVER_KEY));
        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json; UTF-8 "));
        restTemplate.setInterceptors(interceptors);

        String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);

        return CompletableFuture.completedFuture(firebaseResponse);
    }
}

package com.billlog.rest.salsapan.controller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class FcmPushUtils {

    /**
     * 전체 사용자에게 푸쉬 - 공지사항 등과 같이 사용
     * @param title
     * @param content
     * @return
     * @throws JSONException
     */
    public static @ResponseBody HttpEntity<String> createFcmAllMessage(String title, String content) throws JSONException {

        Map<String, Object> fcm = new HashMap<>();
        fcm.put("title",title);
        fcm.put("body",content);

        JSONObject body = new JSONObject();

        body.put("to", "/topics/salsapan"); //topic 으로 보낼경우 토큰 값 없이 전체 유저에게 발송한다.

        JSONObject notification = new JSONObject();
        notification.put("title", fcm.get("title"));
        notification.put("body", fcm.get("body"));

        /**
         * "notification" 으로 보내면 안드로이드 백그라운드 상태에서만 onMessageReceived() 를 타고
         * "data" 로 보내면 포그라운드, 백그라운드 모두 onMessageReceived() 를 탄다.
         * 한글 깨짐으로 인해 onMessageReceived()에서 디코딩을 하기위해 "data"로 보낸다.
         */

//        body.put("notification", notification);
        body.put("data", notification);

        HttpEntity<String> request = new HttpEntity<>(body.toString());

        return request;
    }

    //

    /**
     * 인포 정보가 등록 되고 그 글과 같은 도시 지역을 관심지역으로 설정한 사용자에게 푸쉬를 날린다.
     * @param fcm - "title":제목, "body":내용, "tokens"List<String> : 전송 받을 사용자 token 값
     * @return
     * @throws JSONException
     */
    public static @ResponseBody HttpEntity<String> createFcmMessageTargetToCity(Map<String, Object> fcm) throws JSONException {

        JSONObject body = new JSONObject();

        List<String> tokenList = (List<String>) fcm.get("tokens");

        JSONArray array = new JSONArray();

        for(int i = 0 ; i < tokenList.size() ; i++){
            array.put(tokenList.get(i));
        }

        body.put("registration_ids", array); //여러개의 메시지일 경우 registration_ids, 단일 메세지의 경우 to 사용

        JSONObject notification = new JSONObject();
        notification.put("title", fcm.get("title"));
        notification.put("body", fcm.get("body"));
//        body.put("notification", notification);
        body.put("data", notification);

        HttpEntity<String> request = new HttpEntity<>(body.toString());

        return request;

    }

    // 해당 게시글의 댓글이 등록되었을때 작성자에게 푸쉬 발송.
    // 같은 도시 지역 사용자에게 푸쉬
    public static @ResponseBody HttpEntity<String> createFcmMessageTargetToWriter(Map<String, Object> fcm) throws JSONException {

        JSONObject body = new JSONObject();

        body.put("to", fcm.get("token")); //단일 메세지의 경우 to 사용

        JSONObject notification = new JSONObject();
        notification.put("title", fcm.get("title"));
        notification.put("body", fcm.get("body"));

//        body.put("notification", notification);
        body.put("data", notification);

        HttpEntity<String> request = new HttpEntity<>(body.toString());

        return request;
    }

}

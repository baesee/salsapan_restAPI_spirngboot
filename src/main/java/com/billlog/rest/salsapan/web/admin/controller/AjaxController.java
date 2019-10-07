package com.billlog.rest.salsapan.web.admin.controller;

import com.billlog.rest.salsapan.controller.CustomUtils;
import com.billlog.rest.salsapan.controller.FcmPushUtils;
import com.billlog.rest.salsapan.mapper.DeviceMapper;
import com.billlog.rest.salsapan.mapper.UserMapper;
import com.billlog.rest.salsapan.service.AndroidFCMPushNotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping("/web/ajax")
public class AjaxController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private final AndroidFCMPushNotificationsService androidFCMPushNotificationsService;

    public AjaxController(AndroidFCMPushNotificationsService androidFCMPushNotificationsService) {
        this.androidFCMPushNotificationsService = androidFCMPushNotificationsService;
    }

    //AJAX 호출 (게시글 등록, 수정)
    @PostMapping(value="/user/premium")
    @ResponseBody
    public Object modifyPremiumUser(@RequestParam(value="user_idxs[]") String[] user_idxs,
            @RequestParam(value="type") String type) throws UnsupportedEncodingException {

        String title = "SALSAPAN";
        String content;
        String deviceTokenByIdx;
        List<String> tokenList = new ArrayList<>();

        for(int i = 0 ; i < user_idxs.length ; i++){
            userMapper.modifyPStateAndRole(user_idxs[i] , type);
            deviceTokenByIdx = deviceMapper.findMsgReciverUsersByUserIdx(user_idxs[i]);
            if(!CustomUtils.isEmpty(deviceTokenByIdx)) {
                tokenList.add(deviceTokenByIdx);
            }
        }

        Map<String, Object> retVal = new HashMap<>();

        //성공했다고 처리
        retVal.put("code", "OK");
        if("S".equals(type)){
            retVal.put("message", "선택한 회원을 프리미엄 회원으로 변경 하였습니다.");
            content = "회원님의 권한이 프리미엄 회원으로 변경되었습니다.";
        }else if("F".equals(type)){
            retVal.put("message", "선택한 회원의 프리미엄 신청을 철회 하였습니다.");
            content = "회원님의 프리미엄 신청이 철회되었습니다.";
        }else{
            retVal.put("message", "처리가 완료되었습니다.");
            content = "회원님의 프리미엄 신청 처리가 완료되었습니다.";
        }

        if( !CustomUtils.isEmpty(tokenList) ){
            title   = URLEncoder.encode(title  ,"UTF-8");
            content = URLEncoder.encode(content,"UTF-8");

            Map<String, Object> fcm = new HashMap<>();
            fcm.put("title", title);
            fcm.put("body", content);
            fcm.put("tokens",tokenList);

            HttpEntity<String> request = FcmPushUtils.createFcmMessageTargetToCity(fcm);
            CompletableFuture<String> pushNotification = androidFCMPushNotificationsService.send(request);
            CompletableFuture.allOf(pushNotification).join();
        }

        return retVal;

    }

}

package com.billlog.rest.salsapan.controller.v1;

import com.billlog.rest.salsapan.advice.exception.CUserNotFoundException;
import com.billlog.rest.salsapan.mapper.DeviceMapper;
import com.billlog.rest.salsapan.model.SalsaDevice;
import com.billlog.rest.salsapan.model.response.CommonResult;
import com.billlog.rest.salsapan.service.AndroidFCMPushNotificationsService;
import com.billlog.rest.salsapan.service.ResponseService;
import com.billlog.rest.salsapan.util.CustomUtils;
import io.swagger.annotations.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Api(tags = {"9 - 9. FCM"})
//@RestController
@RestController
@RequestMapping(value = "/v1/fcm")
public class FCMDeviceController {
    /**
     * 참고 블로그
     * http://blog.naver.com/PostList.nhn?blogId=scw0531
     */

    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private final ResponseService responseService;
    @Autowired
    private final AndroidFCMPushNotificationsService androidFCMPushNotificationsService;

    public FCMDeviceController(ResponseService responseService, AndroidFCMPushNotificationsService androidFCMPushNotificationsService) {
        this.responseService = responseService;
        this.androidFCMPushNotificationsService = androidFCMPushNotificationsService;
    }

    /**
     * 안드로이드 로그인 사용자 디바이스 정보 등록
     * @param user_id
     * @param device_token - FCM 에서 사용할 Dvice Token 값
     * @param device_name - 디바이스 명
     * @param os_version - OS 버전정보
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "1. 사용자 디바이스 등록", notes = "사용자의 디바이스 토큰과 정보를 등록/수정 한다")
    @PostMapping("/device")
    public CommonResult updateDeviceInfo(@ApiParam(value = "회원 ID", required = true)@RequestParam String user_id,
                                         @ApiParam(value = "DeviceToken", required = true) @RequestParam String device_token,
                                         @ApiParam(value = "디바이스 명", required = true) @RequestParam String device_name,
                                         @ApiParam(value = "브랜드 명", required = true) @RequestParam String device_brand,
                                         @ApiParam(value = "제품 명", required = true) @RequestParam String device_product,
                                         @ApiParam(value = "SDK 버전정보", required = true) @RequestParam String sdk_version,
                                         @ApiParam(value = "OS 버전정보", required = true) @RequestParam String os_version) {

        if (CustomUtils.isEmpty(user_id)) {
            throw new CUserNotFoundException();
        } else {
            SalsaDevice salsaDevice = new SalsaDevice();

            salsaDevice.setUser_id(user_id);
            salsaDevice.setDevice_token(device_token);
            salsaDevice.setDevice_name(device_name);
            salsaDevice.setDevice_brand(device_brand);
            salsaDevice.setDevice_product(device_product);
            salsaDevice.setSdk_version(sdk_version);
            salsaDevice.setOs_version(os_version);

            int result = deviceMapper.updateDeviceInfo(salsaDevice);

            if (result > 0) {
                return responseService.getSuccessResult();
            }else{
                return responseService.getFailResult(99091,"디바이스 정보등록 중 오류가 발생하였습니다.");
            }
        }
    }

    /**
     * 전체 메시지 전송 ( 공지사항 )
     * @throws JSONException
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "2. 전체 푸쉬 전송", notes = "전체 사용자에게 푸쉬메시지를 전송한다.")
    @PostMapping(value = "/sendAll")
    public @ResponseBody ResponseEntity<String> send(String title, String message) throws JSONException{

        JSONObject body = new JSONObject();

        body.put("to", "/topics/salsapan"); //단일 메세지의 경우 to 사용

        JSONObject notification = new JSONObject();
        notification.put("title", title);
        notification.put("body", message);

        body.put("notification", notification);

        HttpEntity<String> request = new HttpEntity<>(body.toString());

        CompletableFuture<String> pushNotification = androidFCMPushNotificationsService.send(request);
        CompletableFuture.allOf(pushNotification).join();

        try{
            String firebaseResponse = pushNotification.get();

            return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);

        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }

        return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
    }
}


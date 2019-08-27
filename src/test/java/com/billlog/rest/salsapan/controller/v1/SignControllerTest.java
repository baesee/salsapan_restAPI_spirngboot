package com.billlog.rest.salsapan.controller.v1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//Boot의 Configuration들을 자동으로 설정할 수 있습니다. 다음과 같이 classes 설정으로 일부만 로드할 수도 있습니다. 명시하지 않으면 Config에 명시된 모든 빈이 로드됩니다.
//@SpringBootTest(classes = {CustomUserDetailService.class, SecurityConfiguration.class})
//@AutoConfigureMockMvc
//@Transactional
public class SignControllerTest {
/*
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void signin() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "swag");
        params.add("password", "1234");
        mockMvc.perform(post("/v1/signin").params(params))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").exists())
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    public void signup() throws Exception {
        long epochTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "happydaddy_" + epochTime + "@naver.com");
        params.add("password", "12345");
        params.add("name", "happydaddy_" + epochTime);
        mockMvc.perform(post("/v1/signup").params(params))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").exists());
    }

    @Test
    @WithMockUser(username = "mockUser", roles = {"ADMIN"}) // 가상의 Mock 유저 대입
    public void accessdenied() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/exception/accessdenied"));
    }
 */






    /*
     //==== Junit assert 메서드 ==== //
        assertNotNull(obj), assertNotNull(obj)
        객체(obj)의 Null여부를 테스트
        assertTrue(condition), assertFalse(condition)
        조건(condition)의 참/거짓 테스트
        assertEquals(obj1, obj2), assertNotEquals(obj1, obj2)
        obj1와 obj2의 값이 같은지 테스트
        assertSame(obj1, obj2)
        obj1과 obj2의 객체가 같은지 테스트
        assertArrayEquals(arr1,arr2)
        배열 arr1과 arr2가 같은지 확인한다.
        assertThat(T actual, Matcher matcher)
        첫번째 인자에 비교대상 값을 넣고, 두번째 로직에는 비교로직을 넣어 테스트.
        ex) assertThat(a, is(100)) : a의 값이 100인가?
        ex) assertThat(obj, is(nullValue())); 객체가 null인가?

        MockMvc 메서드
        perform

        주어진 url을 수행할수 있는 환경을 구성해 줍니다.
        GET, POST, PUT, DELETE 등 다양한 method를 처리 가능합니다.
        header에 값을 세팅하거나 AcceptType 설정을 지원합니다.
        mockMvc.perform(post(“/v1/signin”).params(params)
        andDo

        perform 요청을 처리합니다. andDo(print())를 하면 처리 결과를 console 화면에서도 볼수 있습니다.
        andExpect

        검증 내용을 체크합니다.
        결과가 200 OK 인지 체크 – andExpect(status().isOK())
        결과 데이터가 Json인 경우 다음과 같이 체크 가능합니다.
        .andExpect(jsonPath(“$.success”).value(true))
        andReturn

        테스트 완료 후 결과 객체를 받을수 있습니다. 후속 작업이 필요할때 용이 합니다.
        MvcResult result = mockMvc.perform(post(“/v1/signin”).params(params)).andDo(print());
        여기까지 Spring의 단위 테스트를 위한 가장 기본적인 방법에 대해 살펴보았습니다. 적절한 어노테이션의 사용은 테스팅 환경을 쉽게 구축하는데 매우 효과적입니다.
        매번 서버를 올렸다 내렸다 하면서 테스트하는 것은 정작 중요한 프로세스를 개발할 시간을 낭비하는 것과 같습니다.
        무언가 프로세스가 변경되었을 때 바로바로 돌려보고 검증할 수 있는 테스트 코드는 필수적입니다.
     */
}
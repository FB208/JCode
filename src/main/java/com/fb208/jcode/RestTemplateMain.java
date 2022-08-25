package com.fb208.jcode;


import com.alibaba.fastjson.JSONObject;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class RestTemplateMain {

    public static void main(String[] args) {
        //模拟数据
        String mockToken = "sys 47b26945-c7b0-493c-9aad-d9de8d97edef";
        String mockJson = "{\"map\":{\"code\":\"123456\"},\"phone\":\"13110034599\"," +
                "\"templateId\":\"1319483330181218304\"}";
        String url = "http://gateway.ha-iot.com:8888/cloud-provider-shortmessage/sendSM";
        //调用短信服务
        RestTemplate restTemplate = new RestTemplate();
        //请求头
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        headers.setContentType(MediaType.APPLICATION_JSON);
        //请求头加入
        headers.add("token",mockToken);

        //发送
        HttpEntity<String> request = new HttpEntity<>(mockJson, headers);
        ResponseEntity<String> resEntity = restTemplate.postForEntity(url, request, String.class);

        System.out.println(resEntity.getBody());
    }
}

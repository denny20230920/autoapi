package com.demo.api;

import com.alibaba.fastjson2.JSON;
import com.demo.pojo.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import io.qameta.allure.Allure;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class BackUserAPI {

    //用户登录
    public String login(String apiUrl, User user){
        //获取被测环境域名
        String url = BaseAPI.url+apiUrl;

        Allure.description("获取图书列表");
        Allure.step("请求地址:"+url);
        Allure.step("请求参数:"+user);

        //将user对象转成字符串
        String jsonString = JSON.toJSONString(user);

        String result = BaseAPI.postJson(url,jsonString,"");

        return result;
    }

    //获取图书列表
    public String bookList(String apiUrl,String token){

        //获取被测环境域名
        String url = BaseAPI.url+apiUrl;

        Allure.description("获取图书列表");
        Allure.step("请求地址:"+url);

        String result = BaseAPI.get(url, token);

        return result;
    }

    public static void main(String[] args) throws JsonProcessingException {

        String url = "/users/login";

        User user = new User();
        user.setUsername("111222");
        user.setPassword("123456");

        BackUserAPI backUserAPI = new BackUserAPI();

        String token = backUserAPI.login(url, user);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(token);
        token = jsonNode.get("data").asText();
        System.out.println(token);

        String result = backUserAPI.bookList("/books/1/10", token);
        System.out.println(result);

    }

}

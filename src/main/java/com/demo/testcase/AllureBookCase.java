package com.demo.testcase;

import com.demo.api.BackUserAPI;
import com.demo.pojo.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Epic("图书管理系统接口自动化")
@Feature("图书模块接口")
public class AllureBookCase {

    private String url;

    private String token;

    private BackUserAPI backUserAPI;

    @BeforeClass
    public void setUp(){

        InputStream ios = AllureBookCase.class.getClass().getResourceAsStream("/property.properties");

        Properties properties = new Properties();
        try {
            properties.load(ios);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String loginUrl = properties.getProperty("login.url");
        String username = properties.getProperty("books.user.username");
        String password = properties.getProperty("books.user.password");
        url = properties.getProperty("books.options.none");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        backUserAPI = new BackUserAPI();
        String loginResult = backUserAPI.login(loginUrl, user);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(loginResult);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        token = jsonNode.get("data").asText();
    }

    @Test(priority = 1,description = "获取图书列表")
    @Story("T1_获取图书列表")
    public void T1_BookList(){
        String result = backUserAPI.bookList("/books/1/10", token);
        Object value = Configuration.defaultConfiguration().jsonProvider().parse(result);
        String msg = JsonPath.read(value, "$.msg");
        Assert.assertEquals(msg,"success");
    }
}

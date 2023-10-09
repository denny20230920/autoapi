package com.demo.testcase;

import com.demo.api.BackUserAPI;
import com.demo.pojo.User;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("图书管理系统接口自动化")
@Feature("用户模块接口")
public class AllureUserCase {

    private BackUserAPI backUserAPI;

    @BeforeClass
    public void setUp(){
        backUserAPI = new BackUserAPI();
    }

    @Test(priority = 1,description = "用户登录")
    @Story("T1_用户登录")
    public void T1_Login(){
        //请求地址
        String url = "/users/login";

        //请求数据
        User user = new User();
        user.setUsername("111222");
        user.setPassword("123456");

        //预期结果
        String ex = "success";

        String result = backUserAPI.login(url, user);

        //通过jsonpath获取响应数据，并断言
        Object value = Configuration.defaultConfiguration().jsonProvider().parse(result);
        //获取运行时的响应数据
        String msg = JsonPath.read(value, "$.msg");
        //根据获取的数据进行断言
        Assert.assertEquals(msg,ex);
    }

}

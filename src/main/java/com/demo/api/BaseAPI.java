package com.demo.api;

import okhttp3.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BaseAPI {

    //初始化OkHttp
    public static final OkHttpClient client = new OkHttpClient();

    //地址被测系统的地址
    public static String url;

    //项目只获取一次项目被测地址
    static {

        InputStream ios = BaseAPI.class.getClass().getResourceAsStream("/property.properties");

        Properties properties = new Properties();
        try {
            properties.load(ios);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        url = properties.getProperty("host.url");
    }

    //OkHttp的put请求,请求体位josn方式
    public static String put(String apiUrl,String param){

        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json"),param);

        Request request = new Request.Builder().url(apiUrl).put(requestBody).build();

        Response response = null;
        String result = null;

        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()){
                result = response.body().string();
                System.out.println(result);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        response.close();

        return result;
    }

    //OkHttp的post请求,请求体位josn方式
    public static String postJson(String apiUrl,String param,String token){

        //定义传参的数据格式和数据
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),param);

        Request request = new Request.Builder()
                .url(apiUrl)
                .post(requestBody)
                .header("token",token)
                .build();

        Response response = null;
        String result = null;

        try {
            response = client.newCall(request).execute();

            if (response.isSuccessful()){
                result = response.body().string();
                //System.out.println("返回数据："+result);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        response.close();

        return result;
    }

    //Okhttp的post表单请求
    public static String postForm(String apiUrl,RequestBody requestBody,String token){

        Request request = new Request.Builder()
                .url(apiUrl)
                .post(requestBody)
                .header("token",token)
                .build();

        Response response = null;
        String result = null;

        try {
            response = client.newCall(request).execute();

            if (response.isSuccessful()){

                result = response.body().string();

                //System.out.println("返回结果："+result);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        response.close();

        return result;
    }

    public static String get(String apiUrl,String token){

        //构建请求
        Request request = new Request.
                Builder()
                .url(apiUrl)
                .header("token",token)
                .build();

        Response response = null;
        String result = null;

        try {
            //发送请求
            response = client.newCall(request).execute();
            //获取响应数据
            if (response.isSuccessful()) {
                result = response.body().string();
                //System.out.println("返回结果："+result);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //关闭响应资源
        response.close();

        return result;
    }

}

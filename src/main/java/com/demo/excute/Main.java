package com.demo.excute;

import org.testng.TestNG;

import java.util.Collections;

public class Main {

    public static void main(String[] args) {
        //批量运行测试用例
        //创建TestNG对象
        TestNG testNG = new TestNG();
        //执行testng.xml文件配置
        testNG.setTestSuites(Collections.singletonList("E:\\workspace\\api\\testng.xml"));
        //运行
        testNG.run();

    }

}

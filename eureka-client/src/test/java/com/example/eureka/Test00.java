package com.example.eureka;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @description:
 * @author: maben
 * @createDate: 2020/4/17
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class Test00 {

    @Test
    public void test00(){
        System.out.println("12345678");
    }

    @Test
    public void 测试中文方法(){
        System.out.println("12345678");
    }

}

package com.jsp;

import com.maben.util.JavaUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Project springboot-parent
 * @Package jsp
 * @File ${File_name} 创建时间:2020年07月13日 13:37
 * @Title 标题（要求能简洁地表达出类的功能和职责）
 * @Description 描述（简要描述类的职责、实现方式、使用注意事项等）
 * @Copyright Copyright (c) 2020 中国软件与技术服务股份有限公司
 * @Company 中国软件与技术服务股份有限公司
 * @Module 模块: 模块名称
 * @Author maben
 * @Reviewer 审核人
 * @Version 1.0
 * @History 修订历史（历次修订内容、修订人、修订时间等）
 *
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TestBM {

    @Test
    public void test00()throws Exception{
        System.out.println("----------------------------------test start----------------------------------------");
        System.out.println(JavaUtils.execCommand("mysql -uroot -proot"));
    }

}

package com.example.euraka;

import com.maben.util.JavaUtils;
import com.maben.util.OSUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: maben
 * @createDate: 2020/4/29
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class Test1 {
    @Test
    public void test00() throws Exception {
        final long start = System.currentTimeMillis();
        while (true) {
            final long end = System.currentTimeMillis();
            final long cha = end - start;
            if ((cha / 60000 > 0) && (cha % 60000 == 0)) {
                System.out.println(cha);
                System.out.println(new Date());
            }
        }
    }

    @Test
    public void test01() throws Exception {
        System.out.println("----------------------------------test start----------------------------------------");
        System.out.println(execCmd(new ArrayList<String>(){{
            add("mysql -uroot -pmaben123");
            add("show databases;");
        }},null));
    }

    /**
     * 执行系统命令, 返回执行结果
     *
     * @param cmds 需要执行的命令
     * @param dir 执行命令的子进程的工作目录, null 表示和当前主进程工作目录相同
     */
    public static String execCmd(List<String> cmds, File dir) throws Exception {
        StringBuilder result = new StringBuilder();

        Process process = null;
        BufferedReader bufrIn = null;
        BufferedReader bufrError = null;

        try {
            // 执行命令, 返回一个子进程对象（命令在子进程中执行）
            String[] arr = new String[cmds.size()+2];
            arr[0] = "cmd";
            arr[1] = "/c";
            for (int i = 0; i < cmds.size(); i++) {
                arr[i+2] = cmds.get(i);
            }
            process = Runtime.getRuntime().exec(arr,null, dir);

            // 方法阻塞, 等待命令执行完成（成功会返回0）
//            final int i = process.waitFor();
//            System.out.println("执行结果："+i);

            // 获取命令执行结果, 有两个结果: 正常的输出 和 错误的输出（PS: 子进程的输出就是主进程的输入）
            bufrIn = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            bufrError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "UTF-8"));

            // 读取输出
            String line = null;
            while ((line = bufrIn.readLine()) != null) {
                result.append(line).append('\n');
            }
            while ((line = bufrError.readLine()) != null) {
                result.append(line).append('\n');
            }

        } finally {
            closeStream(bufrIn);
            closeStream(bufrError);

            // 销毁子进程
            if (process != null) {
                process.destroy();
            }
        }

        // 返回执行结果
        return result.toString();
    }

    private static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (Exception e) {
                // nothing
            }
        }
    }
}

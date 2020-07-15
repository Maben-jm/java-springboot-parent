package com.maben.util;

import org.springframework.boot.system.ApplicationHome;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class JavaUtils {

    /**
     *@description 获取jar包运行路径
     *@time    创建时间:2019年11月5日上午10:39:30
     *@throws Exception
     *@history 修订历史（历次修订内容、修订人、修订时间等）
     *@return 路径
     */
    protected String getJarRuntimePath() {
        final ApplicationHome h = new ApplicationHome(getClass());
        final File jarF = h.getSource();
        final String path = jarF.getParentFile().toString();
        return path;
    }
    /**
     * 与页面保持长连接
     * @param response response
     * @throws Exception
     */
    public static void connectHtml(HttpServletResponse response) throws Exception {
        //和前端保持畅通
        response.getOutputStream().write(" ".getBytes());
        response.flushBuffer();
    }

    /**
     * 执行命令
     * @param str 需要执行的命令
     */
    public static String execCommand(String str)throws Exception{
        String[] cmd ;
        if (OSUtil.isLinux() || OSUtil.isMacOSX()){
            cmd = new String[]{ "sh", "-c", str };
        }else if (OSUtil.isWindows()){
            cmd = new String[]{ "cmd", "/c", str };
        }else {
            throw new Exception("本机环境不确定，无法执行！！！");
        }
        final Process process = Runtime.getRuntime().exec(cmd);

        // 0 表示线程正常终止。
        if (process.waitFor() == 0) {
            final BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            final StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            final String result = sb.toString();
            return result;
        }
        throw new Exception("执行命令报错！！");
    }
}

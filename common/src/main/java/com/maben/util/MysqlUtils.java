package com.maben.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.system.ApplicationHome;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * @description:
 * @author: maben
 * @createDate: 2020/4/21
 */
public class MysqlUtils {
    private static Logger logger = LoggerFactory.getLogger(MysqlUtils.class);

    /**
     * @param hostIP ip地址，可以是本机也可以是远程
     * @param userName 数据库的用户名
     * @param password 数据库的密码
     * @param savePath 备份的路径
     * @param fileName 备份的文件名
     * @param databaseName 需要备份的数据库的名称
     * @return boolean
     */
    public static boolean backup(String hostIP, String userName, String password, String savePath, String fileName, String databaseName) {
        return backup(hostIP, userName, password, savePath, fileName, databaseName, SYSTEM_TYPE.WINDOWS);
    }

    /**
     * @param hostIP ip地址，可以是本机也可以是远程
     * @param userName 数据库的用户名
     * @param password 数据库的密码
     * @param savePath 备份的路径
     * @param fileName 备份的文件名
     * @param databaseName 需要备份的数据库的名称
     * @param type  系统类型
     * @return boolean
     */
    public static boolean backup(String hostIP, String userName, String password, String savePath, String fileName, String databaseName, SYSTEM_TYPE type) {
        fileName += ".sql";
        File saveFile = new File(savePath);
        if (!saveFile.exists()) {// 如果目录不存在
            saveFile.mkdirs();// 创建文件夹
        }
        if (!savePath.endsWith(File.separator)) {
            savePath = savePath + File.separator;
        }
        //拼接命令行的命令
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("mysqldump").append(" --opt").append(" -h").append(hostIP);
        if (SYSTEM_TYPE.WINDOWS == type) {
            stringBuilder.append(" --user=").append("\"").append(userName).append("\"").append(" --password=").append("\"").append(password).append("\"").append(" --lock-all-tables=false ");
        } else {
            stringBuilder.append(" --user=").append("'").append(userName).append("'").append(" --password=").append("'").append(password).append("'").append(" --lock-all-tables=false ");

        }
        stringBuilder.append(" --result-file=").append(savePath + fileName).append(" --default-character-set=utf8 ").append(databaseName);
        try {
            //调用外部执行exe文件的javaAPI
            logger.info("------>" + stringBuilder.toString());
            String[] cmd;
            if (SYSTEM_TYPE.LINUX == type) {
                // 加上词句拼装执行复杂shell
                cmd = new String[] { "sh", "-c", stringBuilder.toString() };
            } else {
                cmd = new String[] { "cmd", "/c", stringBuilder.toString() };
            }
            Process process = Runtime.getRuntime().exec(cmd);
            if (process.waitFor() == 0) {// 0 表示线程正常终止。
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean recover(String filepath, String ip, String database, String userName, String password) {
        return recover(filepath, ip, database, userName, password, SYSTEM_TYPE.WINDOWS, null);
    }

    /**
     * @param filepath 数据库备份的脚本路径
     * @param ip IP地址
     * @param database 数据库名称
     * @param userName 用户名
     * @param password 密码
     * @param type 系统类型
     * @param response response
     * @return 恢复结果
     */
    public static boolean recover(String filepath, String ip, String database, String userName, String password, SYSTEM_TYPE type, HttpServletResponse response) {
        String stmt1 = "mysqladmin -h " + ip;
        String stmt2 = "mysql -h " + ip;
        if (type == SYSTEM_TYPE.LINUX) {
            stmt1 = stmt1 + " -u'" + userName + "' -p'" + password + "' create " + database;
            stmt2 = stmt2 + " -u'" + userName + "' -p'" + password + "' " + database + " < " + filepath;
        } else {
            stmt1 = stmt1 + " -u\"" + userName + "\" -p\"" + password + "\" create " + database;
            stmt2 = stmt2 + " -u\"" + userName + "\" -p\"" + password + "\" " + database + " < " + filepath;
        }
        String[] cmd1 = { "cmd", "/c", stmt1 };
        String[] cmd2 = { "cmd", "/c", stmt2 };
        try {
            Runtime.getRuntime().exec(cmd1);
            final Process process = Runtime.getRuntime().exec(cmd2);
            logger.info("process.isAlive()---" + process.isAlive());
            if (process.waitFor() == 0) {
                logger.info("数据已从 " + filepath + " 导入到数据库中");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * @param filepath 数据库备份的脚本路径
     * @param ip IP地址
     * @param database 数据库名称
     * @param userName 用户名
     * @param password 密码
     * @param type 系统类型
     * @param response response
     * @return 恢复结果
     */
    public static boolean recover2(String filepath, String ip, String database, String userName, String password, SYSTEM_TYPE type, HttpServletResponse response) {

        String[] cmd1;
        try {
            if (type == SYSTEM_TYPE.LINUX) {
                cmd1 = new String[] { "cmd", "/c", "mysql -u'" + userName + "' -p'" + password + "' -h'" + ip + "' " + database + " < " + filepath };
            } else {
                cmd1 = new String[] { "cmd", "/c", "mysql -u\"" + userName + "\" -p\"" + password + "\" -h\"" + ip + "\" " + database + " < " + filepath };
            }
            final Process process = Runtime.getRuntime().exec(cmd1);
            if (process.waitFor() == 0) {
                logger.info("数据已从 " + filepath + " 导入到数据库中");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }



}



package com.maben.util;

/**
 * 获取当前登录系统
 */
public class OSUtil {
    private static final boolean osIsMacOsX;
    private static final boolean osIsWindows;
    private static final boolean osIsWindowsXP;
    private static final boolean osIsWindows2003;
    private static final boolean osIsWindowsVista;
    private static final boolean osIsLinux;
    private static final boolean osIsWindowsWin7;
    private static final boolean osIsWindowsWin8;
    private static final boolean osIsWindowsWin10;

    static {
        String os = System.getProperty("os.name");
        if (os != null) {
            os = os.toLowerCase();
        }
        osIsMacOsX = "mac os x".equals(os);
        osIsWindows = os != null && os.contains("windows");
        osIsWindowsXP = "windows xp".equals(os);
        osIsWindows2003 = "windows 2003".equals(os);
        osIsWindowsVista = "windows vista".equals(os);
        osIsLinux = os != null && os.contains("linux");
        osIsWindowsWin7 = os != null && os.contains("windows 7");
        osIsWindowsWin8 = os != null && os.contains("windows 8");
        osIsWindowsWin10 = os != null && os.contains("windows 10");
    }

    public static boolean isMacOSX() {
        return osIsMacOsX;
    }

    public static boolean isWindows() {
        return osIsWindows;
    }

    public static boolean isWindowsXP() {
        return osIsWindowsXP;
    }

    public static boolean isWindows2003() {
        return osIsWindows2003;
    }

    public static boolean isWindowsVista() {
        return osIsWindowsVista;
    }

    public static boolean isLinux() {
        return osIsLinux;
    }

    public static boolean IsWindowsWin7() {
        return osIsWindowsWin7;
    }

    public static boolean IsWindowsWin8() {
        return osIsWindowsWin8;
    }

    public static boolean IsWindowsWin10() {
        return osIsWindowsWin10;
    }
    public static void main(String[] args){
        for (String str :args){
            System.out.println(str);
        }
        System.out.println("---------------------------------");
        System.out.println(System.getProperty("os.name"));
        System.out.println("---------------------------------");
        System.out.println("当前系统为Mac系统："+OSUtil.isMacOSX());
        System.out.println("当前系统为Linux系统："+ OSUtil.isLinux());
        System.out.println("当前系统为Windows系统："+OSUtil.isWindows());
        System.out.println("当前系统为Windows2003系统："+OSUtil.isWindows2003());
        System.out.println("当前系统为WindowsXP系统："+OSUtil.isWindowsXP());
    }
}

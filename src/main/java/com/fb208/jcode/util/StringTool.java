package com.fb208.jcode.util;

public class StringTool {
    /*
     * 删除末尾字符串
     */
    public static String trimEnd(String inStr, String suffix) {
        while (inStr.endsWith(suffix)) {
            inStr = inStr.substring(0, inStr.length() - suffix.length());
        }
        return inStr;
    }

    /*
     * 删除开头字符
     */
    public static String trimStart(String inStr, String suffix) {
        while (inStr.startsWith(suffix)) {
            inStr = inStr.substring(suffix.length(), inStr.length());
        }
        return inStr;
    }

    public static String clearChar(String inStr, String... suffix) {
        for (int i = 0; i < suffix.length; i++) {
            inStr = inStr.replace(suffix[i], "");
        }
        return inStr;
    }
}

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

}

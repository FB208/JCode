package com.fb208.jcode.util;


import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 驼峰与下划线命名互转
 */
public class NameTool {
    private static Pattern linePattern = Pattern.compile("_(\\w)");
    /** 下划线转驼峰 */
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /** 驼峰转下划线(简单写法，效率低于{@link #humpToLine2(String)}) */
    public static String humpToLine(String str) {
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }

    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /** 驼峰转下划线,效率比上面高 */
    public static String humpToLine2(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 首字母转大写
     * @param str
     * @return
     */
    public static String firstCharToUpperCase(String str) {
        if (StringUtils.isBlank(str)){
            return str;
        }
        char[]chars = str.toCharArray();
        if ( chars[0] >= 'a' && chars[0] <= 'z'){
            chars[0] -= 32;
            return String.valueOf(chars);
        }else {
            return str;
        }
    }

    /**
     * 首字母转小写
     * @param str
     * @return
     */
    public static String firstCharToLowerCase(String str) {
        if (StringUtils.isBlank(str)){
            return str;
        }
        char[]chars = str.toCharArray();
        if ( chars[0] >= 'A' && chars[0] <= 'Z') {
            chars[0] += 32;
            return String.valueOf(chars);
        }else {
            return str;
        }
    }


}

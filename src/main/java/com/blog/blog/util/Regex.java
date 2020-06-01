package com.blog.blog.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    /**
     * 判断字符串是否为数字
     * 数字返回true
     * 反之 返回false
     * @param str
     * @return
     */
    public static boolean StringisNumberType(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }
}

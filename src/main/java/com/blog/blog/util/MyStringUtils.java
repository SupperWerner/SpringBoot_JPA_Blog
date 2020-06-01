package com.blog.blog.util;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class MyStringUtils {

    /**
     * 字符串 1,2,3,4
     * 转为
     * Long类型的List
     *
     * @param strs
     * @return
     */
    public static List<Long> StringToLongList(String strs){
        List<Long> list = new ArrayList<>();
        if (!StringUtils.isEmpty(strs)){
            String[] strArray = strs.split(",");
            for (String s : strArray) {
                list.add(new Long(s));
            }

        }
        return list;
    }


    /**
     * 判断字符传是否为null 或者 “”
     * 如果为空 返回 true
     * 不为空返回 false
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        return StringUtils.isEmpty(str);
    }
}

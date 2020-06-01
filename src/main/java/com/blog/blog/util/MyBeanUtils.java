package com.blog.blog.util;

import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

public class MyBeanUtils {
    /**
     * 获取传入对象中所有属性为空的字符串数组
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames(Object source){
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();
        List<String> nullPropertyNames = new ArrayList<>();
        for (PropertyDescriptor pd : propertyDescriptors) {
            String propertyName = pd.getName();
            if (beanWrapper.getPropertyValue(propertyName)==null){
                nullPropertyNames.add(propertyName);
            }
        }
        return nullPropertyNames.toArray(new String[nullPropertyNames.size()]);
    }
}

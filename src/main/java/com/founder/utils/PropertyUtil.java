package com.founder.utils;

import java.util.Properties;
import java.util.List;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: administrator
 * Date: 2004-9-12
 * Time: 22:17:11
 */
public class PropertyUtil {

    public static String getPropertyValue(String propertyName) {
    	//空配置
    	if(propertyName==null || "".equals(propertyName))return "";
    	//获取配置
        String value;
        Properties prop = new Properties();
        InputStream in = null;
        try {
            in = PropertyUtil.class.getClassLoader().getResourceAsStream("com/founder/config/config.properties");
            prop.load(in);
            value = prop.getProperty(propertyName);
            in.close();
            return value.trim();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

/*    public static void main(String[] args){
        String val = getPropertyValue("indexpage");
        System.out.println(val);
    }*/
}

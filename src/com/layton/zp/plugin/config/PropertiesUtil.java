package com.layton.zp.plugin.config;

import com.intellij.ide.util.PropertiesComponent;

public class PropertiesUtil {
    public static String LAST_BRANCH = "last_branch";

    public static void setValue(String key,String value){
         PropertiesComponent.getInstance().setValue(key,value);
    }

    public static String getValue(String key){
       return PropertiesComponent.getInstance().getValue(key);
    }
}

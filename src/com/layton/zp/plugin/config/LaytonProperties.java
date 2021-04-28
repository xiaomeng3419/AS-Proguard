package com.layton.zp.plugin.config;

import com.intellij.openapi.ui.MessageType;
import com.layton.zp.plugin.util.LaytonNotification;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LaytonProperties implements ParseProperties{

    @Override
    public Properties readProperties() {
        Properties properties = new Properties();
        File file = new File("/Users/zhangpan/.jetbrains/layton.properties");
        if (file.exists()){
            InputStream in = null;
            try {
                in = new FileInputStream(file);
                properties.load(in);
            }catch (Exception e){
                e.printStackTrace();
            }finally{
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }

    public static void main(String[] args) {
        System.out.println(LaytonProperties.class.getResource("/").getPath());
    }
}

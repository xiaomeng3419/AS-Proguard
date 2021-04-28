package com.layton.zp.plugin.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TZPProperties implements ParseProperties {

    @Override
    public Properties readProperties() {
        Properties properties = new Properties();
        File file = new File(System.getProperty("user.dir").concat(File.separator).concat("resources").concat(File.separator).concat("layton.properties"));
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
}

package com.layton.zp.plugin.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.intellij.notification.NotificationType;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PythonProcessor {

    public static Boolean prepareFile(String branch){
        boolean result = false;
        try {
//            LaytonNotification.notification("python3 "+"/Users/zhangpan/.jetbrains/proguard.py -b "+branch,NotificationType.INFORMATION);
            Process pr = Runtime.getRuntime().exec("python3 "+"/Users/zhangpan/.jetbrains/proguard.py -t "+ Type.DecodeType.MOVE.getCode()+" -b "+branch);
            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
//                LaytonNotification.notification(line,NotificationType.INFORMATION);
                if(line.isEmpty()){
                     result = false;
                }
                JSONObject jsonObject = JSON.parseObject(line);
                if(!jsonObject.isEmpty()){
                    if(Integer.parseInt(jsonObject.get("code").toString()) == 0){
                        result = true;
                    }else {
//                        LaytonNotification.notification(jsonObject.get("data").toString(), NotificationType.ERROR);
                        result = false;
                    }
                }
            }
            in.close();
            pr.waitFor();
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result = false;
        }
        return  result;

    }

    public static List<String> searchKeyword(String branch,String keyword){
        List<String> result = new ArrayList<>();
        try {
            LaytonNotification.notification("python3 "+"/Users/zhangpan/.jetbrains/proguard.py -b "+branch,NotificationType.INFORMATION);
            Process pr = Runtime.getRuntime().exec("python3 "+"/Users/zhangpan/.jetbrains/proguard.py -t "+Type.DecodeType.SEARCH.getCode()+" -b "+branch+" -k "+keyword);
            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            StringBuffer resultLine = new StringBuffer("");
            while ((line = in.readLine()) != null) {
                LaytonNotification.notification(line,NotificationType.INFORMATION);
                resultLine.append(line);
            }
            if(resultLine.toString().isEmpty()){
                return result;
            }
            JSONObject jsonObject = JSON.parseObject(resultLine.toString());
            if(!jsonObject.isEmpty()){
                if(Integer.valueOf(jsonObject.get("code").toString()) == 0){
                    JSONArray array = jsonObject.getJSONArray("data");
                    for (Object jsonElement : array) {
                        System.out.println(jsonElement.toString());
                        result.add(jsonElement.toString());
                    }
                }else {
                    return result;
                }
            }
            in.close();
            pr.waitFor();
        }catch (Exception e){
            e.printStackTrace();
            LaytonNotification.notification(e.getMessage(), NotificationType.ERROR);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(prepareFile("v3.6.7"));
    }
}

package com.layton.zp.plugin.config;

import java.util.*;

public class LaytonConfiguration {
    public static Hashtable<String, Object> configuration = configuration();

    public static Hashtable<String, Object> configuration() {
        Hashtable<String, Object> configuration = new Hashtable();
        Properties baseConfig = new LaytonProperties().readProperties();
        configuration.put(Key.PROGUARD_JAR_PATH, baseConfig.getProperty(Key.PROGUARD_JAR_PATH, ""));
        configuration.put(Key.PROCESS_JAR, baseConfig.getProperty(Key.PROCESS_JAR, ""));
        configuration.put(Key.PROJECT_GIT_PATH, baseConfig.getProperty(Key.PROJECT_GIT_PATH, ""));
        configuration.put(Key.PROJECT_GIT_BRANCH, baseConfig.getProperty(Key.PROJECT_GIT_BRANCH, ""));
        configuration.put(Key.PROJECT_MAPPING_TXT_PATH, baseConfig.getProperty(Key.PROJECT_MAPPING_TXT_PATH, ""));
        configuration.put(Key.NEED_UNZIP, baseConfig.getProperty(Key.NEED_UNZIP, String.valueOf(false)));
        configuration.put(Key.UNZIP_CMD, baseConfig.getProperty(Key.UNZIP_CMD, ""));
        configuration.put(Key.MAPPING_TEMP_FILE_PATH, baseConfig.getProperty(Key.MAPPING_TEMP_FILE_PATH, ""));
        configuration.put(Key.LOCAL_MAPPING_PATH, baseConfig.getProperty(Key.LOCAL_MAPPING_PATH, ""));
        configuration.put(Key.LOCAL_DOWNLOAD_MAPPING_PATH, baseConfig.getProperty(Key.LOCAL_DOWNLOAD_MAPPING_PATH, ""));
        // 2021 4 28
        configuration.put(Key.LOCAL_SEARCH_KEYWORD_SCRIPT, baseConfig.getProperty(Key.LOCAL_SEARCH_KEYWORD_SCRIPT, ""));


        boolean isReady = true;
        for (Map.Entry<String, Object> entry : configuration.entrySet()) {
            if(entry.getValue().toString().isEmpty() && Key.MUSTFIELD.contains(entry.getKey())){
//                LaytonNotification.notification(entry.getKey()+"must be specified", MessageType.ERROR);
//                System.out.println(entry.getKey()+"must be specified");
                isReady = false;
                break;
            }
        }
        configuration.put("ready",isReady);
//        configuration = configuration;
        return configuration;
    }





    public static void main(String[] args) {
  /*      new LaytonConfiguration().configuration().entrySet().forEach(entry -> {
            System.out.print(entry.getKey());
            System.out.print("---");
            System.out.println(entry.getValue());
        });*/
//        System.out.println(new LaytonConfiguration().configuration().containsKey(Key.PROJECT_GIT_PATH));

    }


    /**
     * configuration properties keys
     *
     * @author zhangpan
     */
    public static class Key {
        /**
         * jar path
         **/
        public static String PROGUARD_JAR_PATH = "com.layton.zp.constant.proguard.jar.path";
        /**
         *
         **/
        public static String PROCESS_JAR = "com.layton.zp.constant.process.jar";
        /**
         *
         **/
        public static String PROJECT_GIT_PATH = "com.layton.zp.constant.project.git.path";
        /**
         *
         **/
        public static String PROJECT_GIT_BRANCH = "com.layton.zp.constant.project.git.branch";
        /**
         *
         **/
        public static String PROJECT_MAPPING_TXT_PATH = "com.layton.zp.constant.project.mapping.txt.path";
        /**
         *
         **/
        public static String NEED_UNZIP = "com.layton.zp.constant.mapping.unzip";
        /**
         *
         **/
        public static String UNZIP_CMD = "com.layton.zp.constant.mapping.unzip.cmd";
        /**
         *
         **/
        public static String MAPPING_TEMP_FILE_PATH = "com.layton.zp.constant.git.project.temp.log.path";
        /**
         *
         */
        public static String LOCAL_MAPPING_PATH = "com.layton.zp.constant.local.mapping.path";
        /**
         * com.layton.zp.constant.local.download.mapping.path
         */
        public static String LOCAL_DOWNLOAD_MAPPING_PATH = "com.layton.zp.constant.local.download.mapping.path";

        public static String LOCAL_SEARCH_KEYWORD_SCRIPT = "com.layton.zp.constant.project.search.script";


        public static List<String> MUSTFIELD = new ArrayList<>();
        {
            MUSTFIELD.add(UNZIP_CMD);
            MUSTFIELD.add(MAPPING_TEMP_FILE_PATH);
            MUSTFIELD.add(PROJECT_MAPPING_TXT_PATH);
            MUSTFIELD.add(PROJECT_GIT_PATH);
            MUSTFIELD.add(NEED_UNZIP);
            MUSTFIELD.add(LOCAL_MAPPING_PATH);
        }
    }
}

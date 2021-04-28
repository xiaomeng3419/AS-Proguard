package com.layton.zp.plugin.processor.git;

import com.intellij.notification.NotificationType;
import com.intellij.openapi.ui.MessageType;
import com.layton.zp.plugin.config.LaytonConfiguration;
import com.layton.zp.plugin.util.LaytonNotification;
import com.layton.zp.plugin.util.ShellProcessorUtil;


import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * @author zhangpan
 * first: generate File Dir
 * second: copy mapping.txt.xz -> branch_name/
 * three: unzip
 */
public class LocalEngine extends AbstractEngine {

    public LocalEngine(Hashtable<String, Object> configuration) {
        this.configuration = configuration;
    }
    @Override
    public boolean generateMapping() {
        if (configuration.get(LaytonConfiguration.Key.PROJECT_GIT_BRANCH).toString() == null) {
            if (configuration.containsKey(LaytonConfiguration.Key.LOCAL_MAPPING_PATH)) {
//                this.branch = configuration.get(LaytonConfiguration.Key.LOCAL_MAPPING_PATH).toString();
            } else {
                System.out.println("Project needs to specify branch");
                LaytonNotification.notification("Project mapping file path needs to specify branch", NotificationType.ERROR);
                return false;
            }
        }

        if (Boolean.valueOf(configuration.get(LaytonConfiguration.Key.NEED_UNZIP).toString())) {
            List<String> commands = new ArrayList<>();
            String mappingDirPath = configuration.get(LaytonConfiguration.Key.LOCAL_MAPPING_PATH).toString().concat(File.separator)
                    .concat(configuration.get(LaytonConfiguration.Key.PROJECT_GIT_BRANCH).toString());
            if(!createDirIfNotExist(mappingDirPath))
                return false;
            String mappingXZPath = configuration.get(LaytonConfiguration.Key.LOCAL_DOWNLOAD_MAPPING_PATH).toString().concat(File.separator).concat("mapping.txt.xz");
            File mappingFile = new File(mappingDirPath+File.separator+"mapping.txt");
            File mappingXZFile = new File(mappingXZPath);
            if (mappingFile.exists()) {
                LaytonNotification.notification("use last mapping.txt",NotificationType.INFORMATION);
                return true;

            }else {
                LaytonNotification.notification(mappingDirPath+File.separator+"mapping.txt",NotificationType.INFORMATION);

                /*if(!Boolean.valueOf(configuration.get(LaytonConfiguration.Key.NEED_UNZIP).toString())){
                    return true;
                }else {
                    mappingFile.delete();
                }*/
            }
            if(!mappingXZFile.exists()){
                LaytonNotification.notification("Not can use mapping file! path:"+configuration.get(LaytonConfiguration.Key.LOCAL_DOWNLOAD_MAPPING_PATH).toString(),NotificationType.ERROR);
                return false;
            }
            commands.add("cd "+configuration.get(LaytonConfiguration.Key.LOCAL_DOWNLOAD_MAPPING_PATH).toString());
            commands.add("mv -f mapping.txt.xz "+configuration.get(LaytonConfiguration.Key.LOCAL_MAPPING_PATH).toString().concat(File.separator).concat(configuration.get(LaytonConfiguration.Key.PROJECT_GIT_BRANCH).toString()));
            commands.add("cd  " + configuration.get(LaytonConfiguration.Key.LOCAL_MAPPING_PATH).toString().concat(File.separator)
                    .concat(configuration.get(LaytonConfiguration.Key.PROJECT_GIT_BRANCH).toString()));

            commands.add(configuration.get(LaytonConfiguration.Key.UNZIP_CMD) + "mapping.txt.xz");
            if(ShellProcessorUtil.shell(commands).isEmpty()){
                return true;
            }
        }
        return true;
    }

    public static boolean createDirIfNotExist(String dirName){
        File dirFile = new File(dirName);
        if (dirFile.exists()) {
            if (dirFile.isDirectory()) {
                return true;
            } else {
                LaytonNotification.notification(dirName+"the same name file exists, can not create dir", NotificationType.ERROR);
                return false;
            }
        } else {
            dirFile.mkdir();
            return true;
        }
    }

 /*   public static void main(String[] args) {
        List<String> commands = new ArrayList<>();
        commands.add("xz -f  mapping.txt.xz");
    }*/
}

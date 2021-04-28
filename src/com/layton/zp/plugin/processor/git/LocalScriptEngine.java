package com.layton.zp.plugin.processor.git;

import com.intellij.notification.NotificationType;
import com.layton.zp.plugin.config.LaytonConfiguration;
import com.layton.zp.plugin.util.LaytonNotification;
import com.layton.zp.plugin.util.PythonProcessor;

import java.io.File;
import java.util.Hashtable;

public class LocalScriptEngine extends AbstractEngine {
    public  boolean mappingXZFileExist = false;
    public  boolean mappingFileExist = false;
    public LocalScriptEngine(Hashtable<String, Object> configuration) {
        this.configuration = configuration;
    }

    @Override
    public boolean generateMapping() {
        if (configuration.get(LaytonConfiguration.Key.PROJECT_GIT_BRANCH).toString() == null) {
            if (configuration.containsKey(LaytonConfiguration.Key.LOCAL_MAPPING_PATH)) {
            } else {
                LaytonNotification.notification("Project mapping file path needs to specify branch", NotificationType.ERROR);
                return false;
            }
        }
        beforePrepare();
        if(mappingFileExist){
            return true;
        }
        if(!mappingXZFileExist){
            LaytonNotification.notification("Project mapping xz not such file", NotificationType.ERROR);
            return false;
        }
        Boolean localScriptResp = PythonProcessor.prepareFile(configuration.get(LaytonConfiguration.Key.PROJECT_GIT_BRANCH).toString());
        return localScriptResp;
    }

    public void beforePrepare(){
        mappingFileExist = false;
        mappingXZFileExist = false;
        String mappingDirPath = configuration.get(LaytonConfiguration.Key.LOCAL_MAPPING_PATH).toString().concat(File.separator)
                .concat(configuration.get(LaytonConfiguration.Key.PROJECT_GIT_BRANCH).toString());

        String mappingXZPath = configuration.get(LaytonConfiguration.Key.LOCAL_DOWNLOAD_MAPPING_PATH).toString().concat(File.separator).concat("mapping.txt.xz");

        File mappingFile = new File(mappingDirPath+File.separator+"mapping.txt");
        File mappingXZFile = new File(mappingXZPath);
        if (mappingFile.exists()) {
            LaytonNotification.notification("use last mapping.txt", NotificationType.INFORMATION);
            mappingFileExist = true;
            return;
        }else {
            LaytonNotification.notification(mappingDirPath+File.separator+"mapping.txt",NotificationType.INFORMATION);
            mappingFileExist = false;
        }
        if(!mappingXZFile.exists()){
            LaytonNotification.notification("Not can use mapping file! path:"+configuration.get(LaytonConfiguration.Key.LOCAL_DOWNLOAD_MAPPING_PATH).toString(),NotificationType.ERROR);
            mappingXZFileExist = false;
            return ;
        }else {
            mappingXZFileExist = true;
        }
        return ;
    }
}

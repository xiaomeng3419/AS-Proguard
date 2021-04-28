package com.layton.zp.plugin.processor;

import com.intellij.notification.NotificationType;
import com.intellij.openapi.ui.MessageType;
import com.layton.zp.plugin.config.LaytonConfiguration;
import com.layton.zp.plugin.util.LaytonNotification;
import com.layton.zp.plugin.util.ShellProcessorUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class ProguardEngine {

    private Hashtable<String,Object> configuration = new Hashtable<>();

    private String processJarPath;
    private String mappingTxtPath;
    private String unzipCMD;
    private Boolean needUnzip = true;
    private Boolean useGit = false;


    public void setNeedUnzip(Boolean needUnzip) {
        this.needUnzip = needUnzip;
    }

    public void setProcessJarPath(String processJarPath) {
        this.processJarPath = processJarPath;
    }

    public ProguardEngine(Hashtable<String, Object> configuration,boolean needUnzip,boolean useGit) {
        this.configuration = configuration;
        this.needUnzip = needUnzip;
        this.useGit = useGit;
    }

    public String exe() {
        if(!beforeProcess()){
            return "error";
        }
        List<String> decode = decode();
        for (int i = 0; i < decode.size(); i++) {
          decode.set(i,decode.get(i).concat("\r\n"));
        }
        return decode.toString();
    }

    private List<String> decode() {
        List<String> commands = new ArrayList<>();
//        sh + mapping.txt +  log
        String mappingFilePath = "";
        if(this.useGit){
            mappingFilePath = configuration.get(LaytonConfiguration.Key.PROJECT_GIT_PATH).toString()
                    .concat(configuration.get(LaytonConfiguration.Key.PROJECT_MAPPING_TXT_PATH).toString())+File.separator+configuration.get(LaytonConfiguration.Key.PROJECT_GIT_BRANCH).toString()+ File.separator+"mapping.txt";
        }else {
            mappingFilePath = configuration.get(LaytonConfiguration.Key.LOCAL_MAPPING_PATH).toString()+File.separator+File.separator+configuration.get(LaytonConfiguration.Key.PROJECT_GIT_BRANCH).toString()+File.separator+"mapping.txt";
        }
        commands.add(configuration.get(LaytonConfiguration.Key.PROGUARD_JAR_PATH).toString()+ " "+
                mappingFilePath +
                " "+configuration.get(LaytonConfiguration.Key.MAPPING_TEMP_FILE_PATH).toString()+ File.separator+"temp.log");
        return ShellProcessorUtil.shell(commands);
    }

    private Boolean beforeProcess(){
        if(processJarPath == null || processJarPath.isEmpty()){
            if (configuration.containsKey(LaytonConfiguration.Key.PROCESS_JAR)) {
                this.processJarPath = configuration.get(LaytonConfiguration.Key.PROCESS_JAR).toString();
            } else {
                System.out.println("The target jar package path must be specified ");
                LaytonNotification.notification("The target jar package path must be specified ", NotificationType.ERROR);
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("13");
    }
/*    private boolean unzipMapping(){
        if(needUnzip){
            List<String> commands = new ArrayList<>();
            String mappingPath = configuration.get(LaytonConfiguration.Key.PROJECT_GIT_BRANCH).toString()
                    .concat(configuration.get(LaytonConfiguration.Key.PROJECT_MAPPING_TXT_PATH).toString());
            File mappingFile = new File(mappingPath, "mapping_txt");
            if (mappingFile.exists()) {
                mappingFile.delete();
            }
            commands.add("cd  " + configuration.get(LaytonConfiguration.Key.PROJECT_GIT_PATH).toString());
            commands.add("cd  " + configuration.get(LaytonConfiguration.Key.PROJECT_MAPPING_TXT_PATH).toString());
            commands.add(configuration.get(LaytonConfiguration.Key.UNZIP_CMD)+"  mapping.txt.xz");
            ShellUtil.shell(commands);
        }
        return true;
    }*/
}

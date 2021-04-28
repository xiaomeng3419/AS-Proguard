package com.layton.zp.plugin.processor.git;

import com.intellij.notification.NotificationType;
import com.intellij.openapi.ui.MessageType;
import com.layton.zp.plugin.config.LaytonConfiguration;
import com.layton.zp.plugin.util.LaytonNotification;
import com.layton.zp.plugin.util.ShellProcessorUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class GitEngine extends AbstractEngine {


    public GitEngine(Hashtable<String, Object> configuration) {
        this.configuration = configuration;
    }

    public boolean exeGitCmd() {
        //第一步获取命令行集。
        //第二部执行命令行并返回结果集
        List<String> rspList = new ArrayList<String>();
        List<String> commands = new ArrayList<>();
        if (this.configuration.get(LaytonConfiguration.Key.PROJECT_GIT_BRANCH).toString().isEmpty()) {
            if (configuration.containsKey(LaytonConfiguration.Key.PROJECT_GIT_BRANCH)) {
//                this.branch = configuration.get(LaytonConfiguration.Key.PROJECT_GIT_BRANCH).toString();
            } else {
                System.out.println("Project needs to specify branch");
                LaytonNotification.notification("Project needs to specify branch", NotificationType.ERROR);
                return false;
            }
        }
        if (!configuration.containsKey(LaytonConfiguration.Key.PROJECT_GIT_PATH) || configuration.get(LaytonConfiguration.Key.PROJECT_GIT_PATH) == null) {
            System.out.println("Project needs to specify path");
            LaytonNotification.notification("Project needs to specify path", NotificationType.ERROR);
            return false;
        }
//        git 执行操作流程。
        commands.add("cd " + configuration.get(LaytonConfiguration.Key.PROJECT_GIT_PATH).toString());
//        commands.add("git checkout .");
        commands.add("git checkout " + configuration.get(LaytonConfiguration.Key.PROJECT_GIT_BRANCH).toString());
        commands.add("git status");
        rspList = ShellProcessorUtil.shell(commands);
        unzipMapping();
        if (!rspList.isEmpty()) {
            rspList.forEach(System.out::println);
            if (rspList.get(0).contains(configuration.get(LaytonConfiguration.Key.PROJECT_GIT_BRANCH).toString())) {
                return true;
            }
        }

        return false;
    }

    private boolean unzipMapping() {
        if (Boolean.valueOf(configuration.get(LaytonConfiguration.Key.NEED_UNZIP).toString())) {
            List<String> commands = new ArrayList<>();
            String mappingPath = configuration.get(LaytonConfiguration.Key.PROJECT_GIT_BRANCH).toString()
                    .concat(configuration.get(LaytonConfiguration.Key.PROJECT_MAPPING_TXT_PATH).toString());
            File mappingFile = new File(mappingPath, "mapping_txt");
            if (mappingFile.exists()) {
                mappingFile.delete();
            }
            commands.add("cd  " + configuration.get(LaytonConfiguration.Key.PROJECT_GIT_PATH).toString());
            commands.add("cd  " + configuration.get(LaytonConfiguration.Key.PROJECT_MAPPING_TXT_PATH).toString());
            commands.add(configuration.get(LaytonConfiguration.Key.UNZIP_CMD) + "  mapping.txt.xz");
            ShellProcessorUtil.shell(commands);
        }
        return true;
    }

    @Override
    public boolean generateMapping() {
        return exeGitCmd();
    }
}

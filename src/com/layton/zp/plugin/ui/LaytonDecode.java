package com.layton.zp.plugin.ui;

import com.intellij.database.extensions.Clipboard;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.process.ScriptRunnerUtil;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.testframework.sm.runner.ui.SMTRunnerToolbarPanel;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.execution.ui.RunContentManager;
import com.intellij.execution.util.ExecUtil;
import com.intellij.ide.actions.ActivateToolWindowAction;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.application.ex.ClipboardUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.ToolWindowId;
import com.intellij.ui.content.Content;
import com.layton.zp.plugin.config.LaytonConfiguration;
import com.layton.zp.plugin.config.PropertiesUtil;
import com.layton.zp.plugin.config.TZPProperties;
import com.layton.zp.plugin.processor.Engine;
import com.layton.zp.plugin.processor.ProguardDecodeProcessor;
import com.layton.zp.plugin.processor.ProguardEngine;
import com.layton.zp.plugin.processor.git.GitEngine;
import com.layton.zp.plugin.processor.git.LocalEngine;
import com.layton.zp.plugin.processor.git.LocalScriptEngine;
import com.layton.zp.plugin.util.LaytonNotification;
import com.layton.zp.plugin.util.PythonProcessor;
import com.layton.zp.plugin.util.ShellProcessorUtil;
import com.layton.zp.plugin.util.TimeUtil;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

public class LaytonDecode {

    private JTextField proguardJarPath;
    private JTextField projectBranch;
    private JButton resetBtn;
    private JButton decodeBtn;
    private JButton saveBtn;
    private JButton docBtn;
    private JTextArea onlineLogTA;
    private JPanel laytonPanel;
    private JCheckBox unzipCB;
    private JCheckBox useGitCB;
    private JCheckBox canEditProguard;
    private JLabel searchKeyWord;
    private JTextField keywordTF;
    private JButton searchButton;

    private ConsoleView consoleView;
    private ToolWindow toolWindow;
    private    Content content;

    public static Project project = null;
    private String decodeLog = "";
    public LaytonDecode(Project project) {
        this.project = project;
        initLayout();

        decodeBtn.addActionListener(e -> {
            if (projectBranch.getText() == null || projectBranch.getText().isEmpty()) {
                LaytonNotification.notification("check your branch,must be specified !", NotificationType.ERROR);
            }
            PropertiesUtil.setValue(PropertiesUtil.LAST_BRANCH,projectBranch.getText());
            String onlineLog = onlineLogTA.getText();
            if (!Boolean.valueOf(LaytonConfiguration.configuration.get("ready").toString())) {
                System.out.println("check your properties!");
                LaytonNotification.notification("check your properties!", NotificationType.ERROR);
                return;
            }
            if (projectBranch.getText() != null && !projectBranch.getText().isEmpty()) {
                LaytonConfiguration.configuration.put(LaytonConfiguration.Key.PROJECT_GIT_BRANCH, projectBranch.getText());
            }
        /*    LaytonPropertiesComponent instance = ServiceManager.getService(LaytonPropertiesComponent.class);
            instance.setState(projectBranch.getText());*/
            Engine engine = null;
            if (useGitCB.isSelected()) {
                engine = new GitEngine(LaytonConfiguration.configuration);
            } else {
//                engine = new LocalEngine(LaytonConfiguration.configuration);
                engine = new LocalScriptEngine(LaytonConfiguration.configuration);
            }
            dump2File(LaytonConfiguration.configuration, onlineLog);
            String result = new ProguardDecodeProcessor(engine, new ProguardEngine(LaytonConfiguration.configuration, unzipCB.isSelected(), useGitCB.isSelected())).process();
            result = result.replace(",", "");
            result = result.replace("[", "");
            result = result.replace("]", "");
            decodeLog = result;
            outputDecodeLog(result);
        });

        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onlineLogTA.setText("");
            }
        });

        docBtn.addActionListener(action ->{
//          System.out.println("-------------");
//          getConsoleView().print("at com.netease.boo.ui.LocalAlbumActivity$getLocalAlbumMedia$1.invokeSuspend(LocalAlbumActivity.java:11)"+ConsoleViewContentType.LOG_DEBUG_OUTPUT.toString().concat("\r\n"),ConsoleViewContentType.LOG_DEBUG_OUTPUT);
//          getConsoleView().print("at com.netease.boo.ui.LocalAlbumActivity$getLocalAlbumMedia$1.invokeSuspend(LocalAlbumActivity.java:11)"+ConsoleViewContentType.LOG_VERBOSE_OUTPUT.toString().concat("\r\n"),ConsoleViewContentType.LOG_VERBOSE_OUTPUT);
//          getConsoleView().print("at com.netease.boo.ui.LocalAlbumActivity$getLocalAlbumMedia$1.invokeSuspend(LocalAlbumActivity.java:11)"+ConsoleViewContentType.LOG_INFO_OUTPUT.toString().concat("\r\n"),ConsoleViewContentType.LOG_INFO_OUTPUT);
//          getConsoleView().print("at com.netease.boo.ui.LocalAlbumActivity$getLocalAlbumMedia$1.invokeSuspend(LocalAlbumActivity.java:11)"+ConsoleViewContentType.LOG_WARNING_OUTPUT.toString().concat("\r\n"),ConsoleViewContentType.LOG_WARNING_OUTPUT);
//          getConsoleView().print("at com.netease.boo.ui.LocalAlbumActivity$getLocalAlbumMedia$1.invokeSuspend(LocalAlbumActivity.java:11)"+ConsoleViewContentType.LOG_ERROR_OUTPUT.toString().concat("\r\n"),ConsoleViewContentType.LOG_ERROR_OUTPUT);
//          getConsoleView().print("at com.netease.boo.ui.LocalAlbumActivity$getLocalAlbumMedia$1.invokeSuspend(LocalAlbumActivity.java:11)"+ConsoleViewContentType.NORMAL_OUTPUT.toString().concat("\r\n"),ConsoleViewContentType.NORMAL_OUTPUT);
//          getConsoleView().print("at com.netease.boo.ui.LocalAlbumActivity$getLocalAlbumMedia$1.invokeSuspend(LocalAlbumActivity.java:11)"+ConsoleViewContentType.SYSTEM_OUTPUT.toString().concat("\r\n"),ConsoleViewContentType.SYSTEM_OUTPUT);
//          getConsoleView().print("at com.netease.boo.ui.LocalAlbumActivity$getLocalAlbumMedia$1.invokeSuspend(LocalAlbumActivity.java:11)"+ConsoleViewContentType.ERROR_OUTPUT.toString().concat("\r\n"),ConsoleViewContentType.ERROR_OUTPUT);
            /*  ArrayList<String> cmds = new ArrayList<>();
            cmds.add("top");
            GeneralCommandLine generalCommandLine = new GeneralCommandLine(cmds);
            generalCommandLine.setCharset(Charset.forName("UTF-8"));
            generalCommandLine.setWorkDirectory(ProguardWindowFactory.project.getBasePath());

            try {
                ProcessHandler processHandler = new OSProcessHandler(generalCommandLine);
                processHandler.startNotify();
                ScriptRunnerUtil.getProcessOutput(generalCommandLine);
                ScriptRunnerUtil.LOG.debug("text");
            }catch (Exception e){
                e.printStackTrace();
            }*/

            ShellProcessorUtil.clipboard(decodeLog);
        });
        /**
         *  if need unzip selection changed
         */
        unzipCB.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                LaytonConfiguration.configuration.put(LaytonConfiguration.Key.NEED_UNZIP, unzipCB.isSelected());
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (projectBranch.getText() == null || projectBranch.getText().isEmpty()) {
                    LaytonNotification.notification("check your branch,must be specified !", NotificationType.ERROR);
                }
                PropertiesUtil.setValue(PropertiesUtil.LAST_BRANCH,projectBranch.getText());
                String onlineLog = onlineLogTA.getText();
                if (!Boolean.valueOf(LaytonConfiguration.configuration.get("ready").toString())) {
                    System.out.println("check your properties!");
                    LaytonNotification.notification("check your properties!", NotificationType.ERROR);
                    return;
                }
                if (projectBranch.getText() != null && !projectBranch.getText().isEmpty()) {
                    LaytonConfiguration.configuration.put(LaytonConfiguration.Key.PROJECT_GIT_BRANCH, projectBranch.getText());
                }
                if(!keywordTF.getText().isEmpty()){
                    List<String> searchResult = PythonProcessor.searchKeyword(projectBranch.getText(),keywordTF.getText());
                    outputArray(searchResult);
                }
            }
        });

        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

              getConsoleView().print("at com.netease.boo.ui.LocalAlbumActivity$getLocalAlbumMedia$1.invokeSuspend(LocalAlbumActivity.java:11)"+ConsoleViewContentType.LOG_DEBUG_OUTPUT.toString().concat("\r\n"),ConsoleViewContentType.LOG_DEBUG_OUTPUT);
              getConsoleView().print("at com.netease.boo.ui.LocalAlbumActivity$getLocalAlbumMedia$1.invokeSuspend(LocalAlbumActivity.java:11)"+ConsoleViewContentType.LOG_VERBOSE_OUTPUT.toString().concat("\r\n"),ConsoleViewContentType.LOG_VERBOSE_OUTPUT);
              getConsoleView().print("at com.netease.boo.ui.LocalAlbumActivity$getLocalAlbumMedia$1.invokeSuspend(LocalAlbumActivity.java:11)"+ConsoleViewContentType.LOG_INFO_OUTPUT.toString().concat("\r\n"),ConsoleViewContentType.LOG_INFO_OUTPUT);
              getConsoleView().print("at com.netease.boo.ui.LocalAlbumActivity$getLocalAlbumMedia$1.invokeSuspend(LocalAlbumActivity.java:11)"+ConsoleViewContentType.LOG_WARNING_OUTPUT.toString().concat("\r\n"),ConsoleViewContentType.LOG_WARNING_OUTPUT);
              getConsoleView().print("at com.netease.boo.ui.LocalAlbumActivity$getLocalAlbumMedia$1.invokeSuspend(LocalAlbumActivity.java:11)"+ConsoleViewContentType.LOG_ERROR_OUTPUT.toString().concat("\r\n"),ConsoleViewContentType.LOG_ERROR_OUTPUT);
              getConsoleView().print("at com.netease.boo.ui.LocalAlbumActivity$getLocalAlbumMedia$1.invokeSuspend(LocalAlbumActivity.java:11)"+ConsoleViewContentType.NORMAL_OUTPUT.toString().concat("\r\n"),ConsoleViewContentType.NORMAL_OUTPUT);
              getConsoleView().print("at com.netease.boo.ui.LocalAlbumActivity$getLocalAlbumMedia$1.invokeSuspend(LocalAlbumActivity.java:11)"+ConsoleViewContentType.SYSTEM_OUTPUT.toString().concat("\r\n"),ConsoleViewContentType.SYSTEM_OUTPUT);
              getConsoleView().print("at com.netease.boo.ui.LocalAlbumActivity$getLocalAlbumMedia$1.invokeSuspend(LocalAlbumActivity.java:11)"+ConsoleViewContentType.ERROR_OUTPUT.toString().concat("\r\n"),ConsoleViewContentType.ERROR_OUTPUT);
              getConsoleView().print("\r\n",ConsoleViewContentType.ERROR_OUTPUT);
            }
        });
    }

    private void initLayout() {
        System.out.println();
        LaytonNotification.notification(System.getProperty("user.dir").concat(File.separator).concat("resources").concat(File.separator).concat("layton.properties"),NotificationType.INFORMATION);
        LaytonNotification.notification(new TZPProperties().readProperties().toString(),NotificationType.INFORMATION);
        projectBranch.setText(PropertiesUtil.getValue(PropertiesUtil.LAST_BRANCH));
        Color background = new Color(38, 38, 38);
        onlineLogTA.setBackground(background);
        if (LaytonConfiguration.configuration.containsKey(LaytonConfiguration.Key.PROGUARD_JAR_PATH)
                && !LaytonConfiguration.configuration.get(LaytonConfiguration.Key.PROGUARD_JAR_PATH).toString().isEmpty()) {
            LaytonNotification.notification("LaytonConfiguration.Key.PROGUARD_JAR_PATH", NotificationType.INFORMATION);
            proguardJarPath.setText(LaytonConfiguration.configuration.get(LaytonConfiguration.Key.PROGUARD_JAR_PATH).toString());
            proguardJarPath.setEnabled(false);
            canEditProguard.setSelected(false);
        }
        canEditProguard.setSelected(false);
        canEditProguard.addChangeListener(listener -> {
            if (canEditProguard.isSelected()) {
                proguardJarPath.setEnabled(true);
            } else {
                proguardJarPath.setEnabled(false);
            }
        });
    }

    public JPanel getLaytonPanel() {
        return laytonPanel;
    }

    public void dump2File(Hashtable configuration, String onlineLog) {
        File onlineLogFile = new File(configuration.get(LaytonConfiguration.Key.MAPPING_TEMP_FILE_PATH) + File.separator + "temp.log");
        BufferedWriter bw = null;
        try {
            if (onlineLogFile.exists()) {
                onlineLogFile.delete();
            }
            onlineLogFile.createNewFile();
            bw = new BufferedWriter(new FileWriter(onlineLogFile));
            bw.write(onlineLog);
            bw.flush();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ConsoleView getConsoleView()  {
        if(consoleView != null){
            LaytonNotification.notification("not null ",NotificationType.INFORMATION);
            toolWindow.activate(null,true,false);
            if(!toolWindow.isVisible()){
                LaytonNotification.notification("not isVisible ",NotificationType.INFORMATION);
                toolWindow.show(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                Content content = toolWindow.getContentManager().getFactory().createContent(consoleView.getComponent(),"Proguard Decode",false);
                consoleView.getComponent().getRootPane().setBackground(Color.orange);
                toolWindow.getContentManager().addContent(content);
                toolWindow.getContentManager().setSelectedContent(content,true);
            }
            return this.consoleView;
        }else {
            try{
//                ToolWindowManager.getInstance(project).registerToolWindow(ToolWindowId.RUN,true, ToolWindowAnchor.BOTTOM);
//                RunContentManager.getInstance(project).getContentDescriptorToolWindowId()
                toolWindow = ToolWindowManager.getInstance(project).getToolWindow(ToolWindowId.RUN);
                if(toolWindow == null){
                     ToolWindowManager.getInstance(project).registerToolWindow(ToolWindowId.RUN,true, ToolWindowAnchor.BOTTOM);
                    toolWindow = ToolWindowManager.getInstance(project).getToolWindow(ToolWindowId.RUN);
                }
                toolWindow.activate(null,true,false);
                if(!toolWindow.isVisible()){
                    toolWindow.show(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }
                LaytonNotification.notification(" null  consoleView",NotificationType.INFORMATION);
                ConsoleView consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(project).getConsole();
                content = toolWindow.getContentManager().getFactory().createContent(consoleView.getComponent(),"Proguard Decode",false);
                LaytonNotification.notification(" null  getContentManager",NotificationType.INFORMATION);
                toolWindow.getContentManager().addContent(content);
                toolWindow.getContentManager().setSelectedContent(content,true);
                this.consoleView = consoleView;
                return this.consoleView;
            }catch (Exception e){
                LaytonNotification.notification(e.toString(),NotificationType.ERROR);
                e.printStackTrace();
            }
        }
        return this.consoleView;
    }

    public void outputDecodeLog(String result){
        getConsoleView().print("Layton·ZHANGFPAN",ConsoleViewContentType.LOG_ERROR_OUTPUT);
        getConsoleView().print(" - ["+TimeUtil.liveTime()+"] - ",ConsoleViewContentType.LOG_DEBUG_OUTPUT);
        getConsoleView().print("decode exception: ".concat("\r\n"),ConsoleViewContentType.LOG_INFO_OUTPUT);
        getConsoleView().print(result,ConsoleViewContentType.LOG_DEBUG_OUTPUT);
        getConsoleView().print("\r\n",ConsoleViewContentType.NORMAL_OUTPUT);
        getConsoleView().print("\r\n",ConsoleViewContentType.NORMAL_OUTPUT);
    }

    public void outputArray(List<String> result){
        getConsoleView().print("Layton·ZHANGFPAN",ConsoleViewContentType.LOG_ERROR_OUTPUT);
        getConsoleView().print(" - ["+TimeUtil.liveTime()+"] - ",ConsoleViewContentType.LOG_DEBUG_OUTPUT);
        getConsoleView().print("decode exception: ".concat("\r\n"),ConsoleViewContentType.LOG_INFO_OUTPUT);
        for (String line: result){
            getConsoleView().print(line,ConsoleViewContentType.LOG_ERROR_OUTPUT);
        }
        getConsoleView().print("\r\n",ConsoleViewContentType.NORMAL_OUTPUT);
        getConsoleView().print("\r\n",ConsoleViewContentType.NORMAL_OUTPUT);
    }

    public static void main(String[] args) {

        NotificationGroup.toolWindowGroup(getTestResultsNotificationDisplayId(ToolWindowId.RUN), ToolWindowId.RUN);
    }

    private static String getTestResultsNotificationDisplayId( String toolWindowId) {
        return "Test Results: " + toolWindowId;
    }

}

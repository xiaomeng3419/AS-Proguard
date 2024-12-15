package com.layton.zp.plugin.util;

import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowId;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.impl.ContentImpl;
import com.layton.zp.plugin.model.ProjectContext;
import com.layton.zp.plugin.ui.factory.JsonFormatWindowFactory;

import java.util.HashMap;
import java.util.Map;


public class TerminalUtil {
    private static Content content;
//
    private static Map<String, ProjectContext> contextMap = new HashMap<>();
//    private static ConsoleView consoleView;
//

//    private static ToolWindow toolWindow;


/*    public static ConsoleView getConsoleView() {
        if (consoleView != null) {
            LaytonNotification.notification("not null ", NotificationType.INFORMATION);
            toolWindow.activate(null, true, false);
            if (!toolWindow.isVisible()) {
                LaytonNotification.notification("not isVisible ", NotificationType.INFORMATION);
                toolWindow.show(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                Content content = toolWindow.getContentManager().getFactory().createContent(consoleView.getComponent(), "Proguard Decode", false);
                consoleView.getComponent().getRootPane().setBackground(Color.orange);
                toolWindow.getContentManager().addContent(content);
                toolWindow.getContentManager().setSelectedContent(content, true);
            }
            return this.consoleView;
        } else {
            try {
//                ToolWindowManager.getInstance(project).registerToolWindow(ToolWindowId.RUN,true, ToolWindowAnchor.BOTTOM);
//                RunContentManager.getInstance(project).getContentDescriptorToolWindowId()
                toolWindow = ToolWindowManager.getInstance(JsonFormatWindowFactory.getProject()).getToolWindow(ToolWindowId.RUN);
                if (toolWindow == null) {
                    ToolWindowManager.getInstance(JsonFormatWindowFactory.getProject()).registerToolWindow(ToolWindowId.RUN, true, ToolWindowAnchor.BOTTOM);
                    toolWindow = ToolWindowManager.getInstance(JsonFormatWindowFactory.getProject()).getToolWindow(ToolWindowId.RUN);
                }
                toolWindow.activate(null, true, false);
                if (!toolWindow.isVisible()) {
                    toolWindow.show(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }
                LaytonNotification.notification(" null  consoleView", NotificationType.INFORMATION);
                ConsoleView consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(JsonFormatWindowFactory.getProject()).getConsole();
                content = toolWindow.getContentManager().getFactory().createContent(consoleView.getComponent(), "Proguard Decode", false);
                LaytonNotification.notification(" null  getContentManager", NotificationType.INFORMATION);
                toolWindow.getContentManager().addContent(content);
                toolWindow.getContentManager().setSelectedContent(content, true);
                this.consoleView = consoleView;
                return this.consoleView;
            } catch (Exception e) {
                LaytonNotification.notification(e.toString(), NotificationType.ERROR);
                e.printStackTrace();
            }
        }
        return this.consoleView;
    }*/

    public static ConsoleView getConsoleView(Project project) {
        ConsoleView consoleView = null;
        ToolWindow toolWindow = null;
        ProjectContext projectContext = null;
        if(contextMap.containsKey(project.getName())){
            projectContext = contextMap.get(project.getName());
            if(projectContext!=null ){
                consoleView = projectContext.getConsoleView();
                toolWindow = projectContext.getToolWindow();
            }

        }
        if (consoleView != null) {
            LaytonNotification.notification("copy error: "+project.getBasePath(),NotificationType.ERROR);
            if (!toolWindow.isVisible()) {
                if(toolWindow.getContentManager().getContentCount()>=0){
                    boolean exist = false;
                    Content[] contents = toolWindow.getContentManager().getContents();
                    for (int i = 0; i < contents.length; i++) {
                        if (contents[i] instanceof ContentImpl && contents[i].getDisplayName().equals("Proguard Decode")) {
                            exist = true;
                        }
                    }
                    if(!exist){
//                        consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(JsonFormatWindowFactory.getProject()).getConsole();
                        consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(project).getConsole();
                        content = toolWindow.getContentManager().getFactory().createContent(consoleView.getComponent(), "Proguard Decode", false);
                        projectContext.setConsoleView(consoleView);
                        toolWindow.getContentManager().addContent(content);
                        toolWindow.getContentManager().setSelectedContent(content, true);
                    }
                    toolWindow.activate(null, true, false);
                } else {
                    createConsoleView(project);
                }

/*                toolWindow.show(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                boolean exist = false;
                Content[] contents = toolWindow.getContentManager().getContents();
                for (int i = 0; i < contents.length; i++) {
                    if (contents[i] instanceof ContentImpl && contents[i].getDisplayName().equals("Proguard Decode")) {
                        return consoleView;
                    }
                }
                consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(JsonFormatWindowFactory.getProject()).getConsole();
                content = toolWindow.getContentManager().getFactory().createContent(consoleView.getComponent(), "Proguard Decode", false);
                toolWindow.getContentManager().addContent(content);
                toolWindow.getContentManager().setSelectedContent(content, true);*/
/*                Content content = toolWindow.getContentManager().getFactory().createContent(consoleView.getComponent(), "Proguard Decode", false);
                consoleView.getComponent().getRootPane().setBackground(Color.orange);
                toolWindow.getContentManager().addContent(content);
                toolWindow.getContentManager().setSelectedContent(content, true);*/
            } else {
                if (toolWindow.getContentManager().getContentCount() <= 0) {
                    System.out.println("-----------1");
                    createConsoleView(project);
                } else {
                    boolean exist = false;
                    Content[] contents = toolWindow.getContentManager().getContents();
                    for (int i = 0; i < contents.length; i++) {
                        if (contents[i] instanceof ContentImpl && contents[i].getDisplayName().equals("Proguard Decode")) {
                            exist = true;
                        }
                    }
                    if(!exist){
                        consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(project).getConsole();
                        content = toolWindow.getContentManager().getFactory().createContent(consoleView.getComponent(), "Proguard Decode", false);
                        toolWindow.getContentManager().addContent(content);
                        toolWindow.getContentManager().setSelectedContent(content, true);
                    } else {
                        toolWindow.getContentManager().setSelectedContent(content, true);
                    }
                    toolWindow.activate(null, true, false);
                }
     /*           if(toolWindow.getContentManager().getContentCount()<=0){
                    content = toolWindow.getContentManager().getFactory().createContent(consoleView.getComponent(), "Proguard Decode", false);
                    toolWindow.getContentManager().addContent(content);
                    toolWindow.getContentManager().setSelectedContent(content, true);
                }*/
            }
            return consoleView;
        }
        return createConsoleView(project);
    }

    public static ConsoleView createConsoleView(Project project) {
        ConsoleView consoleView = null;
        try {
//                ToolWindowManager.getInstance(project).registerToolWindow(ToolWindowId.RUN,true, ToolWindowAnchor.BOTTOM);
//                RunContentManager.getInstance(project).getContentDescriptorToolWindowId()
//            toolWindow = ToolWindowManager.getInstance(JsonFormatWindowFactory.getProject()).getToolWindow(ToolWindowId.RUN);
            ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow(ToolWindowId.RUN);
            if (toolWindow == null) {
                ToolWindowManager.getInstance(project).registerToolWindow(ToolWindowId.RUN, true, ToolWindowAnchor.BOTTOM);
                toolWindow = ToolWindowManager.getInstance(project).getToolWindow(ToolWindowId.RUN);
            }
            toolWindow.activate(null, true, false);
            if (!toolWindow.isVisible()) {
                toolWindow.show(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
            consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(project).getConsole();
            content = toolWindow.getContentManager().getFactory().createContent(consoleView.getComponent(), "Proguard Decode", false);
            toolWindow.getContentManager().addContent(content);
            toolWindow.getContentManager().setSelectedContent(content, true);
            contextpuplate(project, consoleView, toolWindow, content);
            return consoleView;
        } catch (Exception e) {
            LaytonNotification.notification(e.toString(), NotificationType.ERROR);
            e.printStackTrace();
        }
        return consoleView;
    }

    private static void contextpuplate(Project project,ConsoleView consoleView, ToolWindow toolWindow, Content content){
        ProjectContext projectContext = new ProjectContext();
        projectContext.setConsoleView(consoleView);
        projectContext.setToolWindow(toolWindow);
        projectContext.setContent(content);
        contextMap.put(project.getName(), projectContext);
    }

    public static void outputDecodeLog(Project project,String result) {
        getConsoleView(project).print("Layton·ZHANGFPAN", ConsoleViewContentType.LOG_ERROR_OUTPUT);
        getConsoleView(project).print(" - [" + TimeUtil.liveTime() + "] - ", ConsoleViewContentType.LOG_DEBUG_OUTPUT);
        getConsoleView(project).print("decode exception: ".concat("\r\n"), ConsoleViewContentType.LOG_INFO_OUTPUT);
        getConsoleView(project).print(result, ConsoleViewContentType.LOG_DEBUG_OUTPUT);
        getConsoleView(project).print("\r\n", ConsoleViewContentType.NORMAL_OUTPUT);
        getConsoleView(project).print("\r\n", ConsoleViewContentType.NORMAL_OUTPUT);
    }

    public static void outputJson(Project project, String content) {
        output(project, "json format exception:", content);
/*        getConsoleView().print("Layton·ZHANGFPAN", ConsoleViewContentType.LOG_ERROR_OUTPUT);
        getConsoleView().print(" - [" + TimeUtil.liveTime() + "] - ", ConsoleViewContentType.LOG_DEBUG_OUTPUT);
        getConsoleView().print("json format exception: ".concat("\r\n"), ConsoleViewContentType.LOG_INFO_OUTPUT);
        getConsoleView().print(content, ConsoleViewContentType.LOG_DEBUG_OUTPUT);
        getConsoleView().print("\r\n", ConsoleViewContentType.NORMAL_OUTPUT);
        getConsoleView().print("\r\n", ConsoleViewContentType.NORMAL_OUTPUT);*/
    }


    public static void output(Project project, String tag, String content) {
        getConsoleView(project).print("Layton·ZHANGFPAN", ConsoleViewContentType.LOG_ERROR_OUTPUT);
        getConsoleView(project).print(" - [" + TimeUtil.liveTime() + "] - ", ConsoleViewContentType.LOG_DEBUG_OUTPUT);
        getConsoleView(project).print(tag + ": ".concat("\r\n"), ConsoleViewContentType.LOG_INFO_OUTPUT);
        getConsoleView(project).print(content, ConsoleViewContentType.LOG_DEBUG_OUTPUT);
        getConsoleView(project).print("\r\n", ConsoleViewContentType.NORMAL_OUTPUT);
        getConsoleView(project).print("\r\n", ConsoleViewContentType.NORMAL_OUTPUT);
    }




/*
    public static void createTerminal() {
        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(JsonFormatWindowFactory.getProject());
        ToolWindow toolWindow = toolWindowManager.registerToolWindow("Proguard", true, ToolWindowAnchor.BOTTOM);
        toolWindow.setIcon(new );
        toolWindow.setAvailable(true, null);


        TerminalWidgetFactory factory = TerminalWidgetFactory.getInstance();
        TerminalWidget terminalWidget = factory.create(project);
        JPanel panel = terminalWidget.getComponent();
    }
*/


}


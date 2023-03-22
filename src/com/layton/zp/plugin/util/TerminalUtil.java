package com.layton.zp.plugin.util;

import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowId;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.impl.ContentImpl;
import com.layton.zp.plugin.ui.factory.JsonFormatWindowFactory;


public class TerminalUtil {
    private static Content content;
    private static ConsoleView consoleView;
    private static ToolWindow toolWindow;

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

    public static ConsoleView getConsoleView() {
        if (consoleView != null) {
            if (!toolWindow.isVisible()) {
                toolWindow.show(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                boolean exist = false;
                Content[] contents = toolWindow.getContentManager().getContents();
                for (int i = 0; i < contents.length; i++) {
                    if(contents[i] instanceof ContentImpl && contents[i].getDisplayName().equals("Proguard Decode")){
                        return consoleView;
                    }
                }
                consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(JsonFormatWindowFactory.getProject()).getConsole();
                content = toolWindow.getContentManager().getFactory().createContent(consoleView.getComponent(), "Proguard Decode", false);
                toolWindow.getContentManager().addContent(content);
                toolWindow.getContentManager().setSelectedContent(content, true);
/*                Content content = toolWindow.getContentManager().getFactory().createContent(consoleView.getComponent(), "Proguard Decode", false);
                consoleView.getComponent().getRootPane().setBackground(Color.orange);
                toolWindow.getContentManager().addContent(content);
                toolWindow.getContentManager().setSelectedContent(content, true);*/
            }
            return consoleView;
        }
        return createConsoleView();
    }

    public static ConsoleView createConsoleView(){
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
            consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(JsonFormatWindowFactory.getProject()).getConsole();
            content = toolWindow.getContentManager().getFactory().createContent(consoleView.getComponent(), "Proguard Decode", false);
            toolWindow.getContentManager().addContent(content);
            toolWindow.getContentManager().setSelectedContent(content, true);
            return consoleView;
        } catch (Exception e) {
            LaytonNotification.notification(e.toString(), NotificationType.ERROR);
            e.printStackTrace();
        }
        return consoleView;
    }

    public static void outputDecodeLog(String result) {
        getConsoleView().print("Layton·ZHANGFPAN", ConsoleViewContentType.LOG_ERROR_OUTPUT);
        getConsoleView().print(" - [" + TimeUtil.liveTime() + "] - ", ConsoleViewContentType.LOG_DEBUG_OUTPUT);
        getConsoleView().print("decode exception: ".concat("\r\n"), ConsoleViewContentType.LOG_INFO_OUTPUT);
        getConsoleView().print(result, ConsoleViewContentType.LOG_DEBUG_OUTPUT);
        getConsoleView().print("\r\n", ConsoleViewContentType.NORMAL_OUTPUT);
        getConsoleView().print("\r\n", ConsoleViewContentType.NORMAL_OUTPUT);
    }

    public static void outputJson(String content) {
        output("json format exception:", content);
/*        getConsoleView().print("Layton·ZHANGFPAN", ConsoleViewContentType.LOG_ERROR_OUTPUT);
        getConsoleView().print(" - [" + TimeUtil.liveTime() + "] - ", ConsoleViewContentType.LOG_DEBUG_OUTPUT);
        getConsoleView().print("json format exception: ".concat("\r\n"), ConsoleViewContentType.LOG_INFO_OUTPUT);
        getConsoleView().print(content, ConsoleViewContentType.LOG_DEBUG_OUTPUT);
        getConsoleView().print("\r\n", ConsoleViewContentType.NORMAL_OUTPUT);
        getConsoleView().print("\r\n", ConsoleViewContentType.NORMAL_OUTPUT);*/
    }


    public static void output(String tag, String content) {
        getConsoleView().print("Layton·ZHANGFPAN", ConsoleViewContentType.LOG_ERROR_OUTPUT);
        getConsoleView().print(" - [" + TimeUtil.liveTime() + "] - ", ConsoleViewContentType.LOG_DEBUG_OUTPUT);
        getConsoleView().print(tag + ": ".concat("\r\n"), ConsoleViewContentType.LOG_INFO_OUTPUT);
        getConsoleView().print(content, ConsoleViewContentType.LOG_DEBUG_OUTPUT);
        getConsoleView().print("\r\n", ConsoleViewContentType.NORMAL_OUTPUT);
        getConsoleView().print("\r\n", ConsoleViewContentType.NORMAL_OUTPUT);
    }
}


package com.layton.zp.plugin.model;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.content.Content;


public class ProjectContext {
    private ToolWindow toolWindow;
    private ConsoleView consoleView;

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    private Content content;


    public ToolWindow getToolWindow() {
        return toolWindow;
    }

    public void setToolWindow(ToolWindow toolWindow) {
        this.toolWindow = toolWindow;
    }

    public ConsoleView getConsoleView() {
        return consoleView;
    }

    public void setConsoleView(ConsoleView consoleView) {
        this.consoleView = consoleView;
    }
}

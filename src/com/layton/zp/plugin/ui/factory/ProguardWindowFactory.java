package com.layton.zp.plugin.ui.factory;

import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.remoteServer.impl.runtime.log.LoggingHandlerImpl;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.layton.zp.plugin.ui.LaytonDecode;
import org.jetbrains.annotations.NotNull;

public class ProguardWindowFactory implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        LaytonDecode laytonWindow = new LaytonDecode(project);
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(laytonWindow.getLaytonPanel(), "", false);
        toolWindow.getContentManager().addContent(content);

    }

    public void s (@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ToolWindow toolWindow1 = ToolWindowManager.getInstance(project).getToolWindow("Terminal");
        ConsoleView consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(project).getConsole();
        Content content = toolWindow.getContentManager().getFactory().createContent(consoleView.getComponent(),"Proguard Decode",false);
        toolWindow1.getContentManager().addContent(content);
        consoleView.print("at com.netease.boo.ui.LocalAlbumActivity$getLocalAlbumMedia$1.invokeSuspend(LocalAlbumActivity.java:11)", ConsoleViewContentType.NORMAL_OUTPUT);
    }
}

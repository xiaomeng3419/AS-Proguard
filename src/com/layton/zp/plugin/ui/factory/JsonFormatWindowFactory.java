package com.layton.zp.plugin.ui.factory;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.layton.zp.plugin.ui.MainTabPage;
import org.jetbrains.annotations.NotNull;

public class JsonFormatWindowFactory implements ToolWindowFactory {
    private  Project project;

    public Project getProject() {
        return project;
    }

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        this.project = toolWindow.getProject();
        MainTabPage mainTabPage = new MainTabPage(toolWindow.getProject());
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(mainTabPage.getLaytonPanel(), "", false);
        toolWindow.getContentManager().addContent(content);
    }
}

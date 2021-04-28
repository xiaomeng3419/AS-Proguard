package com.layton.zp.plugin;

public class CustomConfiguration {
    private String processJarPath;
    private String projectBranch;
    private Boolean needUnzip;

    public CustomConfiguration(String processJarPath, String projectBranch, Boolean needUnzip) {
        this.processJarPath = processJarPath;
        this.projectBranch = projectBranch;
        this.needUnzip = needUnzip;
    }

    public String getProcessJarPath() {
        return processJarPath;
    }

    public void setProcessJarPath(String processJarPath) {
        this.processJarPath = processJarPath;
    }

    public String getProjectBranch() {
        return projectBranch;
    }

    public void setProjectBranch(String projectBranch) {
        this.projectBranch = projectBranch;
    }

    public Boolean getNeedUnzip() {
        return needUnzip;
    }

    public void setNeedUnzip(Boolean needUnzip) {
        this.needUnzip = needUnzip;
    }
}

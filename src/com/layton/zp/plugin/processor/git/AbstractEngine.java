package com.layton.zp.plugin.processor.git;

import com.intellij.notification.NotificationType;
import com.layton.zp.plugin.config.LaytonConfiguration;
import com.layton.zp.plugin.processor.Engine;
import com.layton.zp.plugin.util.LaytonNotification;

import java.io.File;
import java.util.Hashtable;

public abstract class AbstractEngine implements Engine {
    protected Hashtable<String, Object> configuration;

    public Hashtable<String, Object> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Hashtable<String, Object> configuration) {
        this.configuration = configuration;
    }


}

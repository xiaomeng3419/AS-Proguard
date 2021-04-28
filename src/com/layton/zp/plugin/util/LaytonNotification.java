package com.layton.zp.plugin.util;

import com.intellij.notification.*;
import com.intellij.openapi.ui.MessageType;
import com.intellij.remoteServer.impl.runtime.log.ConsoleTerminalHandlerImpl;
import com.jediterm.terminal.Terminal;

import javax.smartcardio.CardTerminals;
import javax.smartcardio.TerminalFactory;

public class LaytonNotification {
    public static void notification(String content, NotificationType level) {
        NotificationGroup group = new NotificationGroup("Layton-Proguard", NotificationDisplayType.BALLOON, true);
        Notification notification = group.createNotification("Layton-Proguard LaytonNotification","",content, level,null);
        Notifications.Bus.notify(notification);
    }

}

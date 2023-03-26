package com.layton.zp.plugin.ui.factory;

import com.intellij.debugger.memory.utils.AndroidUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.IconLoader;
import com.layton.zp.plugin.util.TerminalUtil;
import org.jetbrains.annotations.NotNull;

public class ScrcpyAction extends AnAction {


    public ScrcpyAction() {
        super("Scrcpy", "Scrcpy1", IconLoader.getIcon("/img/ic_scrcpy.svg"));
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
//        ADB adb = ADB.getInstance(project);
        String selectedDeviceName = null;
/*        AndroidDebugBridge adb = AndroidDebugBridge.createBridge();

        if (adb != null) {
            IDevice[] devices = adb.getDevices();
            if (devices != null && devices.length > 0) {
                for (IDevice device : devices) {
                    TerminalUtil.output("Devices, getAvdName:", device.getAvdName());
                    TerminalUtil.output("Devices, getSerialNumber:", device.getSerialNumber());
*//*                    if (device.isEmulator() && device.getAvdName().equals(selectedAvdName)) {
                        selectedDeviceName = device.getName();
                        break;
                    }*//*
                }
            }
            TerminalUtil.output("Devices, adb: ", "not null, devices length:" + devices.length);
        } else {
            TerminalUtil.output("Devices, adb: ", "null");
        }*/
    }
}

package com.layton.zp.plugin.ui.factory;

import com.android.ddmlib.AdbHelper;
import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import com.intellij.debugger.memory.utils.AndroidUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.IconLoader;
import com.layton.zp.plugin.util.TerminalUtil;
import org.jetbrains.annotations.NotNull;

public class ScrcpyAction extends AnAction {

//https://github.dev/wajahatkarim3/SideMirror/tree/master/src/main
    public ScrcpyAction() {
        super("Scrcpy", "Scrcpy1", IconLoader.getIcon("/img/ic_scrcpy.svg"));
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
//        ADB adb = ADB.getInstance(project);
// 初始化 AndroidDebugBridge
        AndroidDebugBridge adb = AndroidDebugBridge.createBridge();

// 等待 AndroidDebugBridge 初始化完成
        if (adb.hasInitialDeviceList()) {
            // 获取当前连接的设备列表
            IDevice[] devices = adb.getDevices();

            // 如果设备列表不为空，则输出第一个设备的序列号
            if (devices.length > 0) {
                String selectedDeviceSerial = devices[0].getSerialNumber();
                TerminalUtil.output(anActionEvent.getProject(),"Devices, getAvdName:", devices[0].getAvdName());
                TerminalUtil.output(anActionEvent.getProject(),"Devices, getSerialNumber:", devices[0].getSerialNumber());
                System.out.println("Selected device serial: " + selectedDeviceSerial);
            } else {
                System.err.println("No devices connected");
            }
        } else {
            // 等待 AndroidDebugBridge 初始化完成
            int count = 0;
            while (!adb.hasInitialDeviceList() && count < 10) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
            }

            // 获取当前连接的设备列表
            IDevice[] devices = adb.getDevices();

            // 如果设备列表不为空，则输出第一个设备的序列号
            if (devices.length > 0) {
                String selectedDeviceSerial = devices[0].getSerialNumber();
                System.out.println("Selected device serial: " + selectedDeviceSerial);
                TerminalUtil.output(anActionEvent.getProject(),"Devices, getAvdName:", devices[0].getAvdName());
                TerminalUtil.output(anActionEvent.getProject(),"Devices, getSerialNumber:", devices[0].getSerialNumber());
            } else {
                System.err.println("No devices connected");
                TerminalUtil.output(anActionEvent.getProject(),"Devices, adb: ", "not devices");
            }
        }
       /* if (adb != null) {
            IDevice[] devices = adb.getDevices();
            if (devices != null && devices.length > 0) {
                for (IDevice device : devices) {

                    TerminalUtil.output("Devices, getAvdName:", device.getAvdName());
                    TerminalUtil.output("Devices, getSerialNumber:", device.getSerialNumber());
                   *//* if (device.isEmulator() && device.getAvdName().equals(selectedAvdName)) {
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

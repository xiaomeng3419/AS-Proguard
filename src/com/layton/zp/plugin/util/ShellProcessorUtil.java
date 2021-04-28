package com.layton.zp.plugin.util;

import com.intellij.notification.NotificationType;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class ShellProcessorUtil {
    public static List<String> shell(List<String> commands){
        List<String> rspList = new ArrayList<>();
        Runtime run = Runtime.getRuntime();
        Process proc = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            proc = run.exec("/bin/bash", null, null);
            in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(proc.getOutputStream())), true);
            for (String line : commands) {
                LaytonNotification.notification(line, NotificationType.INFORMATION);
                out.println(line);
            }
            out.println("exit");// 这个命令必须执行，否则in流不结束。
            String rspLine = "";
            while ((rspLine = in.readLine()) != null) {
                rspList.add(rspLine);
            }
            proc.waitFor();
        } catch (IOException e1) {
            e1.printStackTrace();
            LaytonNotification.notification(e1.getMessage(), NotificationType.ERROR);
        } catch (InterruptedException e) {
            LaytonNotification.notification(e.getMessage(), NotificationType.ERROR);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                if (proc != null) {
                    proc.destroy();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LaytonNotification.notification(rspList.toString(),NotificationType.INFORMATION);
        return rspList;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(System.getProperty("user.dir"));
        Process pr = Runtime.getRuntime().exec("python3 "+System.getProperty("user.dir")+"/resources/proguard.py -b ss");
        BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {

            System.out.println(line);
        }
        in.close();
        pr.waitFor();

    }

    public static void clipboard(String text){
        Clipboard  clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable tText = new StringSelection(text);
        clip.setContents(tText,null);
    }

    public static String getClipBoardText(){
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable = clip.getContents(null);
        if (transferable != null){
            if(transferable.isDataFlavorSupported(DataFlavor.stringFlavor)){
                try {
                    return (String)transferable.getTransferData(DataFlavor.stringFlavor);
                }catch (Exception e){
                    LaytonNotification.notification("copy error: "+e.getMessage(),NotificationType.ERROR);
                }
            }
        }
        return "";
    }
}
package com.layton.zp.plugin.processor.git;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GitDemo {
    public void exeGitCmd(){
        //第一步获取命令行集。
        //第二部执行命令行并返回结果集
    }

    public static void main(String[] args) {
        List<String> rspList = new ArrayList<String>();
        List<String> commands = new ArrayList<>();
        commands.add("cd /Users/zhangpan/work/gitwork/BubbleAlbum/boo-release/");
        commands.add("git checkout v1.9.7");
        commands.add("git status" );
        Runtime run = Runtime.getRuntime();
        try {
            Process proc = run.exec("/bin/bash", null, null);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(proc.getOutputStream())), true);
            for (String line : commands) {
                out.println(line);
            }
            out.println("exit");// 这个命令必须执行，否则in流不结束。
            proc.waitFor();
            String rspLine = "";
            while ((rspLine = in.readLine()) != null) {
                rspList.add(rspLine);
            }

            in.close();
            out.close();
            proc.destroy();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        rspList.forEach(s -> System.out.println(s));
//        return rspList;
    }
}

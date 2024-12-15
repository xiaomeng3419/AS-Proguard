package com.layton.zp.plugin.ui;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.intellij.openapi.project.Project;
import com.layton.zp.plugin.util.ClipBoardUtil;
import com.layton.zp.plugin.util.TerminalUtil;
import com.layton.zp.plugin.util.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import static javax.swing.ScrollPaneConstants.*;

/**
 * every area must be depends on panel
 */
public class MainTabPage {
    private JTabbedPane tabbedPane1;
    private JButton formatButton;
    private JPanel laytonPanel;
    private JTextField projectBranch;
    private JTextField proguardJarPath;
    private JCheckBox canEditProguard;
    private JButton resetBtn;
    private JButton decodeBtn;
    private JButton saveBtn;
    private JButton docBtn;
    private JCheckBox unzipCB;
    private JCheckBox useGitCB;
    private JTextArea onlineLogTA;
    private JLabel searchKeyWord;
    private JTextField keywordTF;
    private JButton searchButton;
    private JTextArea destContent;
    private JTextArea originContent;
    private JPanel mainPanelLayout;
    private JButton printBtn;
    private JCheckBox unicodeToChinese;
    private JScrollPane destContentScroll;
    private JTabbedPane tabbedPane2;
    private JPanel jsonTabContent;
    private Project project;

    private static ThreadLocal<Project> threadLocalVariable = new ThreadLocal<>();



    public JPanel getLaytonPanel() {
        return mainPanelLayout;
    }

    public MainTabPage(Project project) {
        this.project = project;
        formatButton.addActionListener(e -> {
            if (!originContent.getText().isEmpty()) {
                String result = "Json is invalid";
                String unicodeToTransformResult = originContent.getText();
                if (unicodeToChinese.isSelected()) {
                    unicodeToTransformResult = Utils.unicodeToChinese(unicodeToTransformResult);
                }
                boolean parseSucceed = false;
                try {
                    JSONObject jsonObject = JSONObject.parseObject(unicodeToTransformResult);
                    result = JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                            SerializerFeature.WriteDateUseDateFormat);
                    ClipBoardUtil.copy(result);
                    parseSucceed = true;
                } catch (Exception exception1) {
                    try {
                        JSONArray jsonObject = JSONArray.parseArray(unicodeToTransformResult);
                        result = JSONArray.toJSONString(jsonObject, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                                SerializerFeature.WriteDateUseDateFormat);
                        ClipBoardUtil.copy(result);
                        parseSucceed = true;
                    } catch (Exception exception) {
                        result = result + ": " + Utils.LINE_SPLIT + unicodeToTransformResult;
                        exception.printStackTrace();
                        TerminalUtil.outputJson(project,exception.getMessage());
                    }
                }
                destContent.setText(result);
                if (parseSucceed) {
                    TerminalUtil.output(project, "origin content:", originContent.getText());
                    TerminalUtil.output(project, "JSON", result);
                    originContent.setText("");
                }
            }
        });
        printBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!originContent.getText().isEmpty()) {
                    TerminalUtil.output(project,"log", originContent.getText());
                }
            }
        });
        destContentScroll.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
        JScrollBar js = new JScrollBar(Adjustable.HORIZONTAL);
        destContentScroll.setHorizontalScrollBar(js);
        destContentScroll.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
        destContentScroll.setViewportView(destContent);
 /*       destContent.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // 在这里执行你想要执行的操作，比如重绘界面
                TerminalUtil.output("log1-width", "" + destContent.getWidth());
                TerminalUtil.output("log1-height", "" + destContent.getHeight());
                TerminalUtil.output("log2-width", "" + destContentScroll.getWidth());
                TerminalUtil.output("log2-height", "" + destContentScroll.getHeight());
            }
        });*/
    }
}

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

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;

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
                try {
                    JSONObject jsonObject = JSONObject.parseObject(unicodeToTransformResult);
                    result = JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                            SerializerFeature.WriteDateUseDateFormat);
                    ClipBoardUtil.copy(result);
                } catch (Exception exception1) {
                    try {
                        JSONArray jsonObject = JSONArray.parseArray(unicodeToTransformResult);
                        result = JSONArray.toJSONString(jsonObject, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                                SerializerFeature.WriteDateUseDateFormat);
                        ClipBoardUtil.copy(result);
                    }catch (Exception exception){
                        result = result + ": " + Utils.LINE_SPLIT + unicodeToTransformResult;
                        exception.printStackTrace();
                        TerminalUtil.outputJson(exception.getMessage());
                    }
                }
                destContent.setText(result);
            }
        });
        printBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!originContent.getText().isEmpty()) {
                    TerminalUtil.output("log", originContent.getText());
                }
            }
        });
        destContentScroll.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
        destContentScroll.setHorizontalScrollBar(new JScrollBar(Adjustable.HORIZONTAL));
        destContentScroll.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);
        destContentScroll.setPreferredSize(new Dimension(530, 1070));
        destContentScroll.
    }
}

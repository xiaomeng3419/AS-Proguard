package com.layton.zp.plugin.util;

import org.apache.commons.lang.StringUtils;

public class Utils {

    public static String LINE_SPLIT = "\r\n";

    public static String unicodeToChinese(String content){
        if (StringUtils.isBlank(content)) {
            return null;
        }
        content = content.toLowerCase();

        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;

        while ((i = content.indexOf("\\u", pos)) != -1) {
            sb.append(content.substring(pos, i));
            if (i + 5 < content.length()) {
                pos = i + 6;
                sb.append((char) Integer.parseInt(content.substring(i + 2, i + 6), 16));
            }
        }
        //如果pos位置后，有非中文字符，直接添加
        sb.append(content.substring(pos));

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(unicodeToChinese("00611\\u00611\\u00622\\u0063\\u0064就是\\u4f60ab\\u597d\\u554afasdfasdf"));
    }
}

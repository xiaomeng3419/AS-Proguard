package com.layton.zp.plugin.util;

/*import com.google.gson.JsonObject;
import com.intellij.webcore.util.JsonUtil;*/

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    public static String liveTime() {
        DateTimeFormatter dfVar = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

        return dfVar.format(LocalDateTime.now());
    }

    public static void main(String[] args) {
/*        JsonObject jsonObject = JsonUtil.parseJsonObject("{'code': 0, 'msg': 'success', 'data': ''}");
        System.out.println(jsonObject.get("msg"));*/
    }
}

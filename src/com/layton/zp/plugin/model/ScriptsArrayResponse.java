package com.layton.zp.plugin.model;

import java.util.List;

public class ScriptsArrayResponse {
    private int code;
    private String msg;
    private List<String> data;

    public ScriptsArrayResponse(int code, String msg, List<String> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}

package com.layton.zp.plugin.util;

public class Type {
    public enum DecodeType {

        MOVE(0), SEARCH(1);

        private int code;

        public int getCode() {
            return code;
        }

        DecodeType(int code) {
            this.code = code;
        }
    }
}

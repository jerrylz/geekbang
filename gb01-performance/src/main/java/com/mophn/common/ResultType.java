package com.mophn.common;

public enum ResultType {
    SUCCESS(2000, "成功"),
    FAIL(5000, "失败");
    int code;
    String msg;

    ResultType(int code, String msg) {
        this.code = 2000;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }

}

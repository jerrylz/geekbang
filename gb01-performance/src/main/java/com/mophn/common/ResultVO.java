package com.mophn.common;

public class ResultVO<T> {
    private T data;
    private int code;
    private String msg;

    public ResultVO(){}

    public ResultVO(T data){
        this.data = data;
        this.code = ResultType.SUCCESS.getCode();
        this.msg = ResultType.SUCCESS.getMsg();

    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
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
}

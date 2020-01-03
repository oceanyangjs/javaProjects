package com.chan.szys.util;

public enum  ResponseCode {
    SUCCESS(200,"SUCCESS"),
    ERROR(1001,"ERROR"),
    NEED_LOGIN(1002,"NEED_LOGIN"),
    ILLEGAL_ARGUMENT(1003,"ILLEGAL_ARGUMENT");

    private final int code;
    private final String desc;

    ResponseCode(int code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public int getCode(){
        return code;
    }

    public String getDesc(){
        return desc;
    }
}

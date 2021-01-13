package com.chan.szys.util;

public enum  ResponseCode {
    SUCCESS(200,"SUCCESS"),
    ERROR(1001,"ERROR"),
    DB_ERROR(1002,"DB_ERROR"),
    NO_USER(1003,"NO_USER"),
    PARAM_ERROR(1004,"PARAM_ERROR"),
    TOKEN_ERROR(1005,"TOKEN_ERROR"),
    NAME_FILTER_ERROR(1006,"NAME_FILTER_ERROR"),
    NAME_REPEAT_ERROR(1007,"NAME_REPEAT_ERROR"),
    GAME_VERSION_ERROR(1008,"GAME_VERSION_ERROR"),
    JOIN_TIME_ERROR(1009,"JOIN_TIME_ERROR"),
    DUMPLICATE_JOIN(1010,"DUMPLICATE_JOIN"),
    FINISH(99999,"FINISH");
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

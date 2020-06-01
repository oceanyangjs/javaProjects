package com.chan.szys.util;

import java.util.HashMap;

public class CommonUtil {
    public static HashMap dbset = new HashMap(){
        {
            put(1,"timerank");
            put(2,"numrank");
        }
    };
    public static String rankKey = "rank:model:%s:num:%s:calcu:%s:digit:%s:operate:%s";
    public static String rankUserKey = "rank:user:id:%s:userId:%s:userName:%s:timelast:%s:accuracy:%s:score:%s:time:%s";
    public static int rankcnt = 100;
    public static long tokenlast = 36000 * 24 * 3600 * 1000;
}

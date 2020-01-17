package com.chan.szys.util;

import java.util.HashMap;

public class RankUtil {
    public static HashMap dbset = new HashMap(){
        {
            put(1,"timerank");
            put(2,"numrank");
        }
    };
    public static String rankKey = "rank:model:%s:num:%s:calcu:%s:digit:%s:operate:%s";
    public static String rankUserKey = "rank:user:id:%s:userId:%s:userName:%s:timelast:%s:accuracy:%s:score:%s:time:%s";
    public static int rankcnt = 100;
}

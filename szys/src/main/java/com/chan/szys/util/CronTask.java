package com.chan.szys.util;

import com.chan.szys.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class CronTask {

    @Autowired
    private CompetitionService competitionService;

//    @PostConstruct
    public void init(){
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
//        Date nextTime = null;
//        try {
//            nextTime = df.parse(df2.format(new Date()) + " 00:00:00");
////            Date to = new Date(nextTime.getTime() + 24*3600*1000);
//            CronExpression expression;
//            List<Date> crontimes = new ArrayList<>();
//            expression = new CronExpression("0 * 1-23 * * ?");//测试 每分钟打印一次
//            for(int a = 0;a<1;){
//                nextTime = expression.getNextValidTimeAfter(nextTime);
////                if(nextTime.getTime()>=to.getTime()) break;
//                crontimes.add(nextTime);
//                System.out.println(nextTime);
//            }
//            for(int i=0;i<crontimes.size();i++){
//                System.out.println(df.format(crontimes.get(i)));
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        单独的进程处理
        new Runnable() {
            @Override
            public void run() {
                while(true){
//                    System.out.println(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + ":"
//                            + Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":"
//                            + Calendar.getInstance().get(Calendar.MINUTE) + ":"
//                            + Calendar.getInstance().get(Calendar.SECOND)
//                    );
                    if(Calendar.getInstance().get(Calendar.SECOND) == 30 || Calendar.getInstance().get(Calendar.SECOND) == 20 || Calendar.getInstance().get(Calendar.SECOND) == 00 || Calendar.getInstance().get(Calendar.SECOND) == 40){
//                    if(Calendar.getInstance().get(Calendar.HOUR) == 00 && Calendar.getInstance().get(Calendar.MINUTE) == 00 && Calendar.getInstance().get(Calendar.SECOND) == 01 && Calendar.getInstance().get(Calendar.MILLISECOND) == 000 && Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == 22){
                        try {
//                            System.out.println(competitionService.getInfo());
                            if(Calendar.getInstance().get(Calendar.MILLISECOND) == 300){
                                System.out.println("测试");
                                System.out.println(getMonthBegin(Calendar.getInstance().getTimeInMillis()));
                                System.out.println(getMonthEnd(Calendar.getInstance().getTimeInMillis()));
                            }
                            long startTime = getMonthBegin(Calendar.getInstance().getTimeInMillis());
                            long endTime = getMonthBegin(Calendar.getInstance().getTimeInMillis());
//                            获取当月的配置信息
//                            Info info = competitionService.getInfo();
//                            查询对应的数据--报名过的玩家--玩家这个月报名，同时这个月有成绩产生
//                            需要数据结构 玩家名 玩家信息 成绩 -- 单独取一次的
                            competitionService.stat(startTime,endTime);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.run();
    }

    public Long getMonthBegin(Long dateTimeMillis) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(dateTimeMillis));

        //设置为1号,当前日期既为本月第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        //将小时至0
        c.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        c.set(Calendar.MINUTE, 0);
        //将秒至0
        c.set(Calendar.SECOND,0);
        //将毫秒至0
        c.set(Calendar.MILLISECOND, 0);
        // 获取本月第一天的时间戳
        return c.getTimeInMillis();
    }

    public Long getMonthEnd(Long dateTimeMillis) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(dateTimeMillis));

        //设置为1号,当前日期既为本月第一天
        c.set(Calendar.DAY_OF_MONTH, 21);
        //将小时至0
        c.set(Calendar.HOUR_OF_DAY, 23);
        //将分钟至0
        c.set(Calendar.MINUTE, 59);
        //将秒至0
        c.set(Calendar.SECOND,59);
        //将毫秒至0
        c.set(Calendar.MILLISECOND, 999);
        // 获取本月第一天的时间戳
        return c.getTimeInMillis();
    }
}

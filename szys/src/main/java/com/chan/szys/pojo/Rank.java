package com.chan.szys.pojo;

public class Rank {
    private int id;//存储id
    private String userId;//玩家id
    private String db;
    private int num;//数量（限时模式（1min、3min、5min）、限数模式（10道、30道、50道）） -- 123
    private int calcu;//法则（+-*/） 1234
    private int digit;//位数（两位数、三位数、四位数、五位数） 2345
    private int operate;//操作数（两个数进行运算、三个数进行运算） 2 3
    private double timelast;//耗时 小数点后1位
    private double accuracy;//正确率 小数点后1位
    private String data;//答题数据
    private long insertTime;//时间戳--会用于redis做key的区分

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getCalcu() {
        return calcu;
    }

    public void setCalcu(int calcu) {
        this.calcu = calcu;
    }

    public int getDigit() {
        return digit;
    }

    public void setDigit(int digit) {
        this.digit = digit;
    }

    public int getOperate() {
        return operate;
    }

    public void setOperate(int operate) {
        this.operate = operate;
    }

    public double getTimelast() {
        return timelast;
    }

    public void setTimelast(double timelast) {
        this.timelast = timelast;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(long insertTime) {
        this.insertTime = insertTime;
    }
}

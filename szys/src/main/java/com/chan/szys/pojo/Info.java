package com.chan.szys.pojo;

public class Info {
    private int id;//存储id
    private int model;
    private int num;//数量（限时模式（1min、3min、5min）、限数模式（10道、30道、50道）） -- 123
    private int calcu;//法则（+-*/） 1234
    private int digit;//位数（两位数、三位数、四位数、五位数） 2345
    private int operate;//操作数（两个数进行运算、三个数进行运算） 2 3

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
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
}

package com.chan.szys.pojo;

import java.sql.Timestamp;

public class User {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String id;
    public String password;
    public String name;
    public long registerTime;
    public long updateTime;

    public long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(long registerTime) {
        this.registerTime = registerTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

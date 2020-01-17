package com.chan.szys.mapper;

import com.chan.szys.pojo.User;

import java.util.List;

public interface UserMapper {
//    获取用户名单
    public List<User> list();
//    添加用户
    public int add(User user) throws Exception;
//    查询用户
    public User query(String name);
//    更新名字
    public int changename(User user) throws Exception;
}

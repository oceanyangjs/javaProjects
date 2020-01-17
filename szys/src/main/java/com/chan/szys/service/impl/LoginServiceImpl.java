package com.chan.szys.service.impl;

import com.chan.szys.mapper.UserMapper;
import com.chan.szys.pojo.User;
import com.chan.szys.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    public UserMapper userMapper;
    @Override
    public List<User> list() {
        List<User> listUser = userMapper.list();
        return listUser;
    }

    @Override
    public int add(User user) throws Exception{
        return userMapper.add(user);
    }

    @Override
    public User query(String id) throws Exception {
        return userMapper.query(id);
    }

    @Override
    public int changename(User user) throws Exception {
        return userMapper.changename(user);
    }


}

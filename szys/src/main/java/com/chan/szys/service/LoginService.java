package com.chan.szys.service;

import com.chan.szys.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface LoginService {
    public List<User> list();
}
package com.chan.szys.controller;

import com.chan.szys.pojo.User;
import com.chan.szys.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LoginController {
    @Autowired
    public LoginService loginService;
    @RequestMapping(value = "/register",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String register(@RequestBody User user){
        return "";
    }

    @RequestMapping("listUser")
    public List<User> listUser(){
        List<User> user = loginService.list();
        return user;
    }
}

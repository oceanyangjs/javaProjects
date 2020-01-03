package com.chan.szys.controller;

import com.chan.szys.pojo.User;
import com.chan.szys.service.LoginService;
import com.chan.szys.util.JwtUtil;
import com.chan.szys.util.CommonResponse;
import com.chan.szys.util.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class LoginController {
    @Autowired
    public LoginService loginService;
    @RequestMapping(value = "/register",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String register(@RequestBody User user){
        return "";
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public CommonResponse register(@RequestParam(required = false) String userName){
//        区分是否已经保存了账户
        if(userName == null || userName == ""){//新用户注册
            long timestamp = System.currentTimeMillis();
            String name = "user:" + timestamp;
            String pwd = "000000";
            String id = UUID.randomUUID().toString();
            User user = new User();
            user.setId(id);
            user.setName(name);
            user.setPassword(pwd);
            user.setRegisterTime(timestamp);
            user.setUpdateTime(timestamp);
            try {
                int result = loginService.add(user);
                if(result == 1){
                    long ttlMillis = 15 * 3600 * 1000;
                    String jwttoken = JwtUtil.createJWT(ttlMillis,user);
                    CommonResponse returnresult = new CommonResponse();
                    returnresult.setCode(ResponseCode.SUCCESS.getCode());
                    returnresult.setErrmsg(ResponseCode.SUCCESS.getDesc());
                    returnresult.setToken(jwttoken);
                    return returnresult;
                }else{
                    CommonResponse returnresult = new CommonResponse();
                    returnresult.setCode(201);
                    return returnresult;
                }
            }catch (Exception e){
                CommonResponse returnresult = new CommonResponse();
                returnresult.setCode(300);
                return returnresult;
            }
        }else{//老用户登陆--更新jwt

        }
        return null;
    }

    @RequestMapping("listUser")
    public List<User> listUser(){
        List<User> user = loginService.list();
        return user;
    }
}

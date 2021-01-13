package com.chan.szys.controller;

import com.chan.szys.pojo.User;
import com.chan.szys.util.CommonUtil;
import com.chan.szys.util.JwtUtil;
import com.chan.szys.util.response.RegisterResponse;
import com.chan.szys.service.LoginService;
import com.chan.szys.util.SensitiveWordFilter;
import com.chan.szys.util.response.ChangenameResponse;
import com.chan.szys.util.response.CommonResponse;
import com.chan.szys.util.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
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
    public CommonResponse register(@RequestParam(required = false) String userId){
//        区分是否已经保存了账户
        if(userId == null || userId == ""){//新用户注册
            long timestamp = System.currentTimeMillis();
            String name = "user:" + timestamp + "temp";
            String id = UUID.randomUUID().toString();
            String pwd = "pwd:" + id + ":" + (int)(Math.random()*10000);
            User user = new User();
            user.setId(id);
            user.setName(name);
            user.setPassword(pwd);
            user.setRegisterTime(timestamp);
            user.setUpdateTime(timestamp);
            try {
                int result = loginService.add(user);
                if(result == 1){
                    long ttlMillis = CommonUtil.tokenlast;//有效期15天
                    String jwttoken = JwtUtil.createJWT(ttlMillis,user);
                    CommonResponse returnresult = new CommonResponse();
                    returnresult.setCode(ResponseCode.SUCCESS.getCode());
                    returnresult.setErrmsg(ResponseCode.SUCCESS.getDesc());
                    returnresult.setToken(jwttoken);
                    RegisterResponse registerResponse = new RegisterResponse();
                    registerResponse.setName(user.getName());
                    registerResponse.setIs_new(1);
                    registerResponse.setId(id);
                    returnresult.setData(registerResponse);
                    return returnresult;
                }else{
                    CommonResponse returnresult = new CommonResponse();
                    returnresult.setCode(ResponseCode.ERROR.getCode());
                    returnresult.setErrmsg(ResponseCode.ERROR.getDesc());
                    return returnresult;
                }
            }catch (Exception e){
                CommonResponse returnresult = new CommonResponse();
                returnresult.setCode(ResponseCode.DB_ERROR.getCode());
                returnresult.setErrmsg(ResponseCode.DB_ERROR.getDesc());
                return returnresult;
            }
        }else{//老用户登陆--更新jwt
            String id = userId;//名字唯一
            //根据名字去数据库查询user实际内容
            try {
                User user = loginService.query(id);
                if(user != null){
                    long ttlMillis = CommonUtil.tokenlast;//有效期15天
                    String jwttoken = JwtUtil.createJWT(ttlMillis,user);
                    CommonResponse returnresult = new CommonResponse();
                    returnresult.setToken(jwttoken);
                    RegisterResponse registerResponse = new RegisterResponse();
                    registerResponse.setName(user.getName());
                    registerResponse.setIs_new(0);
                    returnresult.setData(registerResponse);
                    returnresult.setCode(ResponseCode.SUCCESS.getCode());
                    returnresult.setErrmsg(ResponseCode.SUCCESS.getDesc());
                    return returnresult;
                }else{
                    CommonResponse returnresult = new CommonResponse();
                    returnresult.setCode(ResponseCode.NO_USER.getCode());
                    returnresult.setErrmsg(ResponseCode.NO_USER.getDesc());
                    return returnresult;
                }
            } catch (Exception e) {
                e.printStackTrace();
                CommonResponse returnresult = new CommonResponse();
                returnresult.setCode(ResponseCode.DB_ERROR.getCode());
                returnresult.setErrmsg(ResponseCode.DB_ERROR.getDesc());
                return returnresult;
            }
        }
    }

    @RequestMapping("changeName")
    public CommonResponse changeName(String userId,String userName,String jwttoken){
//        参数校验
        if(userId == null || userId == "" || userName == null || userName == ""){
            CommonResponse returnresult = new CommonResponse();
            returnresult.setCode(ResponseCode.PARAM_ERROR.getCode());
            returnresult.setErrmsg(ResponseCode.PARAM_ERROR.getDesc());
            return returnresult;
        }
//        接收到请求先验证jwt--从jwt中解析出内容判断用户
        try {
            User user = loginService.query(userId);
            if(user == null){
                CommonResponse returnresult = new CommonResponse();
                returnresult.setCode(ResponseCode.NO_USER.getCode());
                returnresult.setErrmsg(ResponseCode.NO_USER.getDesc());
                return returnresult;
            }else{
                Boolean check = JwtUtil.isVerify(jwttoken,user);
                if(check){//token有效
//                逻辑处理
//                名字合法性过滤 == 暂时省略
                    SensitiveWordFilter filter = new SensitiveWordFilter();
                    Set<String> set = filter.getSensitiveWord(userName, 1);
                    if(set.size() > 0){
                        CommonResponse returnresult = new CommonResponse();
                        returnresult.setCode(ResponseCode.NAME_FILTER_ERROR.getCode());
                        returnresult.setErrmsg(ResponseCode.NAME_FILTER_ERROR.getDesc());
                        return returnresult;
                    }
//                    先做名字存在性检测
                    int num = loginService.dulpname(userName);
                    if(num > 0){
                        CommonResponse returnresult = new CommonResponse();
                        returnresult.setCode(ResponseCode.NAME_REPEAT_ERROR.getCode());
                        returnresult.setErrmsg(ResponseCode.NAME_REPEAT_ERROR.getDesc());
                        return returnresult;
                    }
                    User usertemp = new User();
                    usertemp.setName(userName);
                    usertemp.setId(userId);
                    usertemp.setIsNew(0);
                    int result = loginService.changename(usertemp);
                    if(result > 0){//需要做jwt更新
                        user.setName(userName);
                        long ttlMillis = CommonUtil.tokenlast;//有效期15天
                        String token = JwtUtil.createJWT(ttlMillis,user);
                        CommonResponse returnresult = new CommonResponse();
                        returnresult.setCode(ResponseCode.SUCCESS.getCode());
                        returnresult.setErrmsg(ResponseCode.SUCCESS.getDesc());
                        returnresult.setToken(token);
                        ChangenameResponse changenameResponse = new ChangenameResponse();
                        changenameResponse.setIsNew(0);
                        returnresult.setData(changenameResponse);
                        return returnresult;
                    }else{
                        CommonResponse returnresult = new CommonResponse();
                        returnresult.setCode(ResponseCode.DB_ERROR.getCode());
                        returnresult.setErrmsg(ResponseCode.DB_ERROR.getDesc());
                        return returnresult;
                    }
                }else{
                    CommonResponse returnresult = new CommonResponse();
                    returnresult.setCode(ResponseCode.TOKEN_ERROR.getCode());
                    returnresult.setErrmsg(ResponseCode.TOKEN_ERROR.getDesc());
                    return returnresult;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            CommonResponse returnresult = new CommonResponse();
            returnresult.setCode(ResponseCode.DB_ERROR.getCode());
            returnresult.setErrmsg(ResponseCode.DB_ERROR.getDesc());
            return returnresult;
        }
    }

    @RequestMapping("listUser")
    public List<User> listUser(){
        List<User> user = loginService.list();
        return user;
    }
}

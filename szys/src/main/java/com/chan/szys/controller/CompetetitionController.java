package com.chan.szys.controller;

import com.chan.szys.pojo.User;
import com.chan.szys.service.LoginService;
import com.chan.szys.util.JwtUtil;
import com.chan.szys.util.ResponseCode;
import com.chan.szys.util.response.CommonResponse;
import com.chan.szys.pojo.Sign;
import com.chan.szys.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

@RestController
public class CompetetitionController {
    @Autowired
    public CompetitionService competitionService;
    @Autowired
    public LoginService loginService;

//    获取信息 - 作为gm暂用，不需要验证token
    @RequestMapping("/getInfo")
    public CommonResponse getInfo(){
        try {
            CommonResponse commonResponse = new CommonResponse();
            commonResponse.setData(competitionService.getInfo());
            commonResponse.setCode(ResponseCode.SUCCESS.getCode());
            commonResponse.setErrmsg(ResponseCode.SUCCESS.getDesc());
            return commonResponse;
        } catch (Exception e) {
            e.printStackTrace();
            CommonResponse returnresult = new CommonResponse();
            returnresult.setCode(ResponseCode.NO_USER.getCode());
            returnresult.setErrmsg(ResponseCode.NO_USER.getDesc());
            return returnresult;
        }
    }

//    报名竞赛接口
    @RequestMapping("/join")
    public CommonResponse join(String userId, String jwttoken,String info){
        //        参数校验
        if(userId == null || userId == "" || jwttoken == null || jwttoken == "" || info == null || info == ""){
            CommonResponse returnresult = new CommonResponse();
            returnresult.setCode(ResponseCode.PARAM_ERROR.getCode());
            returnresult.setErrmsg(ResponseCode.PARAM_ERROR.getDesc());
            return returnresult;
        }
//        判断时间对不对 1-21号才可以报名
        if(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) > 21){
//        if(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) > 32){
            CommonResponse returnresult = new CommonResponse();
            returnresult.setCode(ResponseCode.JOIN_TIME_ERROR.getCode());
            returnresult.setErrmsg(ResponseCode.JOIN_TIME_ERROR.getDesc());
            return returnresult;
        }
        try {
            User user = null;
            user = loginService.query(userId);
            if(user == null){
                CommonResponse returnresult = new CommonResponse();
                returnresult.setCode(ResponseCode.NO_USER.getCode());
                returnresult.setErrmsg(ResponseCode.NO_USER.getDesc());
                return returnresult;
            }else{
                Sign sign = new Sign();
                sign.setUid(userId);
                sign.setInfo(info);
                long timestamp = System.currentTimeMillis();
                sign.setUpdateTime(timestamp);
                competitionService.join(sign);
                CommonResponse returnresult = new CommonResponse();
                long ttlMillis = 15 * 24 * 3600 * 1000;//有效期15天
                String token = JwtUtil.createJWT(ttlMillis,user);
                returnresult.setCode(ResponseCode.SUCCESS.getCode());
                returnresult.setErrmsg(ResponseCode.SUCCESS.getDesc());
                returnresult.setData(competitionService.getInfo());
                returnresult.setToken(token);
                return returnresult;
            }
        } catch (Exception e) {
            System.out.println(e);
            CommonResponse returnresult = new CommonResponse();
            returnresult.setCode(ResponseCode.DB_ERROR.getCode());
            returnresult.setErrmsg(ResponseCode.DB_ERROR.getDesc());
            return returnresult;
        }
    }
}

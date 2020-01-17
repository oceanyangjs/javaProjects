package com.chan.szys.controller;

import com.chan.szys.datatype.RankData;
import com.chan.szys.pojo.User;
import com.chan.szys.service.LoginService;
import com.chan.szys.service.RankService;
import com.chan.szys.util.JwtUtil;
import com.chan.szys.util.RankUtil;
import com.chan.szys.util.ResponseCode;
import com.chan.szys.util.response.CommonResponse;
import com.chan.szys.util.response.GetdataResponse;
import com.chan.szys.util.response.SetdataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
public class RankController {
    @Autowired
    public LoginService loginService;
    @Autowired
    public RankService rankService;
//    上传排行榜数据
    @RequestMapping("/setData")
    public CommonResponse setData(String userId,String jwttoken,int model,int num,int calcu,int digit,int operate,double timelast,double accuracy,String data){
//        参数校验
        if(userId == null || userId == "" || jwttoken == null || jwttoken == "" || data == null || data == "" || (model != 1 && model != 2)){
            CommonResponse returnresult = new CommonResponse();
            returnresult.setCode(ResponseCode.PARAM_ERROR.getCode());
            returnresult.setErrmsg(ResponseCode.PARAM_ERROR.getDesc());
            return returnresult;
        }
        long timestamp = System.currentTimeMillis();
        User user = null;
        try {
            user = loginService.query(userId);
            if(user == null){
                CommonResponse returnresult = new CommonResponse();
                returnresult.setCode(ResponseCode.NO_USER.getCode());
                returnresult.setErrmsg(ResponseCode.NO_USER.getDesc());
                return returnresult;
            }else{
                // 排行榜数据存储
                Object db = RankUtil.dbset.get(model);
                if(db == null){
                    CommonResponse returnresult = new CommonResponse();
                    returnresult.setCode(ResponseCode.PARAM_ERROR.getCode());
                    returnresult.setErrmsg(ResponseCode.PARAM_ERROR.getDesc());
                    return returnresult;
                }else{
                    int result = rankService.add(userId,user.getName(),db.toString(),num,calcu,digit,operate,timelast,accuracy,data,timestamp);
//                    result -1为更新数据库出问题，如果为0则为排名外，如果大于0则为实际排名
                    if(result < 0){
                        CommonResponse returnresult = new CommonResponse();
                        returnresult.setCode(ResponseCode.DB_ERROR.getCode());
                        returnresult.setErrmsg(ResponseCode.DB_ERROR.getDesc());
                        return returnresult;
                    }else{
                        SetdataResponse setdataResponse = new SetdataResponse();
                        setdataResponse.setRank(result);
                        long ttlMillis = 15 * 24 * 3600 * 1000;//有效期15天
                        String token = JwtUtil.createJWT(ttlMillis,user);
                        CommonResponse returnresult = new CommonResponse();
                        returnresult.setCode(ResponseCode.SUCCESS.getCode());
                        returnresult.setErrmsg(ResponseCode.SUCCESS.getDesc());
                        returnresult.setData(setdataResponse);
                        returnresult.setToken(token);
                        return returnresult;
                    }
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

//    获取排行榜数据
    @RequestMapping("/getData")
    public CommonResponse getData(String userId,String jwttoken,int model,int num,int calcu,int digit,int operate){
        //        参数校验
        if(userId == null || userId == "" || jwttoken == null || jwttoken == ""  || (model != 1 && model != 2)){
            CommonResponse returnresult = new CommonResponse();
            returnresult.setCode(ResponseCode.PARAM_ERROR.getCode());
            returnresult.setErrmsg(ResponseCode.PARAM_ERROR.getDesc());
            return returnresult;
        }
        long timestamp = System.currentTimeMillis();
        User user = null;
        try {
            user = loginService.query(userId);
            if(user == null){
                CommonResponse returnresult = new CommonResponse();
                returnresult.setCode(ResponseCode.NO_USER.getCode());
                returnresult.setErrmsg(ResponseCode.NO_USER.getDesc());
                return returnresult;
            }else{
                // 排行榜数据查询
                Object db = RankUtil.dbset.get(model);
                if(db == null){
                    CommonResponse returnresult = new CommonResponse();
                    returnresult.setCode(ResponseCode.PARAM_ERROR.getCode());
                    returnresult.setErrmsg(ResponseCode.PARAM_ERROR.getDesc());
                    return returnresult;
                }else{
                    List<RankData> result = rankService.get(userId,user.getName(),db.toString(),num,calcu,digit,operate);
                    //先进行一下降序排序
                    Collections.sort(result, new Comparator<RankData>() {
                        @Override
                        public int compare(RankData o1, RankData o2) {
                            // 按照学生的年龄进行升序排列
                            if (o1.getScore() > o2.getScore()) {
                                return -1;
                            }
                            if (o1.getScore() == o2.getScore()) {
                                return 0;
                            }
                            return 1;
                        }
                    });
                    List<GetdataResponse> getdataResponses = new ArrayList<>();
                    int rank = 0;
                    int lastRank = 0;
                    double lastScore = 0.0;
                    for(int i = 0;i < result.size();i++){
                        rank++;
                        GetdataResponse item = new GetdataResponse();
                        item.setId(result.get(i).getId());
                        item.setName(result.get(i).getName());
                        item.setTimelast(result.get(i).getTimelast());
                        item.setAccuracy(result.get(i).getAccuracy());
                        item.setScore(result.get(i).getScore());
                        item.setUserId(result.get(i).getUserId());
                        if(item.getScore() == lastScore){
                            int tmp = lastRank;
                            item.setRank(tmp);
                        }else{
                            item.setRank(rank);
                            lastRank = rank;
                        }
                        lastScore = item.getScore();
                        getdataResponses.add(item);
                    }
                    long ttlMillis = 15 * 24 * 3600 * 1000;//有效期15天
                    String token = JwtUtil.createJWT(ttlMillis,user);
                    CommonResponse returnresult = new CommonResponse();
                    returnresult.setCode(ResponseCode.SUCCESS.getCode());
                    returnresult.setErrmsg(ResponseCode.SUCCESS.getDesc());
                    returnresult.setData(getdataResponses);
                    returnresult.setToken(token);
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

    //    获取排行榜单条数据
    @RequestMapping("/getDetail")
    public CommonResponse getDetail(String userId,String jwttoken,int model,int rankId){
        //        参数校验
        if(userId == null || userId == "" || jwttoken == null || jwttoken == ""  || (model != 1 && model != 2)){
            CommonResponse returnresult = new CommonResponse();
            returnresult.setCode(ResponseCode.PARAM_ERROR.getCode());
            returnresult.setErrmsg(ResponseCode.PARAM_ERROR.getDesc());
            return returnresult;
        }
        long timestamp = System.currentTimeMillis();
        User user = null;
        try {
            user = loginService.query(userId);
            if(user == null){
                CommonResponse returnresult = new CommonResponse();
                returnresult.setCode(ResponseCode.NO_USER.getCode());
                returnresult.setErrmsg(ResponseCode.NO_USER.getDesc());
                return returnresult;
            }else{
                // 排行榜数据查询
                Object db = RankUtil.dbset.get(model);
                if(db == null){
                    CommonResponse returnresult = new CommonResponse();
                    returnresult.setCode(ResponseCode.PARAM_ERROR.getCode());
                    returnresult.setErrmsg(ResponseCode.PARAM_ERROR.getDesc());
                    return returnresult;
                }else{
                    String result = rankService.getDetail(rankId,db.toString());

                    long ttlMillis = 15 * 24 * 3600 * 1000;//有效期15天
                    String token = JwtUtil.createJWT(ttlMillis,user);
                    CommonResponse returnresult = new CommonResponse();
                    returnresult.setCode(ResponseCode.SUCCESS.getCode());
                    returnresult.setErrmsg(ResponseCode.SUCCESS.getDesc());
                    returnresult.setData(result);
                    returnresult.setToken(token);
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
}

package com.chan.szys.service;

import com.chan.szys.datatype.RankData;

import java.util.List;

public interface RankService {
    public int add(String userId,String userName,String db,int num,int calcu,int digit,int operate,double timelast,double accuracy,String data,long insertTime) throws Exception;
    public List<RankData> get(String userId, String userName, String db, int num, int calcu, int digit, int operate) throws Exception;
    public String getDetail(int id,String db) throws Exception;
}

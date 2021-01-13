package com.chan.szys.service;

import com.chan.szys.pojo.Info;
import com.chan.szys.pojo.Sign;

public interface CompetitionService {
    public Info getInfo() throws Exception;//获取配置信息
    public void join(Sign sign) throws Exception;//报名参加竞赛
    public void stat(long startTime,long endTime) throws Exception;//每月自动统计数
}

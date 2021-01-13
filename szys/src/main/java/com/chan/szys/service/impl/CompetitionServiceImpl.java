package com.chan.szys.service.impl;

import com.chan.szys.pojo.Info;
import com.chan.szys.pojo.Sign;
import com.chan.szys.service.CompetitionService;
import com.chan.szys.mapper.CompetitionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompetitionServiceImpl implements CompetitionService {
    @Autowired
    public CompetitionMapper competitionMapper;
    @Override
    public Info getInfo() throws Exception {
        return competitionMapper.getInfo();
    }

    @Override
    public void join(Sign sign) throws Exception {
        competitionMapper.join(sign);
    }

    @Override
    public void stat(long startTime,long endTime) throws Exception {
//        三个步骤
//        1.获取信息--区分数据表
//        Info info = competitionMapper.getInfo();
////        2.获取相关信息
//        List<Stat> statsList = competitionMapper.stat();
    }
}

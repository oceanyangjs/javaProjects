package com.chan.szys.mapper;

import com.chan.szys.pojo.Info;
import com.chan.szys.pojo.Sign;

public interface CompetitionMapper {
    public Info getInfo() throws Exception;
    public void join(Sign sign) throws Exception;
//    public List<Stat> stat() throws Exception;
}

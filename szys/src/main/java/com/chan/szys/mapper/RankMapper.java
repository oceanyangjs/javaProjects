package com.chan.szys.mapper;

import com.chan.szys.datatype.RankData;
import com.chan.szys.pojo.Rank;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RankMapper {
    public int add(Rank rank) throws Exception;
    public List<RankData> rank(@Param("db") String db, @Param("num") int num, @Param("calcu") int calcu, @Param("digit") int digit, @Param("operate") int operate, @Param("rankcnt") int rankcnt) throws Exception;
    public List<RankData> timerank(@Param("db") String db, @Param("num") int num, @Param("calcu") int calcu, @Param("digit") int digit, @Param("operate") int operate, @Param("rankcnt") int rankcnt) throws Exception;
    public String getDetail(@Param("id") int id,@Param("db") String db) throws Exception;
}
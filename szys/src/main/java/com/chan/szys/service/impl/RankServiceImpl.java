package com.chan.szys.service.impl;

import com.chan.szys.config.RedisUtil;
import com.chan.szys.datatype.RankData;
import com.chan.szys.mapper.RankMapper;
import com.chan.szys.pojo.Rank;
import com.chan.szys.service.RankService;
import com.chan.szys.util.Base64Util;
import com.chan.szys.util.RankUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import java.util.*;

import static com.chan.szys.util.RankUtil.rankUserKey;

@Service
public class RankServiceImpl implements RankService {
    @Autowired
    public RankMapper rankMapper;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public int add(String userId,String userName,String db, int num, int calcu, int digit, int operate, double timelast, double accuracy, String data, long insertTime) throws Exception {
        Rank rank = new Rank();
        rank.setUserId(userId);
        rank.setNum(num);
        rank.setCalcu(calcu);
        rank.setDigit(digit);
        rank.setOperate(operate);
        rank.setTimelast(timelast);
        rank.setAccuracy(accuracy);
        rank.setData(data);
        rank.setInsertTime(insertTime);
        rank.setDb(db);
        int result = rankMapper.add(rank);
//        插入数据库之后，查看redis中的数据
//        如果redis中无数据，则从mysql获取数据
//        如果redis中有数据，则更新redis中数据
        if(result == 0){
            result = -1;
        }else{
            result = 0;
            Double score = timelast * accuracy;
            String rankKey = String.format(RankUtil.rankKey,db,num,calcu,digit,operate);
            String rankUserKey = String.format(RankUtil.rankUserKey,rank.getId(),userId, Base64Util.encode(userName),timelast,accuracy,score,insertTime);
            Boolean exist = redisUtil.exists(rankKey);
            if(exist){//redis存在数据了
                //插入数据，获取排名
                redisUtil.zadd(rankKey,score,rankUserKey);
                Set<Tuple> scores = redisUtil.zrevrangeWithScores(rankKey,0,RankUtil.rankcnt-1);
                Object rs = redisUtil.zrevrank(rankKey,rankUserKey);
                if(rs == null){
                    result = 0;
                }else{
                    result = Integer.parseInt(rs.toString()) + 1;
                }
            }else{//redis不存在数据,去数据库查询，同时更新到redis
                List<RankData> rankData = rankMapper.rank(db,num,calcu,digit,operate,100);//取前100数据
                for (int i = 0;i<rankData.size();i++){
                    String rankUserKeyTmp = String.format(RankUtil.rankUserKey,rankData.get(i).getId(),rankData.get(i).getUserId(),Base64Util.encode(rankData.get(i).getName()),rankData.get(i).getTimelast(),rankData.get(i).getAccuracy(),rankData.get(i).getScore(),rankData.get(i).getInsertTime());
                    redisUtil.zadd(rankKey,rankData.get(i).getScore(),rankUserKeyTmp);
                }
//                result = redisUtil.zrevrank(rankKey,rankUserKey).intValue();
                Object rs = redisUtil.zrevrank(rankKey,rankUserKey);
                if(rs == null){
                    result = 0;
                }else{
                    result = Integer.parseInt(rs.toString()) + 1;
                }
            }
        }
        return result;
    }

    @Override
    public List<RankData> get(String userId, String userName, String db, int num, int calcu, int digit, int operate) throws Exception {
//        先在redis查询，查询不到则到mysql查询
        String rankKey = String.format(RankUtil.rankKey,db,num,calcu,digit,operate);
        Boolean exist = redisUtil.exists(rankKey);
        List<RankData> rankData = new ArrayList<>();
        if(exist){//redis存在数据了
            //从redis读取排名
            Set<Tuple> scores = redisUtil.zrevrangeWithScores(rankKey,0,RankUtil.rankcnt-1);
            Iterator<Tuple> it = scores.iterator();
            while (it.hasNext()) {
//                rank:user:id:%s:userId:%s:userName:%s:timelast:%s:accuracy:%s:score:%s:time:%s
                String key = it.next().getElement();
                String[] keys = key.split(":");
                RankData item = new RankData();
                item.setId(Integer.valueOf(keys[3]));
                item.setUserId(keys[5]);
                item.setName(Base64Util.decode(keys[7]));
                item.setTimelast(Double.valueOf(keys[9]));
                item.setAccuracy(Double.valueOf(keys[11]));
                item.setScore(Double.valueOf(keys[13]));
                item.setInsertTime(Long.valueOf(keys[15]));
                rankData.add(item);
            }
        }else{//redis不存在数据,去数据库查询，同时更新到redis
            rankData = rankMapper.rank(db,num,calcu,digit,operate,100);//取前100数据
            for (int i = 0;i<rankData.size();i++){
                String rankUserKeyTmp = String.format(RankUtil.rankUserKey,rankData.get(i).getId(),rankData.get(i).getUserId(),Base64Util.encode(rankData.get(i).getName()),rankData.get(i).getTimelast(),rankData.get(i).getAccuracy(),rankData.get(i).getScore(),rankData.get(i).getInsertTime());
                redisUtil.zadd(rankKey,rankData.get(i).getScore(),rankUserKeyTmp);
            }
        }
        return rankData;
    }

    @Override
    public String getDetail(int id, String db) throws Exception {
        return rankMapper.getDetail(id,db);
    }
}

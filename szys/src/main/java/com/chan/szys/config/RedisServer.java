package com.chan.szys.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;

@Service
public class RedisServer {
    @Autowired
    private JedisPool jedisPool;

    public String Test(){
        String aaaa = this.jedisPool.getResource().get("test");
        System.out.println(aaaa);
        return aaaa;
    }
}

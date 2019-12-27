package com.chan.szys.controller;

import com.chan.szys.config.RedisServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;


@RestController
public class IndexController {

    @Autowired
    public RedisServer redisServer;
//    public JedisPool jedisPoolFactory;

    @RequestMapping("/index")
    public String index(){
        String result = redisServer.Test();
//        String s = jedisPoolFactory.getResource().get("test");
//        System.out.println(s);
        return "hello world:" + result;
    }
}

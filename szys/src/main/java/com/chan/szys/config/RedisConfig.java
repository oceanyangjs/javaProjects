package com.chan.szys.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private String timeout;

    @Value("${spring.redis.jredis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.jredis.pool.max-wait}")
    private long maxWaitMillis;

    @Value("${spring.redis.password}")
    private String password;

//    @Value("${spring.redis.block-when-exhausted}")
//    private boolean  blockWhenExhausted;

    @Bean
    public JedisPool RedisConfig()  throws Exception{
        System.out.println("JedisPool注入成功！！");
        System.out.println("redis地址：" + host + ":" + port);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
//        jedisPoolConfig.setBlockWhenExhausted(blockWhenExhausted);
        // 是否启用pool的jmx管理功能, 默认true
        jedisPoolConfig.setJmxEnabled(true);
        if(password.trim().equals("")){
            password = null;//密码为空时，设置为null可以避免验证
        }
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, Integer.valueOf(timeout.substring(0,timeout.length()-2)), password);
        return jedisPool;
    }
}

package com.wico.systemlinkweb.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author XuCheng
 * @Date 2020-6-6 19:09
 */
@Service
public class RedisPoolFactory {
    @Autowired
    RedisConfig redisConfig;

    /**
     * 将JedisPool注入到Spring容器中
     * @return
     */
    @Bean
    public JedisPool jedisPoolFactory(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
        jedisPoolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
        jedisPoolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait() * 1000);

        JedisPool jedisPool = new JedisPool(jedisPoolConfig, redisConfig.getHost(),redisConfig.getPort(),
                redisConfig.getTimeout()*1000, redisConfig.getPassword(),0);
        return jedisPool;

    }
}

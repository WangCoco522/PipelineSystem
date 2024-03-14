package com.wico.systemlinkweb.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author XuCheng
 * @Date 2020-6-3 20:31
 */
@Service
public class RedisService {
    @Autowired
    JedisPool jedisPool;

    /**
     * 取值
     * @param prefix
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz){
        //获取Jedis
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //生成真正的Key
            String reallyKey = prefix.getPrefix()+key;
            String str = jedis.get(reallyKey);
            T t = strToBean(str,clazz);
            return t;
        }
        finally {
            returnToPool(jedis);
        }
    }

    /**
     * 存入key-vlaue
     * @param prefix
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> boolean set(KeyPrefix prefix,String key, T value){
        //获取Jedis
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            if (str == null || str.length() <= 0){
                return false;
            }
            String reallyKey = prefix.getPrefix()+key;
            int seconds = prefix.expireSeconds();
            if (seconds <= 0){
                jedis.set(reallyKey,str);
            }else {
                jedis.setex(reallyKey,seconds,str);
            }
            return true;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 判断key是否存在
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> boolean exists(KeyPrefix prefix, String key){
        //获取Jedis
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String reallyKey = prefix.getPrefix()+key;
            return jedis.exists(reallyKey);
        }finally {
            returnToPool(jedis);
        }
    }


    /**
     * 删除缓存
     * @param prefix
     * @param key
     * @return
     */
    public boolean delete(KeyPrefix prefix, String key){
        //获取Jedis
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String reallyKey = prefix.getPrefix()+key;
            long res = jedis.del(reallyKey);
            return res > 0;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 自增
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> Long incr(KeyPrefix prefix, String key){
        //获取Jedis
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String reallyKey = prefix.getPrefix()+key;
            return jedis.incr(reallyKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 自减
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> Long decr(KeyPrefix prefix, String key){
        //获取Jedis
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String reallyKey = prefix.getPrefix()+key;
            return jedis.decr(reallyKey);
        }finally {
            returnToPool(jedis);
        }
    }

    private <T> String beanToString(T value) {
        if(value == null){
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class){
            return ""+value;
        }else if (clazz == String.class){
            return (String)value;
        }else if (clazz == long.class || clazz == Long.class){
            return ""+value;
        }
        return JSON.toJSONString(value);
    }

    private <T> T strToBean(String str, Class<T> clazz) {
        if (str == null || str.length() <= 0 || clazz == null){
            return null;
        }
        if (clazz == int.class || clazz == Integer.class){
            return (T) Integer.valueOf(str);
        }else if (clazz == String.class){
            return (T)str;
        }else if (clazz == long.class || clazz == Long.class){
            return (T)Long.valueOf(str);
        }
        return JSON.toJavaObject(JSON.parseObject(str),clazz);

    }


    /**
     * 将jedis返回连接池
     * @param jedis
     */
    public void returnToPool(Jedis jedis){
        if (jedis != null){
            jedis.close();
        }
    }
}

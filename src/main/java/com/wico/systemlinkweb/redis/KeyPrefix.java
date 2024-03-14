package com.wico.systemlinkweb.redis;

/**
 * @Author XuCheng
 * @Date 2020-6-6 19:25
 * 设置key的前缀，防止多人开发时将key覆盖
 */
public interface KeyPrefix {

    /**
     * 设置有效期
     * @return
     */
    int expireSeconds();

    /**
     * 获取前缀
     * @return
     */
    String getPrefix();
}

package com.wico.systemlinkweb.redis;

/**
 * @Author XuCheng
 * @Date 2020-6-6 19:43
 */
public class UserKey extends BasePrefix {
    private static final int TOKEN_EXPIRE = 3600;

    public UserKey(int expireSeconds,String prefix) {
        super(expireSeconds, prefix);
    }

    public static UserKey token = new UserKey(TOKEN_EXPIRE,"tk");
    public static UserKey getById = new UserKey(TOKEN_EXPIRE,"id");
}

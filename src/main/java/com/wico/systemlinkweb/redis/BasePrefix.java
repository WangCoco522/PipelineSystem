package com.wico.systemlinkweb.redis;

/**
 * @Author XuCheng
 * @Date 2020-6-6 19:27
 */
public abstract class BasePrefix implements KeyPrefix {

    private int expireSeconds;

    private String prefix;

    public BasePrefix(String prefix) {  //0代表永不过期
        this(0,prefix);
    }

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() {//默认0代表用不过期
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}

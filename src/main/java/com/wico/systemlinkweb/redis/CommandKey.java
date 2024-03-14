package com.wico.systemlinkweb.redis;

/**
 * @Author XuCheng
 * @Date 2020-6-30 16:51
 */
public class CommandKey extends BasePrefix {

    public CommandKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static CommandKey commandKey = new CommandKey(60,"cd");
}

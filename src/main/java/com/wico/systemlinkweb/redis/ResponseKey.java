package com.wico.systemlinkweb.redis;

/**
 * @Author XuCheng
 * @Date 2020-6-30 16:48
 */
public class ResponseKey extends BasePrefix {

    public ResponseKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    public static ResponseKey responseKey = new ResponseKey(60,"rk");

}

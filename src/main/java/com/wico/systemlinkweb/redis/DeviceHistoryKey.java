package com.wico.systemlinkweb.redis;

/**
 * @Author XuCheng
 * @Date 2020-6-30 16:31
 */
public class DeviceHistoryKey extends BasePrefix {
    public DeviceHistoryKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static DeviceHistoryKey deviceHistory = new DeviceHistoryKey(60,"dh");

}

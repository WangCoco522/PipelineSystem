package com.wico.systemlinkweb.redis;

/**
 * @Author XuCheng
 * @Date 2020-6-30 14:32
 */
public class DeviceKey extends BasePrefix {
    public DeviceKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    public static DeviceKey deviceList = new DeviceKey(60,"dl");
}

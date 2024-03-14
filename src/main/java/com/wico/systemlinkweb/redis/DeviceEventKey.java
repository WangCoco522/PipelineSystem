package com.wico.systemlinkweb.redis;

/**
 * @Author XuCheng
 * @Date 2020-6-30 16:43
 */
public class DeviceEventKey extends BasePrefix {
    public DeviceEventKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static DeviceEventKey deviceEvent = new DeviceEventKey(60,"de");

}

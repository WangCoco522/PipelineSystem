package com.wico.systemlinkweb.redis;

/**
 * @Author XuCheng
 * @Date 2020-6-30 15:08
 */
public class DeviceAttributeKey extends BasePrefix {
    public DeviceAttributeKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    public static DeviceAttributeKey deviceAttribute = new DeviceAttributeKey(3600,"da");
}

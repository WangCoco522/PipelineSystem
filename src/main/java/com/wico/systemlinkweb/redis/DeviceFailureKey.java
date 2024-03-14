package com.wico.systemlinkweb.redis;

/**
 * @Author XuCheng
 * @Date 2020-6-30 15:23
 */
public class DeviceFailureKey extends BasePrefix{

    public DeviceFailureKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    public static DeviceFailureKey deviceFailure = new DeviceFailureKey(3600 * 24,"df");

}

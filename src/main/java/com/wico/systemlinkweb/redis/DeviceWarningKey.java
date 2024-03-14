package com.wico.systemlinkweb.redis;

/**
 * @Author XuCheng
 * @Date 2020-6-30 16:40
 */
public class DeviceWarningKey extends BasePrefix {
    public DeviceWarningKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    public static DeviceWarningKey deviceWarning = new DeviceWarningKey(60,"dw");
}

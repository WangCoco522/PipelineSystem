package com.wico.systemlinkweb.domain.command;

import lombok.Data;

/**
 * @Author XuCheng
 * @Date 2020/5/6 15:08
 */
public class RequestCommand02 {
    private String deviceIdKeys;
    private String uRLKey;

    public String getDeviceIdKeys() {
        return deviceIdKeys;
    }

    public void setDeviceIdKeys(String deviceIdKeys) {
        this.deviceIdKeys = deviceIdKeys;
    }

    public String getuRLKey() {
        return uRLKey;
    }

    public void setuRLKey(String uRLKey) {
        this.uRLKey = uRLKey;
    }
}

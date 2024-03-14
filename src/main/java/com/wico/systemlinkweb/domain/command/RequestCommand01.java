package com.wico.systemlinkweb.domain.command;

import lombok.Data;

/**
 * @Author XuCheng
 * @Date 2020/5/6 14:35
 */

public class RequestCommand01 {
    private String deviceIdKeys;
    private String iPKey;
    private String portKey;

    public String getDeviceIdKeys() {
        return deviceIdKeys;
    }

    public void setDeviceIdKeys(String deviceIdKeys) {
        this.deviceIdKeys = deviceIdKeys;
    }

    public String getiPKey() {
        return iPKey;
    }

    public void setiPKey(String iPKey) {
        this.iPKey = iPKey;
    }

    public String getPortKey() {
        return portKey;
    }

    public void setPortKey(String portKey) {
        this.portKey = portKey;
    }
}

package com.wico.systemlinkweb.domain.command;

import lombok.Data;

@Data
public class RequestCommand20 {
    private String deviceIdKeys;
    private String uRLKey;
    private String partsKey;

    public String getuRLKey() {
        return uRLKey;
    }

    public void setuRLKey(String uRLKey) {
        this.uRLKey = uRLKey;
    }
}

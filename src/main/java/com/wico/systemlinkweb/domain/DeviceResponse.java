package com.wico.systemlinkweb.domain;

import lombok.Data;

/**
 * @Author XuCheng
 * @Date 2020/4/29 13:02
 */
@Data
public class DeviceResponse {
    private Long id;
    private String deviceIDKey;
    private String ts;
    private String response;
    private String commandCode;
}

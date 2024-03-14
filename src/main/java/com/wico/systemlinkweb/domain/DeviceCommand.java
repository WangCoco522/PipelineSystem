package com.wico.systemlinkweb.domain;

import lombok.Data;

/**
 * @Author XuCheng
 * @Date 2020/4/29 14:21
 */
@Data
public class DeviceCommand {
    private long id;
    private String deviceIdKey;
    private String ts;
    private String commandCode;
    private String command;
}

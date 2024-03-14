package com.wico.systemlinkweb.domain.command;

import lombok.Data;

/**
 * @Author XuCheng
 * @Date 2020/5/6 15:39
 */
@Data
public class RequestCommand08 {
    private String deviceIdKeys;
    private String readIntervalKey;
    private String upTimeKey;
    private String upPeriodKey;
    private Boolean securityKey;
    private String businessPeriodKey;
}

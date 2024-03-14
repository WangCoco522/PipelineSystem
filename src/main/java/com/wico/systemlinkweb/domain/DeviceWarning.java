package com.wico.systemlinkweb.domain;

import lombok.Data;

/**
 * @Author XuCheng
 * @Date 2020/4/28 16:13
 */
@Data
public class DeviceWarning {
    private Long id;
    private String deviceIdKey;  //设备id
    private String warningIdKey;    //警告id
    private String warningTimeKey;   //警告时间
    private String warningContentKey;  //警告累积量
    private String warningInfo;  //告警详情
    private String ts;
}

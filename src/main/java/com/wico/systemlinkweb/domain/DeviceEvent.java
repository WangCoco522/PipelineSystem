package com.wico.systemlinkweb.domain;

import lombok.Data;

/**
 * @Author XuCheng
 * @Date 2020/4/29 10:10
 */
@Data
public class DeviceEvent {
    private Long id;
    private String deviceIdKey;  //设备id
    private String eventIdKey;    //事件id
    private String eventTimeKey;   //事件时间
    private String eventContentKey;  //事件累积量
    private String eventInfo;   //事件详情
}

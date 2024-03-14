package com.wico.systemlinkweb.domain.command;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class Command20 {
    @JSONField(name = "DeviceIDKey")
    private String deviceIdKey;  //设备id
    @JSONField(name = "OPTypeKey")
    private String oPTypeKey;    //操作类型
    @JSONField(name = "URLKey")
    private String uRLKey;        //下载地址
    @JSONField(name = "PartsKey")
    private String partsKey;        //下载版本
    @JSONField(name = "RandNumKey")
    private String randNumKey;   //随机数
    @JSONField(name = "SecurityKey")
    private Boolean securityKey;  //是否加密
    @JSONField(name = "PuidKey")
    private String puidKey;       //加密插件ID
    @JSONField(name = "TaskIDKey")
    private String taskIDKey;    //指令id
}

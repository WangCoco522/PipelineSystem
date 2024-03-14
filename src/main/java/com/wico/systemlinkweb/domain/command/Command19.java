package com.wico.systemlinkweb.domain.command;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class Command19 {
    @JSONField(name = "DeviceIDKey")
    private String deviceIdKey;  //设备id
    @JSONField(name = "OPTypeKey")
    private String oPTypeKey;    //操作类型
    @JSONField(name = "UnitPriceKey")
    private String unitPriceKey;  // 单价
    @JSONField(name = "AlarmRMBKey")
    private String alarmRMBKey;  // 预警关阀阈值
    @JSONField(name = "CloseRMBKey")
    private String closeRMBKey;  // 欠费关阀阈值
    @JSONField(name = "LostDaysKey")
    private String lostDaysKey;  // 通信失联天数
    @JSONField(name = "RandNumKey")
    private String randNumKey;  // 随机数
    @JSONField(name = "SecurityKey")
    private Boolean securityKey;  //是否加密
    @JSONField(name = "PuidKey")
    private String puidKey;       //加密插件ID
    @JSONField(name = "TaskIDKey")
    private String taskIDKey;    //指令id
}

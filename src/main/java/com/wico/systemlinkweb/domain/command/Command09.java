package com.wico.systemlinkweb.domain.command;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Author XuCheng
 * @Date 2020/5/6 15:49
 */
// 远程关阀使能命令
@Data
public class Command09 {
    @JSONField(name = "DeviceIDKey")
    private String deviceIdKey;  //设备id
    @JSONField(name = "OPTypeKey")
    private String oPTypeKey;    //操作类型
    @JSONField(name = "EnableKey")
    private Integer enableKey;        //使能控制字
    @JSONField(name = "StartTimeKey")
    private String startTimeKey;      //起始间隔时间
    @JSONField(name = "HoldingTimeKey")
    private String holdingTimeKey;      //保持时间
    @JSONField(name = "RandNumKey")
    private String randNumKey;   //随机数
    @JSONField(name = "SecurityKey")
    private Boolean securityKey;  //是否加密
    @JSONField(name = "PuidKey")
    private String puidKey;       //加密插件ID
    @JSONField(name = "TaskIDKey")
    private String taskIDKey;    //指令id
}

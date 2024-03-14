package com.wico.systemlinkweb.domain.command;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Author XuCheng
 * @Date 2020/5/6 16:59
 */
// 非计量故障关阀使能命令
@Data
public class Command14 {
    @JSONField(name = "DeviceIDKey")
    private String deviceIdKey;  //设备id
    @JSONField(name = "OPTypeKey")
    private String oPTypeKey;    //操作类型
    @JSONField(name = "CloseValveKey")
    private String closeValveKey;        //故障关阀
    @JSONField(name = "CloseValveEventKey")
    private String closeValveEventKey;      //关阀时间状态位
    @JSONField(name = "RandNumKey")
    private String randNumKey;   //随机数
    @JSONField(name = "SecurityKey")
    private Boolean securityKey;  //是否加密
    @JSONField(name = "PuidKey")
    private String puidKey;       //加密插件ID
    @JSONField(name = "TaskIDKey")
    private String taskIDKey;    //指令id
}

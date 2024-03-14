package com.wico.systemlinkweb.domain.command;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Author XuCheng
 * @Date 2020/5/6 17:04
 */
// 清除命令
@Data
public class Command15 {
    @JSONField(name = "DeviceIDKey")
    private String deviceIdKey;  //设备id
    @JSONField(name = "OPTypeKey")
    private String oPTypeKey;    //操作类型
    @JSONField(name = "RandNumKey")
    private String randNumKey;   //随机数
    @JSONField(name = "SecurityKey")
    private Boolean securityKey;  //是否加密
    @JSONField(name = "PuidKey")
    private String puidKey;       //加密插件ID
    @JSONField(name ="TaskIDKey" )
    private String taskIDKey;    //指令id
    @JSONField(name = "killed")
    private String killed;      //清除命令原因
    @JSONField(name = "status")
    private String status;  //命令执行状态
    @JSONField(name = "PortKey")
    private String PortKey; //端口
}

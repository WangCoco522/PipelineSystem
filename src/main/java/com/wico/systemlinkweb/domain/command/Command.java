package com.wico.systemlinkweb.domain.command;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @Author XuCheng
 * @Date 2020/4/29 15:27
 */
// 更新SOTP加密协议插件命令
@Data
public class Command {
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

    private Integer isEncry; // 是否加密


}

package com.wico.systemlinkweb.domain.command;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Author XuCheng
 * @Date 2020-6-8 10:35
 */
// 清除结束报文命令
@Data
public class Command16 {
    @JSONField(name = "BusinessPeriodKey")
    private String businessPeriodKey;
    @JSONField(name = "DateTimeKey")
    private String dateTimeKey;
    @JSONField(name = "DeviceIDKey")
    private String deviceIDKey;
    @JSONField(name = "OPTypeKey")
    private String oPTypeKey;
    @JSONField(name = "PuidKey")
    private String puidKey;
    @JSONField(name = "RandNumKey")
    private String randNumKey;
    @JSONField(name = "ReadIntervalKey")
    private String readIntervalKey;
    @JSONField(name = "SecurityKey")
    private String securityKey;
    @JSONField(name = "TaskIDKey")
    private String taskIDKey;
    @JSONField(name = "UPPeriodKey")
    private String uPPeriodKey;
    @JSONField(name = "UPTimeKey")
    private String uPTimeKey;
    @JSONField(name = "killed")
    private String killed;      //清除命令原因
    @JSONField(name = "status")
    private String status;  //命令执行状态
}

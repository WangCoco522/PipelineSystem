package com.wico.systemlinkweb.domain.command;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Author XuCheng
 * @Date 2020/5/6 15:41
 */
// 结束报文命令
@Data
public class Command08 {
    @JSONField(name = "DeviceIDKey")
    private String deviceIdKey;  //设备id
    @JSONField(name = "OPTypeKey")
    private String oPTypeKey;    //操作类型
    @JSONField(name = "DateTimeKey")
    private String dateTimeKey;  //日期时间
    @JSONField(name = "ReadIntervalKey")
    private String readIntervalKey; //采集周期
    @JSONField(name = "UPTimeKey")
    private String uPTimeKey;  //下次上传时间
    @JSONField(name = "UPPeriodKey")
    private String uPPeriodKey; //下次上传周期
    @JSONField(name = "RandNumKey")
    private String randNumKey;   //随机数
    @JSONField(name = "SecurityKey")
    private Boolean securityKey;  //是否加密
    @JSONField(name = "PuidKey")
    private String puidKey;       //加密插件ID
    @JSONField(name = "TaskIDKey")
    private String taskIDKey;    //指令id
    @JSONField(name = "BusinessPeriodKey")
    private String businessPeriodKey;
}

package com.wico.systemlinkweb.domain.command;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Author XuCheng
 * @Date 2020/4/29 15:31
 */

// 修改网络参数命令
public class Command01 {
    @JSONField(name = "DeviceIDKey")
    private String deviceIdKey;  //设备id
    @JSONField(name = "OPTypeKey")
    private String oPTypeKey;    //操作类型
    @JSONField(name = "IPKey")
    private String iPKey;        //ip
    @JSONField(name = "PortKey")
    private String portKey;      //端口
    @JSONField(name = "RandNumKey")
    private String randNumKey;   //随机数
    @JSONField(name = "SecurityKey")
    private Boolean securityKey;  //是否加密
    @JSONField(name = "PuidKey")
    private String puidKey;       //加密插件ID
    @JSONField(name = "TaskIDKey")
    private String taskIDKey;    //指令id

    public String getDeviceIdKey() {
        return deviceIdKey;
    }

    public void setDeviceIdKey(String deviceIdKey) {
        this.deviceIdKey = deviceIdKey;
    }

    public String getoPTypeKey() {
        return oPTypeKey;
    }

    public void setoPTypeKey(String oPTypeKey) {
        this.oPTypeKey = oPTypeKey;
    }

    public String getiPKey() {
        return iPKey;
    }

    public void setiPKey(String iPKey) {
        this.iPKey = iPKey;
    }

    public String getPortKey() {
        return portKey;
    }

    public void setPortKey(String portKey) {
        this.portKey = portKey;
    }

    public String getRandNumKey() {
        return randNumKey;
    }

    public void setRandNumKey(String randNumKey) {
        this.randNumKey = randNumKey;
    }

    public Boolean getSecurityKey() {
        return securityKey;
    }

    public void setSecurityKey(Boolean securityKey) {
        this.securityKey = securityKey;
    }

    public String getPuidKey() {
        return puidKey;
    }

    public void setPuidKey(String puidKey) {
        this.puidKey = puidKey;
    }

    public String getTaskIDKey() {
        return taskIDKey;
    }

    public void setTaskIDKey(String taskIDKey) {
        this.taskIDKey = taskIDKey;
    }
}

package com.wico.systemlinkweb.domain.command;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Author XuCheng
 * @Date 2020/5/6 15:54
 */
@Data
public class Command10 {
    @JSONField(name = "DeviceIDKey")
    private String deviceIdKey;  //设备id
    @JSONField(name = "OPTypeKey")
    private String oPTypeKey;    //操作类型
    @JSONField(name = "Alerm1VHKey")
    private Float alerm1VHKey;        //报警流量1上限
    @JSONField(name = "Alerm1VLKey")
    private Float alerm1VLKey;        //报警流量1下限
    @JSONField(name = "Alerm1TimeKey")
    private Float alerm1TimeKey;        //报警流量1持续
    @JSONField(name = "Alerm2VHKey")
    private Float alerm2VHKey;        //报警流量2上限
    @JSONField(name = "Alerm2VLKey")
    private Float alerm2VLKey;        //报警流量2下限
    @JSONField(name = "Alerm2TimeKey")
    private Float alerm2TimeKey;        //报警流量2持续
    @JSONField(name = "Alerm3VHKey")
    private Float alerm3VHKey;        //报警流量3上限
    @JSONField(name = "Alerm3VLKey")
    private Float alerm3VLKey;        //报警流量3下限
    @JSONField(name = "Alerm3TimeKey")
    private Float alerm3TimeKey;        //报警流量3持续
    @JSONField(name = "RandNumKey")
    private String randNumKey;   //随机数
    @JSONField(name = "SecurityKey")
    private Boolean securityKey;  //是否加密
    @JSONField(name = "PuidKey")
    private String puidKey;       //加密插件ID
    @JSONField(name = "TaskIDKey")
    private String taskIDKey;    //指令id

    public Command10(Float alerm1VHKey, Float alerm1VLKey, Float alerm1TimeKey, Float alerm2VHKey, Float alerm2VLKey, Float alerm2TimeKey, Float alerm3VHKey, Float alerm3VLKey, Float alerm3TimeKey) {
        this.alerm1VHKey = alerm1VHKey;
        this.alerm1VLKey = alerm1VLKey;
        this.alerm1TimeKey = alerm1TimeKey;
        this.alerm2VHKey = alerm2VHKey;
        this.alerm2VLKey = alerm2VLKey;
        this.alerm2TimeKey = alerm2TimeKey;
        this.alerm3VHKey = alerm3VHKey;
        this.alerm3VLKey = alerm3VLKey;
        this.alerm3TimeKey = alerm3TimeKey;
    }

    public Command10(){
    }
}

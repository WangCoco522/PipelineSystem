package com.wico.systemlinkweb.domain;

import lombok.Data;

/**
 * @Author XuCheng
 * @Date 2020/4/26 16:41
 */
@Data
public class Device {
    private Long id;  //id
    private String deviceIDKey;   //设备号
    private String deviceType;   //设备类型
    private String collectTimeKey; //最新采集时间
    private String latestCode;       //最新抄码
    private String isWarning;       //是否报警
    private String communityName;  //小区地点
    //    private String kind_code;
    private String kind;
    private String kindContent;
    private String puidKey;         //是否加密
    private int isEncry;   //是否加密
    public String randNum; // 随机数
    public String controlCode;
}

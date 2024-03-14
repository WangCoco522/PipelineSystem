package com.wico.systemlinkweb.domain;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @Author XuCheng
 * @Date 2020/4/27 13:49
 * 设备属性
 */
@Data
public class DeviceAttribute {
    private String deviceIdKey;         //设备号
    private String commNoKey;           //imei
    private String deviceProvince;      //所在省
    private String deviceCity;          //所在市
    private String communityName;       //小区地址
    private String simNumber;           //通讯号码
    private String householdId;         //用户编号
    private String householdName;       //户主姓名
    private String householdNumber;    //户主身份证号
    private String telephone;       //户主电话
    private String installTime;      //设备安装时间
    private String deviceType;      //设备类型
    private String company;        //销售公司
    private String deviceProduct;  //设备厂商
    private String swrlseKey;       //版本号
    private BigDecimal latestCode;  //最新抄码
}



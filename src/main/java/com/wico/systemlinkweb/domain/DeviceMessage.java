package com.wico.systemlinkweb.domain;

import lombok.Data;

/**
 * @Author XuCheng
 * @Date 2020/4/28 15:04
 */
@Data
public class DeviceMessage {
    private Long id;    //序号
    private String deviceIdKey;  //设备ID号
    private String rssiKey;    //信号强度
    private Double realVolKey;  //工况体积
    private Double materV1Key;  //仪表电压
    private Double materV2Key;  //基表电池电压
    private Double temperatureKey; //温度
    private String statusKey;   //状态字
    private String meterStatusWordKey;  //表内工作状态字
    private Double totalStandardFlowKey; //标况累计量
    private Double totalRealFlowKey;     //工况累积量
    private Double standardFlowKey;      //标况瞬时量
    private Double realFlowKey;          //工况瞬时量
    private Double pressureKey;          //压力值
    private Double peakFlowKey;          //时段峰值量
    private Double interfereVolKey;      //受扰累计体积
    private String compressFactorKey;    //校正系数
    private String ip;                    //ip
    private String writeTime;             //写入时间
    private String validData;             //收到有效数据
    private String collecttimekey;         //最近抄表时间
    private Double frozenFlowKey;          //冻结量
}

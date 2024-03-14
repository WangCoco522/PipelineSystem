package com.wico.systemlinkweb.vo;

import com.wico.systemlinkweb.domain.DeviceRange;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author XuCheng
 * @Date 2020/4/26 16:50
 */
@Data
public class QueryVo {
    private int currentPage;
    private int pageSize;
    private String orderField;   //排序列名
    private String orderType;    //排序类型
    private String deviceIdKey;  //设备编号
    private String deviceNumber;   //模糊查询设备编号
    private int timeInterval;   //报警时间间隔
    private String deviceType;      //设备类型
    private String company;         //销售公司
    private String startTime;       //开始时间
    private String endTime;         //结束时间
    private String deviceProduct;   //生产产商
    private int start;
    private String kindCode;               //表类型
    private String username;            //用户名
    private int  screenTime;

    private List<String> kindControl;

    private List<DeviceRange> deviceRanges;    //权限控制表号的范围
    private String manufacturerId;


    private Date date;      //日期

    public int getStart() {
        return (currentPage-1)*pageSize;
    }
}

package com.wico.systemlinkweb.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.wico.systemlinkweb.domain.*;
import com.wico.systemlinkweb.domain.command.Command;
import com.wico.systemlinkweb.domain.command.Command01;
import com.wico.systemlinkweb.mapper.CommandMapper;
import com.wico.systemlinkweb.mapper.DeviceMapper;
import com.wico.systemlinkweb.redis.*;
import com.wico.systemlinkweb.result.Result;
import com.wico.systemlinkweb.service.IDeviceService;
import com.wico.systemlinkweb.utils.*;
import com.wico.systemlinkweb.vo.QueryVo;



import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class DeviceServiceImpl implements IDeviceService {
    @Autowired
    DeviceMapper deviceMapper;

    @Autowired
    RedisService redisService;


    @Autowired
    CommandMapper commandMapper;

    private static final String GOULD_KEY = "75b56a7692513bda04c55afbf00b6e5f";

    private static Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);

    String[] deviceTypeList = {"皮膜", "修正仪", "超声波", "预付费膜式表", "预付费超声波表"};

    String url = "http://47.103.45.47:8080/api/v1/ei2TBYaYZHcwUfBxSfYz/telemetry";

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置格式


    @Override
    public PageBean<SensorAttribute> findSensorAttribute(QueryVo queryVo) {
        logger.info(String.valueOf(queryVo));
        if (queryVo.getCurrentPage() == 0) {
            queryVo.setCurrentPage(1);
        }
        if (queryVo.getPageSize() == 0) {
            queryVo.setPageSize(10);
        }
        if (queryVo.getTimeInterval() == 0) {   //报警时间间隔
            queryVo.setTimeInterval(3);
        }
        if (queryVo.getOrderField() == null) {      //排序列名
            queryVo.setOrderField("collecttimekey");
        }
        if (queryVo.getOrderType() == null) {
            queryVo.setOrderType("desc");
        }
        logger.info(String.valueOf(queryVo));

        PageBean<SensorAttribute> pageBean = new PageBean<>();

        List<SensorAttribute> list = deviceMapper.findSensorAttribute(queryVo);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setId((long) i + 1 + queryVo.getStart());
        }
        // 分页显示
        pageBean.setList(list);
        pageBean.setTotalCount(deviceMapper.findSensorAttributeTotalCount(queryVo));
        pageBean.setPageSize(queryVo.getPageSize());
        pageBean.setCurrentPage(queryVo.getCurrentPage());
        return pageBean;
    }

    @Override
    public PageBean<SensorMessage> findSensorMessage(QueryVo queryVo) {
        logger.info(String.valueOf(queryVo));
        if (queryVo.getCurrentPage() == 0) {
            queryVo.setCurrentPage(1);
        }
        if (queryVo.getPageSize() == 0) {
            queryVo.setPageSize(10);
        }
        if (queryVo.getTimeInterval() == 0) {   //报警时间间隔
            queryVo.setTimeInterval(3);
        }
//        if (queryVo.getOrderField() == null) {      //排序列名
//            queryVo.setOrderField("ts");
//        }
        if (queryVo.getOrderType() == null) {
            queryVo.setOrderType("desc");
        }
        PageBean<SensorMessage> pageBean = new PageBean<>();
        List<SensorMessage> list = deviceMapper.findSensorMessage(queryVo);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setId((long) i + 1 + queryVo.getStart());
            //list.get(i).setTs(TimeCovert.stampToDate(list.get(i).getTs()));
        }
        // 分页显示
        pageBean.setList(list);
        pageBean.setTotalCount(deviceMapper.findSensorMessageTotalCount(queryVo));
        pageBean.setPageSize(queryVo.getPageSize());
        pageBean.setCurrentPage(queryVo.getCurrentPage());
        return pageBean;
    }

}

/*

    @Override
    // 获取主页面设备列表
    public PageBean<Device> findBypage(QueryVo queryVo) {
        if (queryVo.getCurrentPage() == 0) {
            queryVo.setCurrentPage(1);
        }
        if (queryVo.getPageSize() == 0) {
            queryVo.setPageSize(10);
        }
        if (queryVo.getTimeInterval() == 0) {   //报警时间间隔
            queryVo.setTimeInterval(3);
        }
        if (queryVo.getOrderField() == null) {      //排序列名
            queryVo.setOrderField("collecttimekey");
        }
        if (queryVo.getOrderType() == null) {
            queryVo.setOrderType("desc");
        }
        //从redis中取数据，若存在则直接返回，不存在则将数据存入缓存
//        PageBean<Device> pageBean = redisService.get(DeviceKey.deviceList,""+queryVo.getCurrentPage(),PageBean.class);
//        if(pageBean != null){
//            return pageBean;
//        }
        PageBean<Device> pageBean = new PageBean<>();
        Map<String,String> kindDefs = KindServiceImpl.getKindDefs();
        List<Device> list = deviceMapper.findByPage(queryVo);
        for (int i = 0; i < list.size(); i++){
            // 是否加密
            if (list.get(i).getIsEncry() == 0 || list.get(i).equals("")){
                list.get(i).setPuidKey("否");
            }else {
                list.get(i).setPuidKey("是");
            }
            list.get(i).setId((long)i + 1 + queryVo.getStart());
            queryVo.setDeviceIdKey(list.get(i).getDeviceIDKey());
            // 是否报警
            int count = deviceMapper.findWarningCount(queryVo);
            if (count != 0){
                list.get(i).setIsWarning("是");
            }else {
                list.get(i).setIsWarning("无");
            }
            // 设置设备类型
            String deviceType = list.get(i).getDeviceType();
            switch (deviceType){
                case "00":
                    list.get(i).setDeviceType(deviceTypeList[0]);
                    break;
                case "01":
                    list.get(i).setDeviceType(deviceTypeList[1]);
                    break;
                case "03":
                    list.get(i).setDeviceType(deviceTypeList[2]);
                    break;
                case "40":
                    list.get(i).setDeviceType(deviceTypeList[3]);
                    break;
                case "43":
                    list.get(i).setDeviceType(deviceTypeList[4]);
                    break;
                default:
                    break;
            }
            list.get(i).setKindContent(kindDefs.get(list.get(i).getKind()));
        }
        pageBean.setTotalCount(deviceMapper.findTotalCount(queryVo));
        // 设置页面当前数据
        pageBean.setList(list);
        pageBean.setPageSize(queryVo.getPageSize());
        pageBean.setCurrentPage(queryVo.getCurrentPage());
//        redisService.set(DeviceKey.deviceList,"" + pageBean.getCurrentPage(), pageBean);
        return pageBean;
    }

    @Override
    public PageBean<Device> findSpecial(QueryVo queryVo) {
        if (queryVo.getCurrentPage() == 0) {
            queryVo.setCurrentPage(1);
        }
        if (queryVo.getPageSize() == 0) {
            queryVo.setPageSize(10);
        }
        if (queryVo.getTimeInterval() == 0) {   //报警时间间隔
            queryVo.setTimeInterval(3);
        }
        if (queryVo.getOrderField() == null) {      //排序列名
            queryVo.setOrderField("collecttimekey");
        }
        if (queryVo.getOrderType() == null) {
            queryVo.setOrderType("desc");
        }
        //从redis中取数据，若存在则直接返回，不存在则将数据存入缓存
//        PageBean<Device> pageBean = redisService.get(DeviceKey.deviceList,""+queryVo.getCurrentPage(),PageBean.class);
//        if(pageBean != null){
//            return pageBean;
//        }
        PageBean<Device> pageBean = new PageBean<>();
        Map<String,String> kindDefs = KindServiceImpl.getKindDefs();
        List<Device> list = deviceMapper.findSpecial(queryVo);
        for (int i = 0; i < list.size(); i++){
            // 是否加密
            if (list.get(i).getPuidKey() == null || list.get(i).equals("")){
                list.get(i).setPuidKey("否");
            }else {
                list.get(i).setPuidKey("是");
            }
            list.get(i).setId((long)i + 1 + queryVo.getStart());
            queryVo.setDeviceIdKey(list.get(i).getDeviceIDKey());
            // 是否报警
            int count = deviceMapper.findWarningCount(queryVo);
            if (count != 0){
                list.get(i).setIsWarning("是");
            }else {
                list.get(i).setIsWarning("无");
            }
            // 设置设备类型
            String deviceType = list.get(i).getDeviceType();
            switch (deviceType){
                case "00":
                    list.get(i).setDeviceType(deviceTypeList[0]);
                    break;
                case "01":
                    list.get(i).setDeviceType(deviceTypeList[1]);
                    break;
                case "03":
                    list.get(i).setDeviceType(deviceTypeList[2]);
                    break;
                default:
                    break;
            }
            list.get(i).setKindContent(kindDefs.get(list.get(i).getKind()));
        }
        pageBean.setTotalCount(deviceMapper.findTotalSpecialCount(queryVo));
        // 设置页面当前数据
        pageBean.setList(list);
        pageBean.setPageSize(queryVo.getPageSize());
        pageBean.setCurrentPage(queryVo.getCurrentPage());
//        redisService.set(DeviceKey.deviceList,"" + pageBean.getCurrentPage(), pageBean);
        return pageBean;
    }

    // 根据设备id获取设备信息
    @Override
    public DeviceAttribute findAttributeById(String deviceId) {
//        DeviceAttribute deviceAttribute = redisService.get(DeviceAttributeKey.deviceAttribute,""+deviceId,DeviceAttribute.class);
//        if (deviceAttribute != null){
//            return deviceAttribute;
//        }
        // 根据设备id获取设备信息
        DeviceAttribute deviceAttribute = deviceMapper.findAttributeById(deviceId);
        // 设置设备类型
        switch (deviceAttribute.getDeviceType()){
            case "00":
                deviceAttribute.setDeviceType(deviceTypeList[0]);
                break;
            case "01":
                deviceAttribute.setDeviceType(deviceTypeList[1]);
                break;
            case "03":
                deviceAttribute.setDeviceType(deviceTypeList[2]);
                break;
            default:
                break;
        }
//        redisService.set(DeviceAttributeKey.deviceAttribute,"" + deviceId, deviceAttribute);
        return deviceAttribute;
    }

    // 更新设备表信息
    @Override
    public boolean updateAttribute(DeviceAttribute deviceAttribute) {
        switch (deviceAttribute.getDeviceType()){
            case "皮膜":
                deviceAttribute.setDeviceType("00");
                break;
            case "修正仪":
                deviceAttribute.setDeviceType("01");
                break;
            case "超声波":
                deviceAttribute.setDeviceType("03");
                break;
            default:
                break;
        }
        try {
            deviceMapper.updateAttribute(deviceAttribute);
            //更新redis缓存
//            redisService.set(DeviceAttributeKey.deviceAttribute,"" + deviceAttribute.getDeviceIdKey(), deviceAttribute);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    // 查询多日未上报数据的设备，并返回list
    @Override
    public PageBean<Device> findFailure(QueryVo queryVo) {
        if (queryVo.getCurrentPage() == 0) {
            queryVo.setCurrentPage(1);
        }
        if (queryVo.getPageSize() == 0) {
            queryVo.setPageSize(10);
        }
        if (queryVo.getTimeInterval() == 0) {   //报警时间间隔
            queryVo.setTimeInterval(3);
        }
        if (queryVo.getOrderField() == null) {      //排序列名
            queryVo.setOrderField("collecttimekey");
        }
        if (queryVo.getOrderType() == null) {
            queryVo.setOrderType("desc");
        }
//        PageBean<Device> pageBean = redisService.get(DeviceFailureKey.deviceFailure,""+queryVo.getCurrentPage() + queryVo.getScreenTime(), PageBean.class);
//        if (pageBean != null){
//            return pageBean;
//        }
        PageBean<Device> pageBean = new PageBean<>();
        Map<String,String> kindDefs = KindServiceImpl.getKindDefs();
        // 查询多日未上报设备
        List<Device> list = deviceMapper.findFailure(queryVo);
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).getPuidKey() == null || list.get(i).equals("")){
                list.get(i).setPuidKey("否");
            }else {
                list.get(i).setPuidKey("是");
            }
            list.get(i).setId((long)i + 1 + queryVo.getStart());
            queryVo.setDeviceIdKey(list.get(i).getDeviceIDKey());
            int count = deviceMapper.findWarningCount(queryVo);
            if (count != 0){
                list.get(i).setIsWarning("是");
            }else {
                list.get(i).setIsWarning("无");
            }
            String deviceType = list.get(i).getDeviceType();
            switch (deviceType){
                case "00":
                    list.get(i).setDeviceType(deviceTypeList[0]);
                    break;
                case "01":
                    list.get(i).setDeviceType(deviceTypeList[1]);
                    break;
                case "03":
                    list.get(i).setDeviceType(deviceTypeList[2]);
                    break;
                default:
                    break;
            }
            list.get(i).setKindContent(kindDefs.get(list.get(i).getKind()));
        }
        // 查询多日未上报数据的数目
        pageBean.setTotalCount(deviceMapper.findFailureTotalCount(queryVo));
        pageBean.setList(list);
        pageBean.setPageSize(queryVo.getPageSize());
        pageBean.setCurrentPage(queryVo.getCurrentPage());
//        redisService.set(DeviceFailureKey.deviceFailure, "" + pageBean.getCurrentPage() + queryVo.getScreenTime(), pageBean);
        return pageBean;
    }

    // 查询历史上报信息
    @Override
    public PageBean<DeviceMessage> findHistory(QueryVo queryVo) {
        if (queryVo.getCurrentPage() == 0) {
            queryVo.setCurrentPage(1);
        }
        if (queryVo.getPageSize() == 0) {
            queryVo.setPageSize(10);
        }
        if (queryVo.getTimeInterval() == 0) {   //报警时间间隔
            queryVo.setTimeInterval(3);
        }
        if (queryVo.getOrderField() == null) {      //排序列名
            queryVo.setOrderField("collecttimekey");
        }
        if (queryVo.getOrderType() == null) {
            queryVo.setOrderType("desc");
        }
//        PageBean<DeviceMessage> pageBean = redisService.get(DeviceHistoryKey.deviceHistory,""+ queryVo.getCurrentPage() + queryVo.getDeviceIdKey(), PageBean.class);
//        if (pageBean != null){
//            return pageBean;
//        }
        PageBean<DeviceMessage> pageBean = new PageBean<>();
        // 查询历史上报信息
        List<DeviceMessage> list = deviceMapper.findHistory(queryVo);
        logger.info(String.valueOf(list));
        for (int i = 0; i < list.size(); i++){
            list.get(i).setId((long)i + 1 + queryVo.getStart());
            SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyyMMddHHmm");
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            // 最近抄表时间
            String time = "20"+list.get(i).getCollecttimekey();
            // 转换时间格式
            try {
                list.get(i).setCollecttimekey(simpleDateFormat1.format(simpleDateFormat.parse(time)));
            }catch (Exception e){
                System.out.println("时间转换失败！");
            }
        }
        pageBean.setList(list);
        pageBean.setCurrentPage(queryVo.getCurrentPage());
        pageBean.setPageSize(queryVo.getPageSize());
        pageBean.setTotalCount(deviceMapper.findMessageTotal(queryVo.getDeviceIdKey()));
//        redisService.set(DeviceHistoryKey.deviceHistory,""+ queryVo.getCurrentPage() + queryVo.getDeviceIdKey(), pageBean);
        return pageBean;
    }

    // 查询设备告警信息
    @Override
    public PageBean<DeviceWarning> findWarning(QueryVo queryVo) {
        String[] warningList = {"拆表","反向安装","计量模块故障","温度传感器故障",
                "压力传感器故障","流量异常","通信电压低","计量电压低","采集故障",
                "燃气泄漏故障","窃气关阀","复位关阀","计量低电压关阀","超声波计量故障关阀","电磁干扰","脉动流量",
                "关阀指令关阀","人工开阀","其他事件"};

        if (queryVo.getCurrentPage() == 0) {
            queryVo.setCurrentPage(1);
        }
        if (queryVo.getPageSize() == 0) {
            queryVo.setPageSize(10);
        }
        if (queryVo.getTimeInterval() == 0) {   //报警时间间隔
            queryVo.setTimeInterval(3);
        }
        if (queryVo.getOrderField().equals("")) {      //排序列名
            queryVo.setOrderField("ts");
        }
        if (queryVo.getOrderType() == null) {
            queryVo.setOrderType("desc");
        }
//        PageBean<DeviceWarning> pageBean = redisService.get(DeviceWarningKey.deviceWarning, "" + queryVo.getCurrentPage() + queryVo.getDeviceIdKey(), PageBean.class);
//        if (pageBean != null){
//            return pageBean;
//        }
        PageBean<DeviceWarning> pageBean = new PageBean<>();
        // 查询告警信息
        List<DeviceWarning> list = deviceMapper.findWarning(queryVo);
        for (int i = 0; i < list.size(); i++){
            list.get(i).setId((long)i + 1 + queryVo.getStart());
            list.get(i).setWarningTimeKey(TimeCovert.stringToDate(list.get(i).getWarningTimeKey() + "00"));
            list.get(i).setTs(TimeCovert.stampToDate(list.get(i).getTs()));
            System.out.println(list.get(i).getTs());
            // 获取设备的分类/来源kind
            String kind = deviceMapper.findKindByDeviceId(list.get(i).getDeviceIdKey());
            // 警告id
            String code = list.get(i).getWarningIdKey();
            // 获取故障类型
            String content = deviceMapper.findWarningInfoByKindAndId(kind, code,"Alarm");
            // 设置告警详情
            list.get(i).setWarningInfo(content);
        }
        // 分页显示
        pageBean.setList(list);
        // 告警信息数目
        pageBean.setTotalCount(deviceMapper.findWarningTotalCount(queryVo.getDeviceIdKey()));
        pageBean.setPageSize(queryVo.getPageSize());
        pageBean.setCurrentPage(queryVo.getCurrentPage());
//        redisService.set(DeviceWarningKey.deviceWarning, "" + queryVo.getCurrentPage() + queryVo.getDeviceIdKey(), pageBean);
        return pageBean;
    }

    // 获取设备事件信息
    @Override
    public PageBean<DeviceEvent> findEvent(QueryVo queryVo) {

        String[] eventList = {"拆表","反向安装","计量模块故障","温度传感器故障","压力传感器故障",
                "流量异常","通信电压低","计量电压低","采集故障","燃气泄漏关阀","窃气关阀","复位关阀",
                "计量低电压关阀","超声波计量故障关阀","电磁干扰","脉动流量","其他事件"};

        if (queryVo.getCurrentPage() == 0) {
            queryVo.setCurrentPage(1);
        }
        if (queryVo.getPageSize() == 0) {
            queryVo.setPageSize(10);
        }
        if (queryVo.getTimeInterval() == 0) {   //报警时间间隔
            queryVo.setTimeInterval(3);
        }
        if (queryVo.getOrderField() == null) {      //排序列名
            queryVo.setOrderField("eventtimekey");
        }
        if (queryVo.getOrderType() == null) {
            queryVo.setOrderType("desc");
        }
//        PageBean<DeviceEvent> pageBean = redisService.get(DeviceEventKey.deviceEvent, "" + queryVo.getCurrentPage() + queryVo.getDeviceIdKey(), PageBean.class);
//        if(pageBean != null){
//            return pageBean;
//        }
        PageBean<DeviceEvent> pageBean = new PageBean<>();
        // 获取设备事件信息
        List<DeviceEvent> list = deviceMapper.findEvent(queryVo);
        for (int i = 0; i < list.size(); i++){
            if(list.get(i).getEventIdKey() == null || list.get(i).getEventTimeKey() == null || list.get(i).getEventContentKey() == null)
                continue;
            list.get(i).setId((long)i + 1 + queryVo.getStart());
            //logger.info(list.get(i).getEventTimeKey());
            list.get(i).setEventTimeKey(TimeCovert.stringToDate(list.get(i).getEventTimeKey() + "00"));
            //logger.info("转换后："+list.get(i).getEventTimeKey());
            // 获取设备的分类/来源kind
            String kind = deviceMapper.findKindByDeviceId(list.get(i).getDeviceIdKey());
            // 事件id
            String code = list.get(i).getEventIdKey();
            // 获取事件详情
            String content = deviceMapper.findWarningInfoByKindAndId(kind, code,"Event");
            // 设置事件详情
            list.get(i).setEventInfo(content);
        }
        list.removeIf(s -> s.getEventContentKey() == null || s.getEventTimeKey() == null || s.getEventIdKey() == null);
        pageBean.setList(list);
        pageBean.setCurrentPage(queryVo.getCurrentPage());
        pageBean.setPageSize(queryVo.getPageSize());
        // 事件信息数目 分页显示
        pageBean.setTotalCount(deviceMapper.findEventTotalCount(queryVo.getDeviceIdKey()));
//        redisService.set(DeviceEventKey.deviceEvent, "" + queryVo.getCurrentPage() + queryVo.getDeviceIdKey(), pageBean);
        return pageBean;
    }

    // 获取命令响应信息
    @Override
    public PageBean<DeviceResponse> findResponse(QueryVo queryVo) {
        if (queryVo.getCurrentPage() == 0) {
            queryVo.setCurrentPage(1);
        }
        if (queryVo.getPageSize() == 0) {
            queryVo.setPageSize(10);
        }
        if (queryVo.getTimeInterval() == 0) {   //报警时间间隔
            queryVo.setTimeInterval(3);
        }
        if (queryVo.getOrderField() == null) {      //排序列名
            queryVo.setOrderField("ts");
        }
        if (queryVo.getOrderType() == null) {
            queryVo.setOrderType("desc");
        }
        //PageBean<DeviceResponse> pageBean = redisService.get(ResponseKey.responseKey, "" + queryVo.getCurrentPage() + queryVo.getDeviceIdKey(), PageBean.class);
//        if (pageBean != null){
//            return pageBean;
//        }
        // 查询命令响应信息
        PageBean<DeviceResponse> pageBean = new PageBean<>();
        List<DeviceResponse> list = deviceMapper.findResponse(queryVo);
        for (int i = 0; i < list.size(); i++){
            list.get(i).setId((long)i + 1 + queryVo.getStart());
            list.get(i).setTs(TimeCovert.stampToDate(list.get(i).getTs()));
        }
        // 分页显示
        pageBean.setList(list);
        pageBean.setTotalCount(deviceMapper.findResponseTotalCount(queryVo.getDeviceIdKey()));
        pageBean.setPageSize(queryVo.getPageSize());
        pageBean.setCurrentPage(queryVo.getCurrentPage());
        //redisService.set(ResponseKey.responseKey, "" + queryVo.getCurrentPage() + queryVo.getDeviceIdKey(), pageBean);
        return pageBean;
    }

    // 获取设备命令列表
    @Override
    public PageBean<DeviceCommand> findCommand(QueryVo queryVo) {
        if (queryVo.getCurrentPage() == 0) {
            queryVo.setCurrentPage(1);
        }
        if (queryVo.getPageSize() == 0) {
            queryVo.setPageSize(10);
        }
        if (queryVo.getTimeInterval() == 0) {   //报警时间间隔
            queryVo.setTimeInterval(3);
        }
        if (queryVo.getOrderField() == null) {      //排序列名
            queryVo.setOrderField("ts");
        }
        if (queryVo.getOrderType() == null) {
            queryVo.setOrderType("desc");
        }
//        PageBean<DeviceCommand> pageBean = redisService.get(CommandKey.commandKey, "" + queryVo.getCurrentPage() + queryVo.getDeviceIdKey(), PageBean.class);
//        if (pageBean != null){
//            return pageBean;
//        }
        PageBean<DeviceCommand> pageBean = new PageBean<>();
        // 获取设备命令列表
        List<DeviceCommand> list = deviceMapper.findCommand(queryVo);
        for (int i = 0; i < list.size(); i++){
            list.get(i).setId((long)i + 1 + queryVo.getStart());
            list.get(i).setTs(TimeCovert.stampToDate(list.get(i).getTs()));
        }
        pageBean.setList(list);
        pageBean.setCurrentPage(queryVo.getCurrentPage());
        pageBean.setPageSize(queryVo.getPageSize());
        pageBean.setTotalCount(deviceMapper.findCommandTotalCount(queryVo.getDeviceIdKey()));
//        redisService.set(CommandKey.commandKey, "" + queryVo.getCurrentPage() + queryVo.getDeviceIdKey(), pageBean);
        return pageBean;
    }


    // 解析控制码，检查是否符合出厂标准
    @Override
    public Map<String, Map<String, Object>> changeNet(String[] deviceIdKeys) {
        String url = "http://47.103.45.47:8080/api/v1/ei2TBYaYZHcwUfBxSfYz/telemetry";
        Map<String, Map<String, Object>> map = new HashMap<>();
        for (int i = 0; i < deviceIdKeys.length; i++) {
            Map<String, Object> map1 = new HashMap<>();
            DeviceMessage deviceMessage = null;
            try {
                // 根据设备id 获取设备历史上报的信息
                deviceMessage = deviceMapper.findMessageById(deviceIdKeys[i]);
            } catch (Exception e) {
                System.out.println("未查询到数据");
            }
            if (deviceMessage == null) {
                String string = "未获取到相关数据";
                map1.put("error", string);
                map1.put("swrlseKey", null);
                map1.put("rssiKey", null);
                map1.put("totalFlowKey", null);
                map1.put("SOTPEncryption", null);
                map1.put("validData", null);
                map1.put("ModifyNetPar", null);
                map1.put("downloadPlug", null);
                map1.put("SOTPLogin", null);
                map1.put("status", null);
                map.put(deviceIdKeys[i], map1);
                continue;
            }
            map1.put("error", null);
            // 信息中的有效数据
            String validData = deviceMessage.getValidData();
            map1.put("validData", validData);
            String b2 = validData.substring(16, 24);
            String b2Str = HexToBinaryStr.bytes2BinaryStr(HexToBinaryStr.hexStringToByte(b2));
            System.out.println(b2Str);
            // 下载插件标记
            if (b2Str.charAt(3) == '0') {
                map1.put("downloadPlug", true);
            } else if (b2Str.charAt(3) == '1') {
                map1.put("downloadPlug", false);
            }
            // SOTP登录验证标记
            if (b2Str.charAt(4) == '0') {
                map1.put("SOTPLogin", true);
            } else if (b2Str.charAt(4) == '1') {
                map1.put("SOTPLogin", false);
            }
            // SOTP加密验证标记
            if (b2Str.charAt(5) == '0') {
                map1.put("SOTPEncryption", true);
            } else if (b2Str.charAt(5) == '1') {
                map1.put("SOTPEncryption", false);
            }
            // 获取该设备的出厂标准条件信息
            DevicePassCondition devicePassCondition = deviceMapper.findDevicePassConditionById(1);
            DeviceAttribute deviceAttribute = null;
            try {
                deviceAttribute = deviceMapper.findAttributeById(deviceIdKeys[i]);
            }catch (Exception e){
                String string = "未获取到相关数据";
                map1.put("error", string);
                map1.put("swrlseKey", null);
                map1.put("rssiKey", null);
                map1.put("totalFlowKey", null);
                map1.put("validData", null);
                map1.put("ModifyNetPar", null);
                map1.put("status", null);
                map.put(deviceIdKeys[i], map1);
                continue;
            }
            //获取累积量
            BigDecimal totalFlowKey = deviceAttribute.getLatestCode();
            if ((totalFlowKey != null && totalFlowKey.compareTo(devicePassCondition.getTotalFlowFloorLimit()) == 1 && totalFlowKey.compareTo(devicePassCondition.getTotalFlowUpperLimit()) == -1) || totalFlowKey.compareTo(devicePassCondition.getTotalFlowFloorLimit()) == 0 || totalFlowKey.compareTo(devicePassCondition.getTotalFlowUpperLimit()) == 0) {
                map1.put("totalFlowKey", true);
            } else {
                map1.put("totalFlowKey", false);
            }
            //获取CSQ
            String rssiKey = deviceMessage.getRssiKey();
            if (rssiKey != null && BigDecimal.valueOf(Integer.valueOf(rssiKey)).compareTo(devicePassCondition.getRssiFloorLimit()) == 1 && BigDecimal.valueOf(Integer.valueOf(rssiKey)).compareTo(devicePassCondition.getRssiUpperLimit()) == -1) {
                map1.put("rssiKey", true);
            } else {
                map1.put("rssiKey", false);
            }
            //版本号
            String swrlseKey = devicePassCondition.getSwrlse();
            String testswlseKey = null;
            switch (deviceAttribute.getSwrlseKey().length()){
                case 1:
                    testswlseKey = "0" + deviceAttribute.getSwrlseKey();
                    break;
                case 2:
                    testswlseKey = deviceAttribute.getSwrlseKey();
                    break;
                case 4:
                    testswlseKey = deviceAttribute.getSwrlseKey().substring(2, 4);
                    break;
                default:
                    break;
            }
            if (testswlseKey != null && testswlseKey.equals(swrlseKey)) {
                map1.put("swrlseKey", true);
            } else {
                map1.put("swrlseKey", false);
            }
            // 标志位
            if (map1.get("downloadPlug").equals(false) || map1.get("SOTPLogin").equals(false) || map1.get("SOTPEncryption").equals(false) || map1.get("swrlseKey").equals(false)
                    || map1.get("totalFlowKey").equals(false) || map1.get("rssiKey").equals(false) || map1.get("swrlseKey").equals(false)) {
                map1.put("status", false);
            } else {
                map1.put("status", true);
            }
            if (map1.get("status").equals(true)) {
                Command01 command01 = new Command01();
                command01.setoPTypeKey("01");
                command01.setiPKey("nbrc.shgas.com.cn");
                command01.setPortKey("6101");
                command01.setDeviceIdKey(deviceIdKeys[i]);
                Command command = commandMapper.getCommandAttribute(deviceIdKeys[i]);
                if (command == null) {
                    command01.setSecurityKey(false);
                    command01.setPuidKey("");
                } else if (command.getPuidKey() == null || ("").equals(command.getPuidKey())) {
                    command01.setSecurityKey(false);
                    command01.setPuidKey("");
                } else {
                    command01.setSecurityKey(true);
                    command01.setPuidKey(command.getPuidKey());
                }
                command01.setRandNumKey("00000000");
                command01.setTaskIDKey(UUIDUtils.getUUID());
                JSONObject request = new JSONObject();
                request.put(deviceIdKeys[i], JSONObject.toJSONString(command01));
                JSONObject response = null;
                try {
                    response = HttpClientUtils.doPost(url,request);
                    map1.put("ModifyNetPar", true);
                } catch (Exception e) {
                    System.out.println("修改网络参数命令下发失败");
                    map1.put("ModifyNetPar", false);
                }
            } else {
                map1.put("ModifyNetPar", false);
            }
            map.put(deviceIdKeys[i], map1);
        }
            return map;
    }

    // 设置出场标准
    @Override
    public boolean addDevicePassCondition(DevicePassCondition devicePassCondition) {
        String userMsg = devicePassCondition.getUser();
        JSONObject jsonObject = JSONObject.parseObject(userMsg);
        String userId = jsonObject.getString("id");
        devicePassCondition.setUserId(Integer.parseInt(userId));
        if (!deviceMapper.findDevicePassCondition(Integer.parseInt(userId))){
            deviceMapper.insertDevicePassCondition(devicePassCondition);
        }else {
            deviceMapper.updateDevicePassCondition(devicePassCondition);
        }
        return true;
    }

    // 获取每日上报统计结果
    @Override
    public DeviceStatics getStatics(QueryVo queryVo) {
        return deviceMapper.getStatics(queryVo);
    }

    // 导出抄表记录至excel
    @Override
    public ResponseEntity<byte[]> exportMessage(String deviceIdKeys) throws Exception{
        List<DeviceMessage> deviceList = deviceMapper.findHistoryById(deviceIdKeys);
        File file1 = ExcelUtils.CreateFile();
        //创建工作簿
        WritableWorkbook workbook = jxl.Workbook.createWorkbook(file1);
        WritableSheet sheet = workbook.createSheet("抄表信息",0);
        String[] titles = {"设备ID","温度","CSQ信号强度","状态字","通信电压","计量电压","采集时间","表内工作状态字","标况累计量","工况累计量","标况瞬时量","工况瞬时量","压力值","时段峰值量","有效数据"};
        Label label = null;
        for (int i = 0; i <titles.length; i++){
            label = new Label(i,0,titles[i]);
            sheet.addCell(label);
        }
        for (int i = 0; i < deviceList.size(); i++){
            label = new Label(0,i+1,deviceList.get(i).getDeviceIdKey());
            sheet.addCell(label);
            label = new Label(1,i+1,deviceList.get(i).getTemperatureKey()+"");
            sheet.addCell(label);
            label = new Label(2,i+1,deviceList.get(i).getRssiKey());
            sheet.addCell(label);
            label = new Label(3,i+1,deviceList.get(i).getStatusKey());
            sheet.addCell(label);
            label = new Label(4,i+1,deviceList.get(i).getMaterV2Key()+"");
            sheet.addCell(label);
            label = new Label(5,i+1,deviceList.get(i).getMaterV1Key()+"");
            sheet.addCell(label);
            SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyyMMddHHmm");
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String time = "20"+deviceList.get(i).getCollecttimekey();
            try {
                label = new Label(6,i+1,simpleDateFormat1.format(simpleDateFormat.parse(time)));
                sheet.addCell(label);
            }catch (Exception e){
                System.out.println("时间转换失败！");
            }
            label = new Label(7,i+1,deviceList.get(i).getMeterStatusWordKey()+"");
            sheet.addCell(label);
            label = new Label(8,i+1,deviceList.get(i).getTotalStandardFlowKey()+"");
            sheet.addCell(label);
            label = new Label(9,i+1,deviceList.get(i).getTotalRealFlowKey()+"");
            sheet.addCell(label);
            label = new Label(10,i+1,deviceList.get(i).getStandardFlowKey()+"");
            sheet.addCell(label);
            label = new Label(11,i+1,deviceList.get(i).getRealFlowKey()+"");
            sheet.addCell(label);
            label = new Label(12,i+1,deviceList.get(i).getPressureKey()+"");
            sheet.addCell(label);
            label = new Label(13,i+1,deviceList.get(i).getPeakFlowKey()+"");
            sheet.addCell(label);
            label = new Label(14,i+1,deviceList.get(i).getValidData()+"");
            sheet.addCell(label);
        }
        workbook.write();
        workbook.close();
        ResponseEntity<byte[]> result = ExcelUtils.download(file1);
        file1.delete();
        return result;
    }

    // 批量导出属性表至excel
    @Override
    public ResponseEntity<byte[]> exportAttribute(String deviceIdKeys) throws Exception {
        List<DeviceAttribute> deviceList = new ArrayList<>();
        String[] strings = deviceIdKeys.trim().split(",");
        DeviceAttribute deviceAttribute = null;
        for(int i = 0; i < strings.length; i++){
            // 查询设备详细信息
            deviceAttribute = deviceMapper.findAttributeById(strings[i]);
            switch (deviceAttribute.getDeviceType()){
                case "00":
                    deviceAttribute.setDeviceType(deviceTypeList[0]);
                    break;
                case "01":
                    deviceAttribute.setDeviceType(deviceTypeList[1]);
                    break;
                case "03":
                    deviceAttribute.setDeviceType(deviceTypeList[2]);
                    break;
                default:
                    break;
            }
            deviceList.add(deviceAttribute);
        }
        //创建excel文件
        File file1 = ExcelUtils.CreateFile();
        //创建工作簿
        WritableWorkbook workbook = jxl.Workbook.createWorkbook(file1);
        //创建sheet
        WritableSheet sheet = workbook.createSheet("设备信息",0);
        String[] titles = {"设备编号","IMEI","当前软件版本号","所在省","所在市","小区地址","通讯号码","用户编号","户主姓名","户主身份证号","户主电话","设备安装时间","设备类型","销售公司","设备厂商","最新抄码"};
        Label label = null;
        for (int i = 0; i <titles.length; i++){
            label = new Label(i,0,titles[i]);
            sheet.addCell(label);
        }
        // 将设备信息写入excel文件中
        for (int i = 0; i < deviceList.size(); i++){
            label = new Label(0,i+1,deviceList.get(i).getDeviceIdKey());
            sheet.addCell(label);
            label = new Label(1,i+1,deviceList.get(i).getCommNoKey());
            sheet.addCell(label);
            label = new Label(2,i+1,deviceList.get(i).getSwrlseKey());
            sheet.addCell(label);
            label = new Label(3,i+1,deviceList.get(i).getDeviceProvince());
            sheet.addCell(label);
            label = new Label(4,i+1,deviceList.get(i).getDeviceCity());
            sheet.addCell(label);
            label = new Label(5,i+1,deviceList.get(i).getCommunityName());
            sheet.addCell(label);
            label = new Label(6,i+1,deviceList.get(i).getSimNumber());
            sheet.addCell(label);
            label = new Label(7,i+1,deviceList.get(i).getHouseholdId());
            sheet.addCell(label);
            label = new Label(8,i+1,deviceList.get(i).getHouseholdName());
            sheet.addCell(label);
            label = new Label(9,i+1,deviceList.get(i).getHouseholdNumber());
            sheet.addCell(label);
            label = new Label(10,i+1,deviceList.get(i).getTelephone());
            sheet.addCell(label);
            label = new Label(11,i+1,deviceList.get(i).getInstallTime());
            sheet.addCell(label);
            label = new Label(12,i+1,deviceList.get(i).getDeviceType());
            sheet.addCell(label);
            label = new Label(13,i+1,deviceList.get(i).getCompany());
            sheet.addCell(label);
            if (deviceList.get(i).getDeviceProduct()!=null){
                label = new Label(14,i+1,deviceList.get(i).getDeviceProduct());
                sheet.addCell(label);
            }
            if (deviceList.get(i).getLatestCode()!=null){
                label = new Label(15,i+1,deviceList.get(i).getLatestCode().toString());
                sheet.addCell(label);
            }
        }
        workbook.write();
        workbook.close();
        ResponseEntity<byte[]> result = ExcelUtils.download(file1);
        file1.delete();
        return result;
    }

    // 通过excel批量下发更新插件命令
    @Override
    public ResponseEntity<byte[]> upload(MultipartFile file) throws IOException, BiffException, WriteException {
        // 获取文件名
        FileInputStream inputStream = null;
        try {
            inputStream = (FileInputStream) file.getInputStream();
        }catch (Exception e){
            System.out.println("文件格式不正确");
            return null;
        }
        String[] deviceIdKeys = ExcelUtils.getSheet(inputStream);
        //创建excel文件
        File file1 = ExcelUtils.CreateFile();
        WritableWorkbook workbook = jxl.Workbook.createWorkbook(file1);
        WritableSheet sheet = workbook.createSheet("更新SOTP插件结果", 0);
        String[] titles = {"设备编号", "处理结果"};
        Label label = null;
        for (int i = 0; i < titles.length; i++) {
            label = new Label(i, 0, titles[i]);
            sheet.addCell(label);
        }
        for (int i = 0; i < deviceIdKeys.length; i++) {
            label = new Label(0, i + 1, deviceIdKeys[i]);
            sheet.addCell(label);
            System.out.println(deviceIdKeys[i]);
            if ("".equals(deviceIdKeys[i]) || deviceIdKeys[i].length() != 12) {
                label = new Label(1, i + 1, "表号长度不正确");
                sheet.addCell(label);
            } else {
                try {
                    // 创建更新SOTP插件的指令并发送指令
                    Command command = new Command();
                    command.setOPTypeKey("15");
                    command.setDeviceIdKey(deviceIdKeys[i]);
                    Command command1 = commandMapper.getCommandAttribute(deviceIdKeys[i]);
                    if (command1 == null) {
                        command.setSecurityKey(false);
                        command.setPuidKey("");
                        command.setRandNumKey("00000000");
                    } else {
                        if (command1.getPuidKey() == null || ("").equals(command1.getPuidKey())) {
                            command.setSecurityKey(false);
                            command.setPuidKey("");
                        } else {
                            command.setSecurityKey(true);
                            command.setPuidKey(command1.getPuidKey());
                        }
                        command.setRandNumKey(command1.getRandNumKey());
                    }
                    command.setTaskIDKey(UUIDUtils.getUUID());
                    JSONObject request = new JSONObject();
                    request.put(command.getDeviceIdKey(), JSONObject.toJSONString(command));
                    JSONObject response = null;
                    try {
                        // 记录指令的执行结果
                        response = HttpClientUtils.doPost(url, request);
                        label = new Label(1, i + 1, "下载成功");
                        sheet.addCell(label);
                    } catch (Exception e) {
                        label = new Label(1, i + 1, "下载失败");
                        sheet.addCell(label);
                    }
                } catch (Exception e) {
                    label = new Label(1, i + 1, "下载失败");
                    sheet.addCell(label);
                }
            }
        }
        workbook.write();
        workbook.close();
        ResponseEntity<byte[]> result = ExcelUtils.download(file1);
        file1.delete();
        return result;
    }

    // 通过excel批量检查设备是否符合出厂条件
    @Override
    public ResponseEntity<byte[]> exportChangeNetResult(MultipartFile file, String user) throws IOException, BiffException, WriteException {
        FileInputStream fileInputStream = (FileInputStream)file.getInputStream();
        String[] deviceIdKeys = ExcelUtils.getSheet(fileInputStream);
        //创建excel文件
        File file1 = ExcelUtils.CreateFile();
        //创建工作簿
        WritableWorkbook workbook = jxl.Workbook.createWorkbook(file1);
        //创建sheet
        WritableSheet sheet = workbook.createSheet("获取状态值",0);
        String[] titles = {"设备编号","错误信息","下载插件标记","SOTP登陆验证标记","SOTP加密验证标记","累积量","CSQ","版本号","status","修改网络参数"};
        Label label = null;
        for (int i = 0; i <titles.length; i++){
            label = new Label(i,0,titles[i]);
            sheet.addCell(label);
        }
        for (int i = 0; i < deviceIdKeys.length; i++) {
            Map<String,Object> map1 = new HashMap<>();
            label = new Label(0, i+1, deviceIdKeys[i]);
            sheet.addCell(label);
            System.out.println(deviceIdKeys[i]);
            if ("".equals(deviceIdKeys[i]) || deviceIdKeys[i].length() != 12) {
                label = new Label(1, i+1, "表号长度不正确");
                sheet.addCell(label);
            }else{
                DeviceMessage deviceMessage = null;
                try {
                    deviceMessage = deviceMapper.findMessageById(deviceIdKeys[i]);
                } catch (Exception e) {
                    System.out.println("未查询到数据");
                }
                if (deviceMessage == null) {
                    String string = "未获取到相关数据";
                    continue;
                }
                String validData = deviceMessage.getValidData();
                String b2 = validData.substring(16, 24);
                String b2Str = HexToBinaryStr.bytes2BinaryStr(HexToBinaryStr.hexStringToByte(b2));
                // 下载插件标记
                if (b2Str.charAt(3) == '0') {
                    label = new Label(2,i+1,"true");
                    sheet.addCell(label);
                    map1.put("downloadPlug", true);
                } else if (b2Str.charAt(3) == '1') {
                    label = new Label(2,i+1,"false");
                    sheet.addCell(label);
                    map1.put("downloadPlug", false);
                }
                // SOTP登陆验证标记
                if (b2Str.charAt(4) == '0') {
                    label = new Label(3,i+1,"true");
                    sheet.addCell(label);
                    map1.put("SOTPLogin", true);
                } else if (b2Str.charAt(4) == '1') {
                    label = new Label(3,i+1,"false");
                    sheet.addCell(label);
                    map1.put("SOTPLogin", false);
                }
                // SOTP加密验证标记
                if (b2Str.charAt(5) == '0') {
                    label = new Label(4,i+1,"true");
                    sheet.addCell(label);
                    map1.put("SOTPEncryption", true);
                } else if (b2Str.charAt(5) == '1') {
                    label = new Label(4,i+1,"false");
                    sheet.addCell(label);
                    map1.put("SOTPEncryption", false);
                }

                JSONObject user1 = JSONObject.parseObject(user);
                String userId = user1.getString("id");

                DevicePassCondition devicePassCondition = deviceMapper.findDevicePassConditionById(Integer.parseInt(userId));
                DeviceAttribute deviceAttribute = deviceMapper.findAttributeById(deviceIdKeys[i]);
                switch (deviceAttribute.getDeviceType()){
                    case "00":
                        deviceAttribute.setDeviceType(deviceTypeList[0]);
                        break;
                    case "01":
                        deviceAttribute.setDeviceType(deviceTypeList[1]);
                        break;
                    case "03":
                        deviceAttribute.setDeviceType(deviceTypeList[2]);
                        break;
                    default:
                        break;
                }
                //获取累积量
                BigDecimal totalFlowKey = deviceAttribute.getLatestCode();
                if ((totalFlowKey != null && totalFlowKey.compareTo(devicePassCondition.getTotalFlowFloorLimit()) == 1 && totalFlowKey.compareTo(devicePassCondition.getTotalFlowUpperLimit()) == -1) || totalFlowKey.compareTo(devicePassCondition.getTotalFlowFloorLimit()) == 0 || totalFlowKey.compareTo(devicePassCondition.getTotalFlowUpperLimit()) == 0) {
                    label = new Label(5,i+1,"true");
                    sheet.addCell(label);
                    map1.put("totalFlowKey", true);
                } else {
                    label = new Label(5,i+1,"false");
                    sheet.addCell(label);
                    map1.put("totalFlowKey", false);
                }
                //获取CSQ
                String rssiKey = deviceMessage.getRssiKey();
                if (rssiKey != null && BigDecimal.valueOf(Integer.valueOf(rssiKey)).compareTo(devicePassCondition.getRssiFloorLimit()) == 1 && BigDecimal.valueOf(Integer.valueOf(rssiKey)).compareTo(devicePassCondition.getRssiUpperLimit()) == -1) {
                    label = new Label(6,i+1,"true");
                    sheet.addCell(label);
                    map1.put("rssiKey", true);
                } else {
                    label = new Label(6,i+1,"false");
                    sheet.addCell(label);
                    map1.put("rssiKey", false);
                }
                //版本号
                String swrlseKey = devicePassCondition.getSwrlse();
                String testswlseKey = null;
                switch (deviceAttribute.getSwrlseKey().length()){
                    case 1:
                        testswlseKey = "0" + deviceAttribute.getSwrlseKey();
                        break;
                    case 2:
                        testswlseKey = deviceAttribute.getSwrlseKey();
                        break;
                    case 4:
                        testswlseKey = deviceAttribute.getSwrlseKey().substring(2, 4);
                        break;
                    default:
                        break;
                }
                if (testswlseKey != null && testswlseKey.equals(swrlseKey)) {
                    label = new Label(7,i+1,"true");
                    sheet.addCell(label);
                    map1.put("swrlseKey", true);
                } else {
                    label = new Label(7,i+1,"false");
                    sheet.addCell(label);
                    map1.put("swrlseKey", false);
                }
                if (map1.get("downloadPlug").equals(false) || map1.get("SOTPLogin").equals(false) || map1.get("SOTPEncryption").equals(false) || map1.get("swrlseKey").equals(false)
                        || map1.get("totalFlowKey").equals(false) || map1.get("rssiKey").equals(false) || map1.get("swrlseKey").equals(false)) {
                    map1.put("status", false);
                    label = new Label(8,i+1,"false");
                    sheet.addCell(label);
                } else {
                    map1.put("status", true);
                    label = new Label(8,i+1,"true");
                    sheet.addCell(label);
                }
                if (map1.get("status").equals(true)) {
                    Command command = new Command();
                    command.setOPTypeKey("15");
                    command.setDeviceIdKey(deviceIdKeys[i]);
                    Command command1 = commandMapper.getCommandAttribute(deviceIdKeys[i]);
                    if (command1 == null) {
                        command.setSecurityKey(false);
                        command.setPuidKey("");
                        command.setRandNumKey("00000000");
                    } else {
                        if (command1.getPuidKey() == null || ("").equals(command1.getPuidKey())) {
                            command.setSecurityKey(false);
                            command.setPuidKey("");
                        } else {
                            command.setSecurityKey(true);
                            command.setPuidKey(command1.getPuidKey());
                        }
                        command.setRandNumKey(command1.getRandNumKey());
                    }
                    command.setTaskIDKey(UUIDUtils.getUUID());
                    JSONObject request = new JSONObject();
                    request.put(command.getDeviceIdKey(), JSONObject.toJSONString(command));

                    JSONObject response = null;
                    try {
                        response = HttpClientUtils.doPost(url, request);
                        map1.put("ModifyNetPar", true);
                        label = new Label(9,i+1,"true");
                        sheet.addCell(label);
                    } catch (Exception e) {
                        System.out.println("修改网络参数命令下发失败");
                        label = new Label(9,i+1,"false");
                        sheet.addCell(label);
                        map1.put("ModifyNetPar", false);
                    }
                } else {
                    label = new Label(9,i+1,"false");
                    sheet.addCell(label);
                    map1.put("ModifyNetPar", false);
                }
            }
        }
        workbook.write();
        workbook.close();
        ResponseEntity<byte[]> result = ExcelUtils.download(file1);
        file1.delete();
        return result;
    }

    // 获取设备的地址信息
    @Override
    public List<DeviceLocation> findLocation() {
        return deviceMapper.findLocation();
    }

    // 获取设备经纬度信息
    @Override
    public String[][] findLatitude(DeviceLatitudeQuery deviceLatitudeQuery) {
        List<DeviceLatitude> list =  deviceMapper.findLatitude(deviceLatitudeQuery);
        String[][] latArray = new String[list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            latArray[i][0] = list.get(i).getBaiDuLng();
            latArray[i][1] = list.get(i).getBaiDuLat();
        }
        return latArray;
    }

    // 获取一个小时内有告警记录的设备的详细信息
    @Override
    public List<DeviceLatitude> findLatitudeInOneHour() {
        List<DeviceLatitude> list = deviceMapper.findLocationInOneHour();
        for (int i = 0; i < list.size(); i++){
            String deviceType = list.get(i).getDeviceType();
            switch (deviceType){
                case "00":
                    list.get(i).setDeviceType(deviceTypeList[0]);
                    break;
                case "01":
                    list.get(i).setDeviceType(deviceTypeList[1]);
                    break;
                case "03":
                    list.get(i).setDeviceType(deviceTypeList[2]);
                    break;
                default:
                    break;
            }
        }
        return list;
    }

    // 查询一天之内上报信息的离散情况
    @Override
    public List<DeviceDiscrete> findDiscrete(DeviceDiscreteQuery deviceDiscreteQuery) {
        return deviceMapper.findDiscrete(deviceDiscreteQuery);
    }

    // 获取设备离散信息具体到某一小时
    @Override
    public List<DeviceMinuteDiscrete> findMinuteDiscrete(DeviceMinuteDiscreteQuery deviceMinuteDiscreteQuery) {
        return deviceMapper.findMinuteDiscrete(deviceMinuteDiscreteQuery);
    }

    // 获取特定日期的抄表统计信息
    @Override
    public List<DeviceStatUpload> findStatUpload(DeviceStatUploadQuery deviceStatUploadQuery) {
        return deviceMapper.findStatUpload(deviceStatUploadQuery);
    }

    // 获取设备测本号信息
    @Override
    public List<DeviceBook> findBook() {
        return deviceMapper.findBook();
    }


    @Override
    public Result uploadExcel(Sheet sheet, Result result) throws Exception {
        List<String> titleList = new ArrayList<>();
        // 列数
        int column = sheet.getRow(0).getPhysicalNumberOfCells();
        logger.debug("Excel列数：" + column);
        // 此处可判断Excel列数是否符合要求

        // 行数
        int rows = sheet.getLastRowNum();
        logger.debug("Excel行数：" + rows);

        // 将Excel第一行表头添加到集合中
        Row row = sheet.getRow(0);
        for (int i = 0; i < column; i++) {
            row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
            titleList.add(row.getCell(i).getStringCellValue());
        }


        List<AttributeFromExcel> excelData = new ArrayList<>();
        AttributeFromExcel temporary;
        // 循环Excel
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
             temporary = new AttributeFromExcel();
            for (int j = 0; j < column; j++) {
                row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
                String fieldName = ExcelUtils.excelSqlMap.get(titleList.get(j));
                String value = row.getCell(j).getStringCellValue();
                if(fieldName == "deviceProduct"){
                    value = ExcelUtils.DeviceProductMap.get(value);
                }
                ExcelUtils.setFieldValueByName(temporary,fieldName,value);
            }
            //添加进list
            excelData.add(temporary);
        }

        // 此处省略其他操作处理
        // 此处省略其他操作处理

        // 做插入处理
        List<AttributeFromExcel> updateFailure = new ArrayList<>();
        if (excelData.size() > 0) {
            for (AttributeFromExcel attribute : excelData) {
                if (deviceMapper.updatePartAttribute(attribute) == 0)
                    updateFailure.add(attribute);
            }
            if(!updateFailure.isEmpty()){
                for (AttributeFromExcel attribute : updateFailure) {
                    deviceMapper.insertPartAttribute(attribute);
                }
            }
        }
        return result.success("保存数据成功");


    }

    @Override
    public List<DeviceRange> findDeviceRangeByUser(String user) {
        return deviceMapper.findDeviceRangeByUser(user);
    }

    @Override
    public String findKindRangeByRole(String userRole) {
        return deviceMapper.findKindRangeByRole(userRole);
    }

    // 查询某天未上报的设备信息
    @Override
    public PageBean<String> findNotUploadDeviceId(NotUploadDeviceIdQuery notUploadDeviceIdQuery) {
        if (notUploadDeviceIdQuery.getCurrentPage() == 0) {
            notUploadDeviceIdQuery.setCurrentPage(1);
        }
        if (notUploadDeviceIdQuery.getPageSize() == 0) {
            notUploadDeviceIdQuery.setPageSize(300);
        }
        PageBean<String> pageBean = new PageBean<>();
        notUploadDeviceIdQuery.setStart((notUploadDeviceIdQuery.getCurrentPage() - 1) * 300);
        List<String> list = deviceMapper.findNotUploadDeviceId(notUploadDeviceIdQuery);
        pageBean.setTotalCount(deviceMapper.findNotUploadTotalCount(notUploadDeviceIdQuery));
        pageBean.setList(list);
        pageBean.setPageSize(notUploadDeviceIdQuery.getPageSize());
        pageBean.setCurrentPage(notUploadDeviceIdQuery.getCurrentPage());

      return pageBean;
    }



    // 查询报错信息
    @Override
    public PageBean<GasError> findError(QueryVo queryVo) {
        logger.info("1"+ queryVo);
        if (queryVo.getCurrentPage() == 0) {
            queryVo.setCurrentPage(1);
        }
        if (queryVo.getPageSize() == 0) {
            queryVo.setPageSize(10);
        }
        if (queryVo.getTimeInterval() == 0) {   //报警时间间隔
            queryVo.setTimeInterval(3);
        }
        if (queryVo.getOrderField() == null || queryVo.getOrderField().equals("")) {      //排序列名
            queryVo.setOrderField("gas_ts");
        }
        if (queryVo.getOrderType() == null || queryVo.getOrderType().equals("")) {
            queryVo.setOrderType("desc");
        }
        logger.info(String.valueOf(queryVo));

        PageBean<GasError> pageBean = new PageBean<>();
        // 查询错误信息
        List<GasError> list = deviceMapper.findError(queryVo);
        for (int i = 0; i < list.size(); i++){
            list.get(i).setId((long)i + 1 + queryVo.getStart());
            Long gasts = list.get(i).getGasTs();
            String ts=format.format(gasts);
            list.get(i).setTime(ts);
        }
        // 分页显示
        pageBean.setList(list);
        // 告警错误信息数目
        pageBean.setTotalCount(deviceMapper.findErrorTotalCount(queryVo));
        pageBean.setPageSize(queryVo.getPageSize());
        pageBean.setCurrentPage(queryVo.getCurrentPage());
        return pageBean;
    }
}
*/

package com.wico.systemlinkweb.service;

import com.wico.systemlinkweb.domain.*;
import com.wico.systemlinkweb.result.Result;
import com.wico.systemlinkweb.utils.PageBean;
import com.wico.systemlinkweb.vo.QueryVo;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public interface IDeviceService {

    PageBean<SensorAttribute> findSensorAttribute(QueryVo queryVo);

    PageBean<SensorMessage> findSensorMessage(QueryVo queryVo);


    /*

    */
/**
     * 获取主页面设备列表
     * @param queryVo
     * @return
     *//*

    PageBean<Device> findBypage(QueryVo queryVo);

    PageBean<Device> findSpecial(QueryVo queryVo);

    */
/**
     * 获取设备详情页
     * @param deviceId
     * @return
     *//*

    DeviceAttribute findAttributeById(String deviceId);

    */
/**
     * 更新设备属性
     * @param deviceAttribute
     *//*

    boolean updateAttribute(DeviceAttribute deviceAttribute);

    */
/**
     * 查询未上报数据设备
     * @param queryVo
     * @return
     *//*

    PageBean<Device> findFailure(QueryVo queryVo);

    */
/**
     * 查询抄表详情页
     * @param queryVo
     * @return
     *//*

    PageBean<DeviceMessage> findHistory(QueryVo queryVo);

    */
/**
     * 获取告警详情页
     * @param queryVo
     * @return
     *//*

    PageBean<DeviceWarning> findWarning(QueryVo queryVo);

    */
/**
     * 获取报错信息详情页
     * @param queryVo
     * @return
     *//*

    PageBean<GasError> findError(QueryVo queryVo);

    */
/**
     * 获取事件详情页
     * @param queryVo
     * @return
     *//*

    PageBean<DeviceEvent> findEvent(QueryVo queryVo);

    */
/**
     * 获取应答详情页
     * @param queryVo
     * @return
     *//*

    PageBean<DeviceResponse> findResponse(QueryVo queryVo);

    */
/**
     * 获取命令详情页
     * @param queryVo
     * @return
     *//*

    PageBean<DeviceCommand> findCommand(QueryVo queryVo);


    */
/**
     * 获取设备的地址信息
     * @return
     *//*

    List<DeviceLocation> findLocation();


    */
/**
     * 获取设备经纬度信息
     * @return
     *//*

    String[][] findLatitude(DeviceLatitudeQuery deviceLatitudeQuery);

    */
/**
     * 获取一个小时内有抄表记录的设备地址
     * @return
     *//*

    List<DeviceLatitude> findLatitudeInOneHour();

    */
/**
     * 查询一天之内上报信息的离散情况
     * @return
     *//*

    List<DeviceDiscrete> findDiscrete(DeviceDiscreteQuery deviceDiscreteQuery);


    */
/**
     * 获取设备离散信息具体到某一小时
     * @param deviceMinuteDiscreteQuery
     * @return
     *//*

    List<DeviceMinuteDiscrete> findMinuteDiscrete(DeviceMinuteDiscreteQuery deviceMinuteDiscreteQuery );

    */
/**
     * 获取特定日期的抄表统计信息
     * @param deviceStatUploadQuery
     * @return
     *//*

    List<DeviceStatUpload> findStatUpload(DeviceStatUploadQuery deviceStatUploadQuery);
    */
/**
     * 获取所有测本号
     * @return
     *//*

    List<DeviceBook> findBook();

    */
/**
     * 解析控制码
     * @param deviceIdKeys
     * @return
     *//*

    Map<String, Map<String,Object>> changeNet(String[] deviceIdKeys);

    */
/**
     * 添加出厂标砖
     * @param devicePassCondition
     * @return
     *//*

    boolean addDevicePassCondition(DevicePassCondition devicePassCondition);

    */
/**
     * 获取每日上报统计结果
     * @param queryVo
     * @return
     *//*

    DeviceStatics getStatics(QueryVo queryVo);

    */
/**
     * 导出抄表记录至excel
     * @param deviceIdKeys
     * @return
     * @throws Exception
     *//*

     ResponseEntity<byte[]> exportMessage(String deviceIdKeys) throws Exception;
    */
/**
     * 批量导出属性表
     * @param deviceKeys
     * @return
     *//*

    ResponseEntity<byte[]> exportAttribute(String deviceKeys) throws Exception;

    */
/**
     * 通过excel批量下发更新插件命令
     * @param file
     * @return
     * @throws IOException
     * @throws BiffException
     * @throws WriteException
     *//*

    ResponseEntity<byte[]> upload(MultipartFile file) throws IOException, BiffException, WriteException;

    */
/**
     * 通过excel批量检查设备是否符合出厂条件
     * @param file
     * @param user
     * @return
     * @throws IOException
     * @throws BiffException
     * @throws WriteException
     *//*

    ResponseEntity<byte[]> exportChangeNetResult(MultipartFile file, @RequestParam String user) throws IOException, BiffException, WriteException;

    Result uploadExcel(Sheet sheet, Result result) throws Exception;

    List<DeviceRange> findDeviceRangeByUser(String user);

    String findKindRangeByRole(String userRole);

    // 查询某天未上报的设备信息
    PageBean<String>  findNotUploadDeviceId(NotUploadDeviceIdQuery notUploadDeviceIdQuery);

*/


}

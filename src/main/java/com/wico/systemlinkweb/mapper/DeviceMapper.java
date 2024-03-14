package com.wico.systemlinkweb.mapper;

import com.wico.systemlinkweb.domain.*;
import com.wico.systemlinkweb.vo.QueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.ClientInfoStatus;
import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DeviceMapper {
    List<Device> findByPage(QueryVo queryVo);

    List<Device> findSpecial(QueryVo queryVo);

    int findWarningCount(QueryVo queryVo);

    String findKindByDeviceId(String deviceIdKey);

    String findWarningInfoByKindAndId(@Param("kind") String kind, @Param("code") String code, @Param("level") String level);

    int findTotalCount(QueryVo queryVo);

    int findTotalSpecialCount(QueryVo queryVo);

    DeviceAttribute findAttributeById(String deviceId);

    void updateAttribute(DeviceAttribute deviceAttribute);

    List<Device> findFailure(QueryVo queryVo);

    int findFailureTotalCount(QueryVo queryVo);

    List<DeviceMessage> findHistory(QueryVo queryVo);

    List<DeviceMessage> findHistoryById(String deviceId);

    int findMessageTotal(String deviceIdKey);

    List<DeviceWarning> findWarning(QueryVo queryVo);

    int findWarningTotalCount(String deviceIdKey);

    List<DeviceEvent> findEvent(QueryVo queryVo);

    int findEventTotalCount(String deviceIdKey);

    List<DeviceResponse> findResponse(QueryVo queryVo);

    int findResponseTotalCount(String deviceIdKey);

    List<DeviceCommand> findCommand(QueryVo queryVo);

    int findCommandTotalCount(String deviceIdKey);

    DeviceMessage findMessageById(String deviceIdKey);

    DevicePassCondition findDevicePassConditionById(int userId);

    void updateDevicePassCondition(DevicePassCondition devicePassCondition);

    void insertDevicePassCondition(DevicePassCondition devicePassCondition);

    boolean findDevicePassCondition(int id);

    DeviceStatics getStatics(QueryVo queryVo);

    List<DeviceLocation> findLocation();

    List<DeviceLatitude> findLocationInOneHour();

    List<DeviceLatitude> findLatitude(DeviceLatitudeQuery deviceLatitudeQuery);

    List<DeviceDiscrete> findDiscrete(DeviceDiscreteQuery deviceDiscreteQuery );

    List<DeviceMinuteDiscrete> findMinuteDiscrete(DeviceMinuteDiscreteQuery deviceMinuteDiscreteQuery );

    List<DeviceBook> findBook();

    int updatePartAttribute(AttributeFromExcel attribute1);

    int updateTransfered(DeviceLocation deviceLocation);

    void insertLatitude(DeviceLatitude deviceLatitude);

    void insertPartAttribute(AttributeFromExcel attribute1);

    List<DeviceStatUpload> findStatUpload(DeviceStatUploadQuery deviceStatUploadQuery);

    List<DeviceRange> findDeviceRangeByUser(String user);

    String findKindRangeByRole(String userRole);

    List<String> findNotUploadDeviceId(NotUploadDeviceIdQuery notUploadDeviceIdQuery);

    int findNotUploadTotalCount(NotUploadDeviceIdQuery notUploadDeviceIdQuery);

    List<GasError> findError(QueryVo queryVo);

    int findErrorTotalCount(QueryVo queryVo);

    List<SensorAttribute> findSensorAttribute(QueryVo queryVo);

    int findSensorAttributeTotalCount(QueryVo queryVo);

    List<SensorMessage> findSensorMessage(QueryVo queryVo);

    int findSensorMessageTotalCount(QueryVo queryVo);

    void findSensor();

    Integer getCountByRegTime(@Param("regTime") String regTime);

    List<SensorMessage> selectAll();
}

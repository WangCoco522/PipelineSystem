package com.wico.systemlinkweb.controller;


import com.wico.systemlinkweb.domain.*;
import com.wico.systemlinkweb.mapper.DeviceMapper;
import com.wico.systemlinkweb.service.IDeviceService;
import com.wico.systemlinkweb.service.UserService;
import com.wico.systemlinkweb.utils.PageBean;
import com.wico.systemlinkweb.vo.QueryVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.*;

/**
 * 为了前端不做修改，这里返回值不做修改，Result包下的类暂时不使用，未区分get、post请求
 *
 * @Author XuCheng
 * @Date 2020/4/26 16:35
 */
@RestController
@RequestMapping("/device")
@Slf4j
public class DeviceController {
    @Autowired
    private IDeviceService deviceService;
    @Resource
    private UserService userService;

    @Resource
    private DeviceMapper deviceMapper;

    private static Logger logger = LoggerFactory.getLogger(DeviceController.class);

    /**
     * 设备主页面
     *
     * @param queryVo
     * @return
     */
    @CrossOrigin
    @ApiOperation("设备主页面和带参数查询")
    @RequestMapping(value = "/list")
    public Map<String, PageBean<SensorAttribute>> list(@RequestBody QueryVo queryVo) {
        User user = userService.getUserByUsername(queryVo.getUsername());
        queryVo.setManufacturerId(user.getManufacturerId());
        PageBean<SensorAttribute> pageBean = deviceService.findSensorAttribute(queryVo);
        //PageBean<Device> pageBean = deviceService.findBypage(queryVo);
        Map<String, PageBean<SensorAttribute>> map = new HashMap<>();
        map.put("deviceList", pageBean);
        return map;
    }


    /**
     * 查询历史信息
     *
     * @return
     */
    @CrossOrigin
    @ApiOperation("查询历史信息")
    @RequestMapping(value = "/findMessage")
    public Map<String, PageBean<SensorMessage>> findSensorMessage(@RequestBody QueryVo queryVo) {
        PageBean<SensorMessage> pageBean = deviceService.findSensorMessage(queryVo);
        Map<String, PageBean<SensorMessage>> map = new HashMap<>();
        map.put("SensorMessageList", pageBean);
        return map;
    }
}

/*
package com.wico.systemlinkweb.service.impl;

import com.wico.systemlinkweb.domain.Manufacturer;
import com.wico.systemlinkweb.mapper.ManufacturerMapper;
import com.wico.systemlinkweb.service.ManufacturerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

*/
/**
 * @author Neosgao
 * @date 15:40 2022/12/9
 **//*

@Service
public class ManufacturerServiceImpl implements ManufacturerService {
    @Resource
    private ManufacturerMapper manufacturerMapper;

    @Override
    public List<Manufacturer> list() {
        return manufacturerMapper.selectAll();
    }

    @Override
    public Map<String, Manufacturer> getManufacturerMap() {
        List<Manufacturer> manufacturerList = this.list();
        Map<String, Manufacturer> manufacturerMap = null;
        if (manufacturerList != null) {
            manufacturerMap = manufacturerList.stream().collect(Collectors.toMap(Manufacturer::getId, m -> m));
        }
        return manufacturerMap;
    }
}
*/

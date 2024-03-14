package com.wico.systemlinkweb.mapper;

import com.wico.systemlinkweb.domain.Manufacturer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Neosgao
 * @date 15:40 2022/12/9
 **/
@Mapper
public interface ManufacturerMapper {
    public List<Manufacturer> selectAll();
}

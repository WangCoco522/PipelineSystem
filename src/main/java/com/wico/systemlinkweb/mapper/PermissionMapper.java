package com.wico.systemlinkweb.mapper;

import com.wico.systemlinkweb.domain.GasPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionMapper {
    List<GasPermission> selectListByUser(int userId);

    List<GasPermission> selectListByPath(String requestUrl);
}

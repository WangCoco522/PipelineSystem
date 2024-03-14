package com.wico.systemlinkweb.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * @Author XuCheng
 * @Date 2020/5/19 10:59
 */
@Mapper
public interface ZwIpFilterMapper {
    int select(String ip);
}

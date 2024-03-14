package com.wico.systemlinkweb.mapper;

import com.wico.systemlinkweb.domain.KindDef;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author XuCheng
 * @Date 2020/4/27 10:35
 */
@Mapper
public interface KindMapper {
    List<KindDef> findKind();
}

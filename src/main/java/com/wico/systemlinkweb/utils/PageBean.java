package com.wico.systemlinkweb.utils;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author XuCheng
 * @Date 2020/4/27 10:08
 */

@Data
public class PageBean<T> {
    private int totalCount;         //总记录数
    private int currentPage;        //当前页
    private int pageSize;           //每页显示条数
    private List<T> list;           //当前页面数据
    private Map<String, Object> extraInfo; //附件数据
}

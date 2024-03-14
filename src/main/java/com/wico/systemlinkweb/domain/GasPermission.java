package com.wico.systemlinkweb.domain;

import lombok.Data;

/**
 * @Author XuCheng
 * @Date 2020-7-2 11:06
 */
@Data
public class GasPermission {
    //主键id
    private Integer id;
    //权限code
    private String permissionCode;
    //权限名
    private String permissionName;
}

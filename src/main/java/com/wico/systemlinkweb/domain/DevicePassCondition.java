package com.wico.systemlinkweb.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author XuCheng
 * @Date 2020/4/29 15:14
 */
@Data
public class DevicePassCondition {
    private int userId;
    private String user;
    private BigDecimal totalFlowUpperLimit;
    private BigDecimal totalFlowFloorLimit;
    private BigDecimal rssiUpperLimit;
    private BigDecimal rssiFloorLimit;
    private String swrlse;
    private Long ts;
}

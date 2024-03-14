package com.wico.systemlinkweb.domain.command;

import lombok.Data;

/**
 * @Author XuCheng
 * @Date 2020/5/6 15:49
 */
@Data
public class RequestCommand09 {
    private String deviceIdKeys;
    private int enableKey;
    private String startTimeKey;
    private String holdingTimeKey;
}

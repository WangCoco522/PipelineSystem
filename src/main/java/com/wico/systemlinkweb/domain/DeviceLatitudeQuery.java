package com.wico.systemlinkweb.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DeviceLatitudeQuery {
    private String minLng;
    private String maxLng;
    private String minLat;
    private String maxLat;
}

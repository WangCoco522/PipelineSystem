package com.wico.systemlinkweb.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceLatitude {
    private String deviceIdKey;
    private String coordinate;
    private String deviceType;
    private String content;
    private String communityName;
    private String baiDuLng;
    private String baiDuLat;
    private String gaoDeLng;
    private String gaoDeLat;
}

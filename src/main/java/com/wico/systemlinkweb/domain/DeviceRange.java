package com.wico.systemlinkweb.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeviceRange {
    private String fromDeviceId;
    private String toDeviceId;
}

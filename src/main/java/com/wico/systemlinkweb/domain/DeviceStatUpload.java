package com.wico.systemlinkweb.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DeviceStatUpload {
    private int notUploadTotalNum;
    private int uniqueTotalNum;
    private int repeatTotalNum;
    private LocalDate date;
}

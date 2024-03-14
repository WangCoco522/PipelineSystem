package com.wico.systemlinkweb.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public class DeviceStatUploadQuery {
    private String kind;
    private LocalDate toDate;
    private LocalDate fromDate;
    private int minusDays;

}

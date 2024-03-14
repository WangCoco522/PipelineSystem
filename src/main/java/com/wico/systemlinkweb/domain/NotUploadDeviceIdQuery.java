package com.wico.systemlinkweb.domain;


import lombok.Data;

import java.time.LocalDate;

@Data
public class NotUploadDeviceIdQuery {
    private int currentPage;
    private int pageSize;
    private int start;
    private LocalDate date;
}

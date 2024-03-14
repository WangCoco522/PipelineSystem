package com.wico.systemlinkweb.domain;

import lombok.Data;

@Data
public class GasError {
    private Long id;  //id
    private Long idAuto;
    private Long gasTs;
    private String resultMessage;
    private String resultCode;
    private Long sign;
    private String gasIn;
    private String time;
    private String resultData;
}

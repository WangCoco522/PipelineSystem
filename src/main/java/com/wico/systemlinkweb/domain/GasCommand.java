package com.wico.systemlinkweb.domain;

import lombok.Data;

@Data
public class GasCommand {
    String deviceId;
    String ts;
    String commandCode;
    String command;
    long id;
    long ts_partation;
    int status;
}

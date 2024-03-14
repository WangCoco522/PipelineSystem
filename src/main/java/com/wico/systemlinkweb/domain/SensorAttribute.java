package com.wico.systemlinkweb.domain;

import lombok.Data;

@Data
public class SensorAttribute {
    private long id;
    private String deviceIDKey;         //设备号
    private int utcTime;
    private double batteryLevel;
    private double batteryVoltage;
    private String collectTimeKey;
    private int readInterval;
    private int remainReport;
    private String randnum;
    private String controlCode;
    private double csq;
    private double gpsLon;
    private double gpsLat;
}

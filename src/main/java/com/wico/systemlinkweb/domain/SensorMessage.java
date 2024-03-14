package com.wico.systemlinkweb.domain;

import lombok.Data;

@Data
public class SensorMessage {
    private String deviceIDKey;
    private String ts;
    private double soilPh;
    private double waterContent1;
    private double temperature1;
    private double soilCondictivity1;
    private double salinity1;
    private double tds1;
    private double waterContent2;
    private double temperature2;
    private double soilCondictivity2;
    private double salinity2;
    private double tds2;
    private double waterContent3;
    private double temperature3;
    private double soilCondictivity3;
    private double salinity3;
    private double tds3;
    private double methanePotency;
    private double systemTemperature;
    private double systemHumidity;
    private long id;
    private String tsPartation;
    private double soilCh4;
    private double soilCh4humi;
    private double soilCh4temp;
    private double soilWz3temp;
    private double soilWz3xspeed;
    private double soilWz3yspeed;
    private double soilWz3zspeed;
    private double soilWz3xdistance;
    private double soilWz3ydistance;
    private double soilWz3zdistance;
    private double soilWz3xacc;
    private double soilWz3yacc;
    private double soilWz3zacc;

}

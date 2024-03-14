package com.wico.systemlinkweb.schedule;

import com.alibaba.fastjson.JSONObject;
import com.wico.systemlinkweb.domain.DeviceLatitude;
import com.wico.systemlinkweb.domain.DeviceLocation;
import com.wico.systemlinkweb.mapper.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.util.List;

//@Configuration
//@EnableScheduling
public class LocationTransferSchedule {
    @Autowired
    DeviceMapper deviceMapper;

    private static final String GOULD_KEY = "75b56a7692513bda04c55afbf00b6e5f";


    @Scheduled(cron = "0 0 23 * * ?")
    void transfer() {
        RestTemplate restTemplate = new RestTemplate();
        List<DeviceLocation> list = deviceMapper.findLocation();

        for (DeviceLocation deviceLocation : list) {
            String queryUrl = "http://restapi.amap.com/v3/geocode/geo?key=" + GOULD_KEY + "&address=" + deviceLocation.getCommunityName() + "&city=上海";
            System.out.println(queryUrl);
            JSONObject resultClient = restTemplate.getForObject(queryUrl, JSONObject.class, new Object[0]);
            deviceMapper.updateTransfered(deviceLocation);

            DeviceLatitude deviceLatitude = new DeviceLatitude();
            deviceLatitude.setDeviceIdKey(deviceLocation.getDeviceIdKey());
            try {
                deviceLatitude.setCoordinate(resultClient.getJSONArray("geocodes").getJSONObject(0).getString("location"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            deviceMapper.insertLatitude(deviceLatitude);
        }
    }
}

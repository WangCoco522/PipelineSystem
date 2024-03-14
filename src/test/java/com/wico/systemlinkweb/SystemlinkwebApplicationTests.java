package com.wico.systemlinkweb;

import com.alibaba.fastjson.JSONObject;
import com.wico.systemlinkweb.component.JwtComponent;
import com.wico.systemlinkweb.domain.*;
import com.wico.systemlinkweb.mapper.DeviceMapper;

import com.wico.systemlinkweb.property.GasAdminProperties;
import com.wico.systemlinkweb.utils.MapBuilder;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
class SystemlinkwebApplicationTests {
    @Autowired
    DeviceMapper deviceMapper;

    private static final String GOULD_KEY_BAIDU= "tOvuAeGtiIZCYxe7oahsEIXbmcQGXu22";
    private static final String GOULD_KEY_GAODE= "75b56a7692513bda04c55afbf00b6e5f";

//    @Test
    void contextLoads()  {
        RestTemplate restTemplate = new RestTemplate();
        List<DeviceLocation> list = deviceMapper.findLocation();
        for(DeviceLocation deviceLocation : list) {
            String queryUrlBaiDu = "http://api.map.baidu.com/geocoding/v3/?output=json&ak=" + GOULD_KEY_BAIDU + "&address="+deviceLocation.getDeviceLocation()+deviceLocation.getCommunityName()+"&city=上海";
            System.out.println(queryUrlBaiDu);
            String resultClientBaiDu = restTemplate.getForObject(queryUrlBaiDu, String.class);
            //System.out.println(resultClientBaiDu);
            JSONObject jsonObject;
            jsonObject = JSONObject.parseObject(resultClientBaiDu);
            DeviceLatitude deviceLatitude = new DeviceLatitude();
            deviceLatitude.setDeviceIdKey(deviceLocation.getDeviceIdKey());
            deviceLatitude.setBaiDuLat(jsonObject.getJSONObject("result").getJSONObject("location").getString("lat"));
                deviceLatitude.setBaiDuLng(jsonObject.getJSONObject("result").getJSONObject("location").getString("lng"));

            String queryUrlGaoDe = "http://restapi.amap.com/v3/geocode/geo?key="+ GOULD_KEY_GAODE +"&address="+ deviceLocation.getDeviceLocation()+deviceLocation.getCommunityName() +"&city=上海";
            JSONObject resultClientGaoDe = restTemplate.getForObject(queryUrlGaoDe, JSONObject.class);
            try {
                assert resultClientGaoDe != null;
                String[] locationStrArray = resultClientGaoDe.getJSONArray("geocodes").getJSONObject(0).getString("location").split(",");
                deviceLatitude.setGaoDeLng(locationStrArray[0]);
                deviceLatitude.setGaoDeLat(locationStrArray[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            deviceMapper.insertLatitude(deviceLatitude);
            deviceMapper.updateTransfered(deviceLocation);
        }
    }
}
//            deviceMapper.updateTransfered(deviceLocation);

//            DeviceLatitude deviceLatitude = new DeviceLatitude();
//            deviceLatitude.setDeviceIdKey(deviceLocation.getDeviceIdKey());

//            deviceMapper.insertLatitude(deviceLatitude);
//        }



//        String queryUrl = "http://restapi.amap.com/v3/geocode/geo?key="+GOULD_KEY+"&address=" + "德平路1189弄90支弄17号401室";
//        JSONObject resultclient = restTemplate.getForObject(queryUrl, JSONObject.class, new Object[0]);
//        System.out.println( resultclient.getJSONArray("geocodes").getJSONObject(0).getString("location"));

        //System.out.println(resultclient);
//        ExcelImport04 excelImport = new ExcelImport04();
//        List<Attribute3> attributes = excelImport.importExcel();
//        List<Attribute3> updateFailure = new ArrayList<>();
//        for (Attribute3 attribute : attributes) {
//            if (deviceMapper.updatePartAttribute3(attribute) == 0)
//                updateFailure.add(attribute);
//        }
//        for (Attribute3 attribute : updateFailure) {
//            deviceMapper.insertPartAttribute3(attribute);
//        }
//        updateFailure.stream().forEach(System.out::println);
//
//    }


//            try {
//                System.out.println(deviceMapper.updatePartAttribute(attribute));
//                // attributeDao.updateAttribute(attribute);
//                System.out.println(attribute.getDeviceIdKey()+":更新成功");
//            }catch (Exception e){
//               updateFailure.add(attribute);
//                System.out.println(e);
//            }



package com.wico.systemlinkweb.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


/**
 * @author XuCheng
 */
public class ThingsBoardJWTTokenTelemetryValue {
    private static String getCommandDays = "5";
    public static String JWT(String thingsboardUrl,String thingsboardUsername,String thingsboardPassword)throws Exception{
        //拿到TB JWT令牌
        URL authUrl = new URL(thingsboardUrl + "/api/auth/login");
        System.out.println(authUrl);
        HttpURLConnection authConnection = (HttpURLConnection) authUrl.openConnection();
        authConnection.setDoInput(true);
        authConnection.setDoOutput(true);
        authConnection.setRequestMethod("GET");
        authConnection.setRequestProperty("Content-Type", "application/json");
        authConnection.setRequestProperty("Accept", "application/json");
        System.out.println(authConnection);
        PrintWriter writer = new PrintWriter(new BufferedOutputStream(authConnection.getOutputStream()));
        JSONObject auth = new JSONObject();
        auth.put("username", thingsboardUsername);
        auth.put("password", thingsboardPassword);
        writer.write(auth.toJSONString());
        writer.flush();
        writer.close();
        BufferedReader authReader = new BufferedReader(new InputStreamReader(authConnection.getInputStream(),StandardCharsets.UTF_8));
        String authResponse;
        StringBuilder stringBuilder1 = new StringBuilder();
        while ((authResponse = authReader.readLine()) != null) {
            stringBuilder1.append(authResponse).append("\n");
        }
        authReader.close();
        authConnection.disconnect();
        JSONObject tokenJson = (JSONObject) JSONObject.parse(stringBuilder1.toString());
        String jwtToken = tokenJson.getString("token");
        return  jwtToken;
    }

    //使用JWT令牌从TB中拿下发命令遥测数据
     public static JSONArray getTelemetryValue(String thingsboardUrl, String thingsboardDeviceId, String thingsboardUsername, String thingsboardPassword, String deviceId)throws Exception{
         String jwtToken = ThingsBoardJWTTokenTelemetryValue.JWT(thingsboardUrl,thingsboardUsername,thingsboardPassword);
         URL url = new URL(thingsboardUrl + "/api/plugins/telemetry/DEVICE/" + thingsboardDeviceId + "/values/timeseries?keys=" + deviceId +  "&startTs=" + Long.toString(new Date().getTime()- Long.parseLong(getCommandDays) * 24 * 60 * 60 * 1000)  +"&endTs="+ Long.toString(new Date().getTime()));
         HttpURLConnection connection = (HttpURLConnection) url.openConnection();
         connection.setDoOutput(true);
         connection.setDoInput(true);
         connection.setRequestMethod("GET");
         connection.setRequestProperty("Content-Type", "application/json");
         connection.setRequestProperty("X-Authorization", "value: " + jwtToken);
         System.out.println(connection);
         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
         String response;
         StringBuilder stringBuilder2 = new StringBuilder();
         while ((response = bufferedReader.readLine()) != null) {
             stringBuilder2.append(response).append("\n");
         }
         bufferedReader.close();
         connection.disconnect();
         JSONObject object = (JSONObject) JSONObject.parse(stringBuilder2.toString());
         JSONArray object1 = (JSONArray) JSONObject.parse(object.getString(deviceId));

         JSONArray js  = new JSONArray();
         js = null;
         JSONArray jsonArray1  = new JSONArray();
         if (object1 != null){
             for (int i = 0; i < object1.size(); i++) {
                 String commandJson = object1.getJSONObject(i).getString("value");
                 String ts = object1.getJSONObject(i).getString("ts");
                 JSONObject jsonObject1 = JSON.parseObject(commandJson);
                 jsonObject1.put("ts",ts);
                 jsonArray1.add(jsonObject1);
             }
            //过滤执行成功的命令
             for (int i = jsonArray1.size()-1; i >= 0; i--) {
                 if ("Success".equals(jsonArray1.getJSONObject(i).getString("status")) || "Failed".equals(jsonArray1.getJSONObject(i).getString("status"))){
                     jsonArray1.remove(i);
                 }
             }
             js = jsonArray1;
             for (int i = js.size()-1;i >= 0; i--){
                 js.getJSONObject(i).put("status","Failed");
                 js.getJSONObject(i).put("killed","manual");
             }
         }
         return js;
     }
   //使用JWT令牌从TB中拿下发最新命令遥测数据
    public static List getLasteTelemetryValue(String thingsboardUrl, String thingsboardDeviceId, String thingsboardUsername, String thingsboardPassword, String deviceId)throws Exception{
        String jwtToken = ThingsBoardJWTTokenTelemetryValue.JWT(thingsboardUrl,thingsboardUsername,thingsboardPassword);

        URL url = new URL(thingsboardUrl + "/api/plugins/telemetry/DEVICE/" + thingsboardDeviceId + "/values/timeseries?keys=" + deviceId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("X-Authorization", "value: " + jwtToken);
        System.out.println(connection);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        String response;
        StringBuilder stringBuilder2 = new StringBuilder();
        while ((response = bufferedReader.readLine()) != null) {
            stringBuilder2.append(response).append("\n");
        }
        bufferedReader.close();
        connection.disconnect();
        JSONObject object = (JSONObject) JSONObject.parse(stringBuilder2.toString());
        JSONArray object1 = (JSONArray) JSONObject.parse(object.getString(deviceId));
        JSONObject object2 = object1.getJSONObject(0);
        String commandJson = object2.getString("value");
        String timestamp = object2.getString("ts");

        List<String> list = new LinkedList<String>();
        if (commandJson != null){
            list.add(commandJson);
            list.add(timestamp);
        }

        return list;

    }
}

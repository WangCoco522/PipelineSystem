/*
package com.wico.systemlinkweb.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wico.systemlinkweb.domain.GasCommand;
import com.wico.systemlinkweb.domain.command.*;
import com.wico.systemlinkweb.exception.GlobleException;
import com.wico.systemlinkweb.mapper.CommandMapper;
import com.wico.systemlinkweb.result.CodeMsg;
import com.wico.systemlinkweb.service.ICommandService;
import com.wico.systemlinkweb.utils.HttpClientUtils;
import com.wico.systemlinkweb.utils.ThingsBoardJWTTokenTelemetryValue;
import com.wico.systemlinkweb.utils.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

*/
/**
 * @Author XuCheng
 * @Date 2020/4/29 15:36
 *//*

@Service
public class CommandServiceImpl implements ICommandService {

    @Value("${command.thingsBoardUrl}")
    private String thingsBoardUrl;
    @Value("${command.thingsBoardUsername}")
    private String thingsBoardUsername;
    @Value("${command.thingsBoardPassword}")
    private String thingsBoardPassword;
    @Value("${command.thingsBoardDeviceId}")
    private String thingsBoardDeviceId;
    @Value("${command.url}")
    private String url;
    @Value("${command.urlHold}")
    private String urlHold;
    @Value("${command.thingsboardDeviceEndDownCommandId}")
    private String endDownCommandId;
//    @Value("${command.endDownUrl}")
//    private String endDownUrl;

    @Autowired
    private CommandMapper commandMapper;

    private static final Logger logger = LoggerFactory.getLogger(Command.class);

    // 修改网络参数命令
    @Override
    public String postCommand01(RequestCommand01 requestCommand01) {
        String[] strings=requestCommand01.getDeviceIdKeys().trim().split(",");
        Command01 command01=new Command01();
        command01.setoPTypeKey("01");
        command01.setiPKey(requestCommand01.getiPKey());
        command01.setPortKey(requestCommand01.getPortKey());
        StringBuilder stringBuilder=new StringBuilder();

        GasCommand gasCommand = new GasCommand();
        gasCommand.setStatus(0);
        long time = System.currentTimeMillis();
        gasCommand.setTs_partation(time);
        String stime = String.valueOf(time);
        gasCommand.setTs(stime);
        gasCommand.setCommandCode("81");

        for(int i=0;i<strings.length;i++){
            command01.setDeviceIdKey(strings[i]);
            gasCommand.setDeviceId(strings[i]);
            // 根据设备id获取设备属性信息

            Command command1 = commandMapper.getAttribute(strings[i]);
            if(command1.getRandNumKey()!=null && !command1.getRandNumKey().equals("")){
                if(command1.getIsEncry()==null || command1.getIsEncry()==0) {
                    command01.setSecurityKey(false);
                    command01.setPuidKey("");
                }else{
                    command01.setSecurityKey(true);
                    command01.setPuidKey(command1.getPuidKey());
                }
                command01.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从gas_attribute表中取出的");
            }else{
                command1=commandMapper.getCommandAttribute(strings[i]);
                if(command1.getPuidKey()==null || ("").equals(command1.getPuidKey())) {
                    command01.setSecurityKey(false);
                    command01.setPuidKey("");
                }else{
                    command01.setSecurityKey(true);
                    command01.setPuidKey(command1.getPuidKey());
                }
                command01.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从attribute_tv表中取出的");
            }
            command01.setTaskIDKey(UUIDUtils.getUUID());
            JSONObject request=new JSONObject();
            request.put(command01.getDeviceIdKey(),JSONObject.toJSONString(command01));
            gasCommand.setCommand(JSONObject.toJSONString(command01));
            JSONObject response=null;
            try {
                response= HttpClientUtils.doPost(url,request);     // 下发指令并获取响应
                commandMapper.insertGasCommand(gasCommand);
            } catch (Exception e) {
                throw new GlobleException(CodeMsg.COMMAND_ERROR);  // 指令下发失败
            }
            stringBuilder.append(strings[i]).append("发送成功!").append(" ");
        }
        return stringBuilder.toString();
    }

    // 下载更新软件版本
    @Override
    public String postCommand02(RequestCommand02 requestCommand02) {
        String[] strings = requestCommand02.getDeviceIdKeys().trim().split(",");
        Command02 command02 = new Command02();
        command02.setURLKey(requestCommand02.getuRLKey());
        command02.setOPTypeKey("02");
        StringBuilder stringBuilder=new StringBuilder();

        GasCommand gasCommand = new GasCommand();
        gasCommand.setStatus(0);
        long time = System.currentTimeMillis();
        gasCommand.setTs_partation(time);
        String stime = String.valueOf(time);
        gasCommand.setTs(stime);
        gasCommand.setCommandCode("83");

        for(int i=0;i<strings.length;i++){
            command02.setDeviceIdKey(strings[i]);
            gasCommand.setDeviceId(strings[i]);
            // 根据设备id获取设备属性信息
            Command command1 = commandMapper.getAttribute(strings[i]);
            if(command1.getRandNumKey()!=null && !command1.getRandNumKey().equals("")){
                if(command1.getIsEncry()==null || command1.getIsEncry()==0) {
                    command02.setSecurityKey(false);
                    command02.setPuidKey("");
                }else{
                    command02.setSecurityKey(true);
                    command02.setPuidKey(command1.getPuidKey());
                }
                command02.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从gas_attribute表中取出的");
            }else{
                command1=commandMapper.getCommandAttribute(strings[i]);
                if(command1.getPuidKey()==null || ("").equals(command1.getPuidKey())) {
                    command02.setSecurityKey(false);
                    command02.setPuidKey("");
                }else{
                    command02.setSecurityKey(true);
                    command02.setPuidKey(command1.getPuidKey());
                }
                command02.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从attribute_tv表中取出的");
            }

            command02.setTaskIDKey(UUIDUtils.getUUID());
            JSONObject request=new JSONObject();
            request.put(command02.getDeviceIdKey(),JSONObject.toJSONString(command02));
            gasCommand.setCommand(JSONObject.toJSONString(command02));
            JSONObject response=null;
            try {
                response=HttpClientUtils.doPost(url,request);
                commandMapper.insertGasCommand(gasCommand);
            } catch (Exception e) {
                throw new GlobleException(CodeMsg.COMMAND_ERROR);
            }
            stringBuilder.append(strings[i]).append("发送成功!").append(" ");
        }
        return stringBuilder.toString();
    }

    // 远程关阀
    @Override
    public String postCommand03(String deviceIdKeys) {
        String[] strings=deviceIdKeys.trim().split(",");
        Command command=new Command();
        command.setOPTypeKey("03");
        StringBuilder stringBuilder=new StringBuilder();

        GasCommand gasCommand = new GasCommand();
        gasCommand.setStatus(0);
        long time = System.currentTimeMillis();
        gasCommand.setTs_partation(time);
        String stime = String.valueOf(time);
        gasCommand.setTs(stime);
        gasCommand.setCommandCode("87");

        for(int i=0;i<strings.length;i++){
            command.setDeviceIdKey(strings[i]);
            gasCommand.setDeviceId(strings[i]);

            Command command1 = commandMapper.getAttribute(strings[i]);
            if(command1.getRandNumKey()!=null && !command1.getRandNumKey().equals("")){
                if(command1.getIsEncry()==null || command1.getIsEncry()==0) {
                    command.setSecurityKey(false);
                    command.setPuidKey("");
                }else{
                    command.setSecurityKey(true);
                    command.setPuidKey(command1.getPuidKey());
                }
                command.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从gas_attribute表中取出的");
            }else{
                command1=commandMapper.getCommandAttribute(strings[i]);
                if(command1.getPuidKey()==null || ("").equals(command1.getPuidKey())) {
                    command.setSecurityKey(false);
                    command.setPuidKey("");
                }else{
                    command.setSecurityKey(true);
                    command.setPuidKey(command1.getPuidKey());
                }
                command.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从attribute_tv表中取出的");
            }

            command.setTaskIDKey(UUIDUtils.getUUID());
            JSONObject request=new JSONObject();
            request.put(command.getDeviceIdKey(),JSONObject.toJSONString(command));
            gasCommand.setCommand(JSONObject.toJSONString(command));
            JSONObject response=null;
            try {
                response=HttpClientUtils.doPost(url,request);
                commandMapper.insertGasCommand(gasCommand);
            }  catch (Exception e) {
                throw new GlobleException(CodeMsg.COMMAND_ERROR);
            }
            stringBuilder.append(strings[i]).append("发送成功!").append(" ");
        }
        return stringBuilder.toString();
    }

    // 远程开阀
    @Override
    public String postCommand04(String deviceIdKeys) {
        String[] strings=deviceIdKeys.trim().split(",");
        Command command=new Command();
        command.setOPTypeKey("04");
        StringBuilder stringBuilder=new StringBuilder();

        GasCommand gasCommand = new GasCommand();
        gasCommand.setStatus(0);
        long time = System.currentTimeMillis();
        gasCommand.setTs_partation(time);
        String stime = String.valueOf(time);
        gasCommand.setTs(stime);
        gasCommand.setCommandCode("86");

        for(int i=0;i<strings.length;i++){
            command.setDeviceIdKey(strings[i]);
            gasCommand.setDeviceId(strings[i]);

            Command command1 = commandMapper.getAttribute(strings[i]);
            if(command1.getRandNumKey()!=null && !command1.getRandNumKey().equals("")){
                if(command1.getIsEncry()==null || command1.getIsEncry()==0) {
                    command.setSecurityKey(false);
                    command.setPuidKey("");
                }else{
                    command.setSecurityKey(true);
                    command.setPuidKey(command1.getPuidKey());
                }
                command.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从gas_attribute表中取出的");
            }else{
                command1=commandMapper.getCommandAttribute(strings[i]);
                if(command1.getPuidKey()==null || ("").equals(command1.getPuidKey())) {
                    command.setSecurityKey(false);
                    command.setPuidKey("");
                }else{
                    command.setSecurityKey(true);
                    command.setPuidKey(command1.getPuidKey());
                }
                command.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从attribute_tv表中取出的");
            }
            command.setTaskIDKey(UUIDUtils.getUUID());
            JSONObject request=new JSONObject();
            request.put(command.getDeviceIdKey(),JSONObject.toJSONString(command));
            gasCommand.setCommand(JSONObject.toJSONString(command));
            JSONObject response=null;
            try {
                response=HttpClientUtils.doPost(url,request);
                commandMapper.insertGasCommand(gasCommand);
            } catch (Exception e) {
                throw new GlobleException(CodeMsg.COMMAND_ERROR);
            }
            stringBuilder.append(strings[i]).append("发送成功!").append(" ");
        }
        return stringBuilder.toString();
    }

    // 恢复出厂设置
    @Override
    public String postCommand05(String deviceIdKeys) {
        String[] strings=deviceIdKeys.trim().split(",");
        Command command=new Command();
        command.setOPTypeKey("05");
        StringBuilder stringBuilder=new StringBuilder();

        GasCommand gasCommand = new GasCommand();
        gasCommand.setStatus(0);
        long time = System.currentTimeMillis();
        gasCommand.setTs_partation(time);
        String stime = String.valueOf(time);
        gasCommand.setTs(stime);
        gasCommand.setCommandCode("8E");

        for(int i=0;i<strings.length;i++){
            command.setDeviceIdKey(strings[i]);
            gasCommand.setDeviceId(strings[i]);

            Command command1 = commandMapper.getAttribute(strings[i]);
            if(command1.getRandNumKey()!=null && !command1.getRandNumKey().equals("")){
                if(command1.getIsEncry()==null || command1.getIsEncry()==0) {
                    command.setSecurityKey(false);
                    command.setPuidKey("");
                }else{
                    command.setSecurityKey(true);
                    command.setPuidKey(command1.getPuidKey());
                }
                command.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从gas_attribute表中取出的");
            }else{
                command1=commandMapper.getCommandAttribute(strings[i]);
                if(command1.getPuidKey()==null || ("").equals(command1.getPuidKey())) {
                    command.setSecurityKey(false);
                    command.setPuidKey("");
                }else{
                    command.setSecurityKey(true);
                    command.setPuidKey(command1.getPuidKey());
                }
                command.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从attribute_tv表中取出的");
            }

            command.setTaskIDKey(UUIDUtils.getUUID());
            JSONObject request=new JSONObject();
            request.put(command.getDeviceIdKey(),JSONObject.toJSONString(command));
            gasCommand.setCommand(JSONObject.toJSONString(command));
            JSONObject response=null;
            try {
                response=HttpClientUtils.doPost(url,request);
                commandMapper.insertGasCommand(gasCommand);
            }  catch (Exception e) {
                throw new GlobleException(CodeMsg.COMMAND_ERROR);
            }
            stringBuilder.append(strings[i]).append("发送成功!").append(" ");
        }
        return stringBuilder.toString();
    }

    // 读取事件记录
    @Override
    public String postCommand06(String deviceIdKeys) {
        String[] strings=deviceIdKeys.trim().split(",");
        Command command=new Command();
        command.setOPTypeKey("06");
        StringBuilder stringBuilder=new StringBuilder();

        GasCommand gasCommand = new GasCommand();
        gasCommand.setStatus(0);
        long time = System.currentTimeMillis();
        gasCommand.setTs_partation(time);
        String stime = String.valueOf(time);
        gasCommand.setTs(stime);
        gasCommand.setCommandCode("85");

        for(int i=0;i<strings.length;i++){
            command.setDeviceIdKey(strings[i]);
            gasCommand.setDeviceId(strings[i]);

            Command command1 = commandMapper.getAttribute(strings[i]);
            if(command1.getRandNumKey()!=null && !command1.getRandNumKey().equals("")){
                if(command1.getIsEncry()==null || command1.getIsEncry()==0) {
                    command.setSecurityKey(false);
                    command.setPuidKey("");
                }else{
                    command.setSecurityKey(true);
                    command.setPuidKey(command1.getPuidKey());
                }
                command.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从gas_attribute表中取出的");
            }else{
                command1=commandMapper.getCommandAttribute(strings[i]);
                if(command1.getPuidKey()==null || ("").equals(command1.getPuidKey())) {
                    command.setSecurityKey(false);
                    command.setPuidKey("");
                }else{
                    command.setSecurityKey(true);
                    command.setPuidKey(command1.getPuidKey());
                }
                command.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从attribute_tv表中取出的");
            }

            command.setTaskIDKey(UUIDUtils.getUUID());
            JSONObject request=new JSONObject();
            request.put(command.getDeviceIdKey(),JSONObject.toJSONString(command));
            gasCommand.setCommand(JSONObject.toJSONString(command));
            JSONObject response=null;
            try {
                response=HttpClientUtils.doPost(url,request);
                commandMapper.insertGasCommand(gasCommand);
            }  catch (Exception e) {
                throw new GlobleException(CodeMsg.COMMAND_ERROR);
            }
            stringBuilder.append(strings[i]).append("发送成功!").append(" ");
        }
        return stringBuilder.toString();
    }

    // 异常告警应答
    @Override
    public String postCommand07(String deviceIdKeys) {
        String[] strings=deviceIdKeys.trim().split(",");
        Command command=new Command();
        command.setOPTypeKey("07");
        StringBuilder stringBuilder=new StringBuilder();

        GasCommand gasCommand = new GasCommand();
        gasCommand.setStatus(0);
        long time = System.currentTimeMillis();
        gasCommand.setTs_partation(time);
        String stime = String.valueOf(time);
        gasCommand.setTs(stime);
        gasCommand.setCommandCode("84");

        for(int i=0;i<strings.length;i++){
            command.setDeviceIdKey(strings[i]);
            gasCommand.setDeviceId(strings[i]);

            Command command1 = commandMapper.getAttribute(strings[i]);
            if(command1.getRandNumKey()!=null && !command1.getRandNumKey().equals("")){
                if(command1.getIsEncry()==null || command1.getIsEncry()==0) {
                    command.setSecurityKey(false);
                    command.setPuidKey("");
                }else{
                    command.setSecurityKey(true);
                    command.setPuidKey(command1.getPuidKey());
                }
                command.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从gas_attribute表中取出的");
            }else{
                command1=commandMapper.getCommandAttribute(strings[i]);
                if(command1.getPuidKey()==null || ("").equals(command1.getPuidKey())) {
                    command.setSecurityKey(false);
                    command.setPuidKey("");
                }else{
                    command.setSecurityKey(true);
                    command.setPuidKey(command1.getPuidKey());
                }
                command.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从attribute_tv表中取出的");
            }
            command.setTaskIDKey(UUIDUtils.getUUID());
            JSONObject request=new JSONObject();
            request.put(command.getDeviceIdKey(),JSONObject.toJSONString(command));
            gasCommand.setCommand(JSONObject.toJSONString(command));
            JSONObject response=null;
            try {
                response=HttpClientUtils.doPost(url,request);
                commandMapper.insertGasCommand(gasCommand);
            }  catch (Exception e) {
                throw new GlobleException(CodeMsg.COMMAND_ERROR);
            }
            stringBuilder.append(strings[i]).append("发送成功!").append(" ");
        }
        return stringBuilder.toString();
    }

    // 结束报文
    @Override
    public String postCommand08(RequestCommand08 requestCommand08) {
        String[] strings = requestCommand08.getDeviceIdKeys().trim().split(",");
        Command08 command08=new Command08();
        command08.setOPTypeKey("10");
        command08.setReadIntervalKey(requestCommand08.getReadIntervalKey());
        if(requestCommand08.getSecurityKey()==null) {
            command08.setSecurityKey(false);
        } else {
            command08.setSecurityKey(requestCommand08.getSecurityKey());
        }
        if (requestCommand08.getBusinessPeriodKey() == null || requestCommand08.getBusinessPeriodKey().equals("false")){
            command08.setBusinessPeriodKey("0");
        }else{
            command08.setBusinessPeriodKey("1");
        }
        StringBuilder stringBuilder=new StringBuilder();

        GasCommand gasCommand = new GasCommand();
        gasCommand.setStatus(0);
        long time = System.currentTimeMillis();
        gasCommand.setTs_partation(time);
        String stime = String.valueOf(time);
        gasCommand.setTs(stime);

        for(int i=0;i<strings.length;i++){
            command08.setDeviceIdKey(strings[i]);
            gasCommand.setDeviceId(strings[i]);

            Command command1 = commandMapper.getAttribute(strings[i]);
            if(command1.getRandNumKey()!=null && !command1.getRandNumKey().equals("")){
                if(command1.getIsEncry()==null || command1.getIsEncry()==0) {
                    command08.setPuidKey("");
                }else{
                    command08.setPuidKey(command1.getPuidKey());
                }
                if(strings[i].startsWith("00")){
                    gasCommand.setCommandCode("0F");
                }else{
                    gasCommand.setCommandCode("5F");
                }
                command08.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从gas_attribute表中取出的");
            }else{
                command1=commandMapper.getCommandAttribute(strings[i]);
                if(command1.getPuidKey()==null || ("").equals(command1.getPuidKey())) {
                    command08.setPuidKey("");
                }else{
                    command08.setPuidKey(command1.getPuidKey());
                }
                if(strings[i].startsWith("00")){
                    gasCommand.setCommandCode("0F");
                }else{
                    gasCommand.setCommandCode("5F");
                }
                command08.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从attribute_tv表中取出的");
            }
            command08.setTaskIDKey(UUIDUtils.getUUID());
            command08.setUPTimeKey(requestCommand08.getUpTimeKey());
            command08.setUPPeriodKey(requestCommand08.getUpPeriodKey());
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyMMddHHmmss");
            command08.setDateTimeKey(simpleDateFormat.format(new Date()));
            JSONObject request=new JSONObject();
            request.put(command08.getDeviceIdKey(),JSONObject.toJSONString(command08));
            gasCommand.setCommand(JSONObject.toJSONString(command08));
            JSONObject response=null;
            try {
                //response=HttpClientUtils.doPost(url,request);
                response=HttpClientUtils.doPost(urlHold,request);
                commandMapper.insertEndPacket(gasCommand);
            } catch (Exception e) {
                throw new GlobleException(CodeMsg.COMMAND_ERROR);
            }
            stringBuilder.append(strings[i]).append("发送成功!").append(" ");
        }
        return stringBuilder.toString();
    }

    // 远程关阀使能
    @Override
    public String postCommand09(RequestCommand09 requestCommand09) {
        String[] strings=requestCommand09.getDeviceIdKeys().trim().split(",");
        Command09 command09=new Command09();
        command09.setOPTypeKey("11");
        command09.setEnableKey(requestCommand09.getEnableKey());
        command09.setStartTimeKey(requestCommand09.getStartTimeKey());
        command09.setHoldingTimeKey(requestCommand09.getHoldingTimeKey());
        StringBuilder stringBuilder=new StringBuilder();

        GasCommand gasCommand = new GasCommand();
        gasCommand.setStatus(0);
        long time = System.currentTimeMillis();
        gasCommand.setTs_partation(time);
        String stime = String.valueOf(time);
        gasCommand.setTs(stime);
        gasCommand.setCommandCode("88");

        for(int i=0;i<strings.length;i++){
            command09.setDeviceIdKey(strings[i]);
            gasCommand.setDeviceId(strings[i]);

            Command command1 = commandMapper.getAttribute(strings[i]);
            if(command1.getRandNumKey()!=null && !command1.getRandNumKey().equals("")){
                if(command1.getIsEncry()==null || command1.getIsEncry()==0) {
                    command09.setSecurityKey(false);
                    command09.setPuidKey("");
                }else{
                    command09.setSecurityKey(true);
                    command09.setPuidKey(command1.getPuidKey());
                }
                command09.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从gas_attribute表中取出的");
            }else{
                command1=commandMapper.getCommandAttribute(strings[i]);
                if(command1.getPuidKey()==null || ("").equals(command1.getPuidKey())) {
                    command09.setSecurityKey(false);
                    command09.setPuidKey("");
                }else{
                    command09.setSecurityKey(true);
                    command09.setPuidKey(command1.getPuidKey());
                }
                command09.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从attribute_tv表中取出的");
            }
            command09.setTaskIDKey(UUIDUtils.getUUID());
            JSONObject request=new JSONObject();
            request.put(command09.getDeviceIdKey(),JSONObject.toJSONString(command09));
            gasCommand.setCommand(JSONObject.toJSONString(command09));
            JSONObject response=null;
            try {
                response=HttpClientUtils.doPost(url,request);
                commandMapper.insertGasCommand(gasCommand);
            } catch (Exception e) {
                throw new GlobleException(CodeMsg.COMMAND_ERROR);
            }
            stringBuilder.append(strings[i]).append("发送成功!").append(" ");
        }
        return stringBuilder.toString();
    }

    // 设置安全流量参数
    @Override
    public String postCommand10(RequestCommand10 requestCommand10) {
        String[] strings=requestCommand10.getDeviceIdKeys().trim().split(",");
        Command10 command10=new Command10(requestCommand10.getAlerm1VHKey(),requestCommand10.getAlerm1VLKey(),requestCommand10.getAlerm1TimeKey()
                ,requestCommand10.getAlerm2VHKey(),requestCommand10.getAlerm2VLKey(),requestCommand10.getAlerm2TimeKey(),
                requestCommand10.getAlerm3VHKey(),requestCommand10.getAlerm3VLKey(),requestCommand10.getAlerm3TimeKey());
        command10.setOPTypeKey("12");
        StringBuilder stringBuilder=new StringBuilder();

        GasCommand gasCommand = new GasCommand();
        gasCommand.setStatus(0);
        long time = System.currentTimeMillis();
        gasCommand.setTs_partation(time);
        String stime = String.valueOf(time);
        gasCommand.setTs(stime);
        gasCommand.setCommandCode("A1");

        for(int i=0;i<strings.length;i++){
            command10.setDeviceIdKey(strings[i]);
            gasCommand.setDeviceId(strings[i]);

            Command command1 = commandMapper.getAttribute(strings[i]);
            if(command1.getRandNumKey()!=null && !command1.getRandNumKey().equals("")){
                if(command1.getIsEncry()==null || command1.getIsEncry()==0) {
                    command10.setSecurityKey(false);
                    command10.setPuidKey("");
                }else{
                    command10.setSecurityKey(true);
                    command10.setPuidKey(command1.getPuidKey());
                }
                command10.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从gas_attribute表中取出的");
            }else{
                command1=commandMapper.getCommandAttribute(strings[i]);
                if(command1.getPuidKey()==null || ("").equals(command1.getPuidKey())) {
                    command10.setSecurityKey(false);
                    command10.setPuidKey("");
                }else{
                    command10.setSecurityKey(true);
                    command10.setPuidKey(command1.getPuidKey());
                }
                command10.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从attribute_tv表中取出的");
            }
            command10.setTaskIDKey(UUIDUtils.getUUID());
            JSONObject request=new JSONObject();
            request.put(command10.getDeviceIdKey(),JSONObject.toJSONString(command10));
            gasCommand.setCommand(JSONObject.toJSONString(command10));
            JSONObject response=null;
            try {
                response=HttpClientUtils.doPost(url,request);
                commandMapper.insertGasCommand(gasCommand);
            } catch (Exception e) {
                throw new GlobleException(CodeMsg.COMMAND_ERROR);
            }
            stringBuilder.append(strings[i]).append("发送成功!").append(" ");
        }
        return stringBuilder.toString();
    }

    // 读取安全流量参数
    @Override
    public String postCommand11(String deviceIdKeys) {
        String[] strings=deviceIdKeys.trim().split(",");
        Command command=new Command();
        command.setOPTypeKey("13");
        StringBuilder stringBuilder=new StringBuilder();

        GasCommand gasCommand = new GasCommand();
        gasCommand.setStatus(0);
        long time = System.currentTimeMillis();
        gasCommand.setTs_partation(time);
        String stime = String.valueOf(time);
        gasCommand.setTs(stime);
        gasCommand.setCommandCode("A2");

        for(int i=0;i<strings.length;i++){
            command.setDeviceIdKey(strings[i]);
            gasCommand.setDeviceId(strings[i]);

            Command command1 = commandMapper.getAttribute(strings[i]);
            if(command1.getRandNumKey()!=null && !command1.getRandNumKey().equals("")){
                if(command1.getIsEncry()==null || command1.getIsEncry()==0) {
                    command.setSecurityKey(false);
                    command.setPuidKey("");
                }else{
                    command.setSecurityKey(true);
                    command.setPuidKey(command1.getPuidKey());
                }
                command.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从gas_attribute表中取出的");
            }else{
                command1=commandMapper.getCommandAttribute(strings[i]);
                if(command1.getPuidKey()==null || ("").equals(command1.getPuidKey())) {
                    command.setSecurityKey(false);
                    command.setPuidKey("");
                }else{
                    command.setSecurityKey(true);
                    command.setPuidKey(command1.getPuidKey());
                }
                command.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从attribute_tv表中取出的");
            }
            command.setTaskIDKey(UUIDUtils.getUUID());
            JSONObject request=new JSONObject();
            request.put(command.getDeviceIdKey(),JSONObject.toJSONString(command));
            gasCommand.setCommand(JSONObject.toJSONString(command));
            JSONObject response=null;
            try {
                response=HttpClientUtils.doPost(url,request);
                commandMapper.insertGasCommand(gasCommand);
            }  catch (Exception e) {
                throw new GlobleException(CodeMsg.COMMAND_ERROR);
            }
            stringBuilder.append(strings[i]).append("发送成功!").append(" ");
        }
        return stringBuilder.toString();
    }

    // 设置长休眠模式
    @Override
    public String postCommand12(String deviceIdKeys) {
        String[] strings=deviceIdKeys.trim().split(",");
        Command12 command=new Command12();
        command.setOPTypeKey("14");
        StringBuilder stringBuilder=new StringBuilder();

        GasCommand gasCommand = new GasCommand();
        gasCommand.setStatus(0);
        long time = System.currentTimeMillis();
        gasCommand.setTs_partation(time);
        String stime = String.valueOf(time);
        gasCommand.setTs(stime);

        for(int i=0;i<strings.length;i++){
            command.setDeviceIdKey(strings[i]);
            gasCommand.setDeviceId(strings[i]);
            if(strings[i].substring(0,2).equals("00")){
                gasCommand.setCommandCode("0F");
            }else{
                gasCommand.setCommandCode("5F");
            }
            Command command1 = commandMapper.getAttribute(strings[i]);
            if(command1.getRandNumKey()!=null && !command1.getRandNumKey().equals("")){
                if(command1.getIsEncry()==null || command1.getIsEncry()==0) {
                    command.setSecurityKey(false);
                    command.setPuidKey("");
                }else{
                    command.setSecurityKey(true);
                    command.setPuidKey(command1.getPuidKey());
                }
                command.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从gas_attribute表中取出的");
            }else{
                command1=commandMapper.getCommandAttribute(strings[i]);
                if(command1.getPuidKey()==null || ("").equals(command1.getPuidKey())) {
                    command.setSecurityKey(false);
                    command.setPuidKey("");
                }else{
                    command.setSecurityKey(true);
                    command.setPuidKey(command1.getPuidKey());
                }
                command.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从attribute_tv表中取出的");
            }
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyMMddHHmmss");
            command.setDateTimeKey(simpleDateFormat.format(new Date()));
            command.setTaskIDKey(UUIDUtils.getUUID());
            JSONObject request=new JSONObject();
            request.put(command.getDeviceIdKey(),JSONObject.toJSONString(command));
            gasCommand.setCommand(JSONObject.toJSONString(command));
            JSONObject response=null;
            try {
                response=HttpClientUtils.doPost(url,request);
                commandMapper.insertGasCommand(gasCommand);
            }  catch (Exception e) {
                throw new GlobleException(CodeMsg.COMMAND_ERROR);
            }
            stringBuilder.append(strings[i]).append("发送成功!").append(" ");
        }
        return stringBuilder.toString();
    }

    // 更新SOTP插件命令
    @Override
    public String postCommand13(String deviceIdkeys) {
        String[] strings=deviceIdkeys.trim().split(",");
        StringBuilder stringBuilder=new StringBuilder();

        GasCommand gasCommand = new GasCommand();
        gasCommand.setStatus(0);
        long time = System.currentTimeMillis();
        gasCommand.setTs_partation(time);
        String stime = String.valueOf(time);
        gasCommand.setTs(stime);
        gasCommand.setCommandCode("83");

        for(int i=0;i<strings.length;i++){
            Command command = new Command();
            command.setOPTypeKey("15");
            command.setDeviceIdKey(strings[i]);
            gasCommand.setDeviceId(strings[i]);

            Command command1 = commandMapper.getAttribute(strings[i]);
            if(command1.getRandNumKey()!=null && !command1.getRandNumKey().equals("")){
                if(command1.getIsEncry()==null || command1.getIsEncry()==0) {
                    command.setSecurityKey(false);
                    command.setPuidKey("");
                }else{
                    command.setSecurityKey(true);
                    command.setPuidKey(command1.getPuidKey());
                }
                command.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从gas_attribute表中取出的");
            }else{
                command1=commandMapper.getCommandAttribute(strings[i]);
                if(command1.getPuidKey()==null || ("").equals(command1.getPuidKey())) {
                    command.setSecurityKey(false);
                    command.setPuidKey("");
                }else{
                    command.setSecurityKey(true);
                    command.setPuidKey(command1.getPuidKey());
                }
                command.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从attribute_tv表中取出的");
            }

            command.setTaskIDKey(UUIDUtils.getUUID());
            JSONObject request = new JSONObject();
            request.put(command.getDeviceIdKey(), JSONObject.toJSONString(command));
            gasCommand.setCommand(JSONObject.toJSONString(command));
            JSONObject response=null;
            try {
                response=HttpClientUtils.doPost(url,request);
                commandMapper.insertGasCommand(gasCommand);
            } catch (Exception e) {
                throw new GlobleException(CodeMsg.COMMAND_ERROR);
            }
            stringBuilder.append(strings[i]).append("发送成功!").append(" ");
        }
        return stringBuilder.toString();
    }

    // 非计量故障关阀使能
    @Override
    public String postCommand14(RequestCommand14 requestCommand14) {
        String[] strings=requestCommand14.getDeviceIdKeys().trim().split(",");
        Command14 command14=new Command14();
        command14.setCloseValveEventKey(requestCommand14.getCloseValveEventKey());
        command14.setCloseValveKey(requestCommand14.getCloseValveKey());
        command14.setOPTypeKey("08");
        StringBuilder stringBuilder=new StringBuilder();

        GasCommand gasCommand = new GasCommand();
        gasCommand.setStatus(0);
        long time = System.currentTimeMillis();
        gasCommand.setTs_partation(time);
        String stime = String.valueOf(time);
        gasCommand.setTs(stime);
        gasCommand.setCommandCode("8D");

        for(int i=0;i<strings.length;i++){
            command14.setDeviceIdKey(strings[i]);
            gasCommand.setDeviceId(strings[i]);

            Command command1 = commandMapper.getAttribute(strings[i]);
            if(command1.getRandNumKey()!=null && !command1.getRandNumKey().equals("")){
                if(command1.getIsEncry()==null || command1.getIsEncry()==0) {
                    command14.setSecurityKey(false);
                    command14.setPuidKey("");
                }else{
                    command14.setSecurityKey(true);
                    command14.setPuidKey(command1.getPuidKey());
                }
                command14.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从gas_attribute表中取出的");
            }else{
                command1=commandMapper.getCommandAttribute(strings[i]);
                if(command1.getPuidKey()==null || ("").equals(command1.getPuidKey())) {
                    command14.setSecurityKey(false);
                    command14.setPuidKey("");
                }else{
                    command14.setSecurityKey(true);
                    command14.setPuidKey(command1.getPuidKey());
                }
                command14.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从attribute_tv表中取出的");
            }
            command14.setTaskIDKey(UUIDUtils.getUUID());
            JSONObject request=new JSONObject();
            request.put(command14.getDeviceIdKey(),JSONObject.toJSONString(command14));
            gasCommand.setCommand(JSONObject.toJSONString(command14));
            JSONObject response=null;
            try {
                response=HttpClientUtils.doPost(url,request);
                commandMapper.insertGasCommand(gasCommand);
            } catch (Exception e) {
                throw new GlobleException(CodeMsg.COMMAND_ERROR);
            }
            stringBuilder.append(strings[i]).append("发送成功!").append(" ");
        }
        return stringBuilder.toString();
    }

    // 清除命令
    @Override
    public String postCommand15(String deviceIdKeys) {
        String[] strings=deviceIdKeys.trim().split(",");
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < strings.length; i++) {
            strings[i] = strings[i].replace("\"","");
            try {
                Command15 command15 = new Command15();
                command15.setDeviceIdKey(strings[i]);
                List<GasCommand> gasCommands = commandMapper.selectGasCommand(strings[i]);
                //logger.info(String.valueOf(gasCommands));
                for(int k=0;k<gasCommands.size();k++){
                    GasCommand temp = gasCommands.get(k);
                    temp.setStatus(4);
                    commandMapper.updateGasCommand(temp);
                }

                JSONArray commandJson1 = ThingsBoardJWTTokenTelemetryValue.getTelemetryValue(thingsBoardUrl, thingsBoardDeviceId, thingsBoardUsername, thingsBoardPassword, strings[i]);
                System.out.println(commandJson1);
                if (commandJson1 != null) {
                    for (int j =0; j < commandJson1.size(); j++) {
                        command15.setOPTypeKey(commandJson1.getJSONObject(j).getString("OPTypeKey"));
                        command15.setPortKey(commandJson1.getJSONObject(j).getString("PortKey"));
                        command15.setKilled(commandJson1.getJSONObject(j).getString("killed"));
                        command15.setStatus(commandJson1.getJSONObject(j).getString("status"));
                        command15.setTaskIDKey(commandJson1.getJSONObject(j).getString("TaskIDKey"));
                        command15.setPuidKey(commandJson1.getJSONObject(j).getString("PuidKey"));
                        command15.setSecurityKey(commandJson1.getJSONObject(j).getBoolean("SecurityKey"));
                        command15.setTaskIDKey(UUIDUtils.getUUID());
                        JSONObject js = new JSONObject(new LinkedHashMap<String, Object>());
                        js.put("ts",commandJson1.getJSONObject(j).getLong("ts"));
                        JSONObject js1 = new JSONObject();
                        commandJson1.getJSONObject(j).remove("ts");
                        JSONObject request = new JSONObject();
                        js1.put(commandJson1.getJSONObject(j).getString("DeviceIDKey"),JSONObject.toJSONString(command15));
                        js.put("values",js1);
                        JSONObject response = null;
                        System.out.println(js);

                        try {
                            response = HttpClientUtils.doPost(url, js);
                        }catch (Exception e1){
                            throw new GlobleException(CodeMsg.COMMAND_ERROR);
                        }
                    }
                }
                stringBuilder.append(strings[i]).append("发送成功!").append(" ");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return stringBuilder.toString();
    }

    // 清除结束报文
    @Override
    public String postCommand16(String deviceIdKeys) {
        String[] strings=deviceIdKeys.trim().split(",");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            try {
                strings[i] = strings[i].replace("\"","");
                Command16 command16 = new Command16();
                command16.setDeviceIDKey(strings[i]);

                List<GasCommand> endPacket = commandMapper.selectGasEndPacket(strings[i]);
                for(int k=0;k<endPacket.size();k++){
                    endPacket.get(k).setStatus(4);
                    commandMapper.updateEndPacket(endPacket.get(k));
                }

                JSONArray commandJson1 = ThingsBoardJWTTokenTelemetryValue.getTelemetryValue(thingsBoardUrl, endDownCommandId, thingsBoardUsername, thingsBoardPassword, strings[i]);
                System.out.println(commandJson1);
                if (commandJson1 != null) {
                    for (int j =0; j < commandJson1.size(); j++) {
                        command16.setBusinessPeriodKey(commandJson1.getJSONObject(j).getString("BusinessPeriodKey"));
                        command16.setDateTimeKey(commandJson1.getJSONObject(j).getString("DateTimeKey"));
                        command16.setOPTypeKey(commandJson1.getJSONObject(j).getString("OPTypeKey"));
                        command16.setPuidKey(commandJson1.getJSONObject(j).getString("PuidKey"));
                        command16.setRandNumKey(commandJson1.getJSONObject(j).getString("RandNumKey"));
                        command16.setReadIntervalKey(commandJson1.getJSONObject(j).getString("ReadIntervalKey"));
                        command16.setSecurityKey(commandJson1.getJSONObject(j).getString("SecurityKey"));
                        command16.setUPPeriodKey(commandJson1.getJSONObject(j).getString("UPPeriodKey"));
                        command16.setUPTimeKey(commandJson1.getJSONObject(j).getString("UPTimeKey"));
                        command16.setKilled(commandJson1.getJSONObject(j).getString("killed"));
                        command16.setStatus(commandJson1.getJSONObject(j).getString("status"));
                        command16.setTaskIDKey(UUIDUtils.getUUID());
                        JSONObject js = new JSONObject(new LinkedHashMap<String, Object>());
                        js.put("ts",commandJson1.getJSONObject(j).getLong("ts"));
                        JSONObject js1 = new JSONObject();
                        commandJson1.getJSONObject(j).remove("ts");
                        JSONObject request = new JSONObject();
                        js1.put(commandJson1.getJSONObject(j).getString("DeviceIDKey"),JSONObject.toJSONString(command16));
                        js.put("values",js1);
                        System.out.println(js);
                        JSONObject response = null;
                        try {
                            response = HttpClientUtils.doPost(urlHold, js);
                        }catch (Exception e1){
                            throw new GlobleException(CodeMsg.COMMAND_ERROR);
                        }
                    }
                }

                stringBuilder.append(strings[i]).append("发送成功!").append(" ");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }

    // 读取IMEI和ICCID号码命令
    @Override
    public String postCommand17(String deviceIdKeys) {
        String[] strings=deviceIdKeys.trim().split(",");
        Command command=new Command();
        command.setOPTypeKey("80");
        StringBuilder stringBuilder=new StringBuilder();

        GasCommand gasCommand = new GasCommand();
        gasCommand.setStatus(0);
        long time = System.currentTimeMillis();
        gasCommand.setTs_partation(time);
        String stime = String.valueOf(time);
        gasCommand.setTs(stime);
        gasCommand.setCommandCode("F0");

        for(int i=0;i<strings.length;i++){
            command.setDeviceIdKey(strings[i]);
            gasCommand.setDeviceId(strings[i]);

            Command command1 = commandMapper.getAttribute(strings[i]);
            if(command1.getRandNumKey()!=null && !command1.getRandNumKey().equals("")){
                if(command1.getIsEncry()==null || command1.getIsEncry()==0) {
                    command.setSecurityKey(false);
                    command.setPuidKey("");
                }else{
                    command.setSecurityKey(true);
                    command.setPuidKey(command1.getPuidKey());
                }
                command.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从gas_attribute表中取出的");
            }else{
                command1=commandMapper.getCommandAttribute(strings[i]);
                if(command1.getPuidKey()==null || ("").equals(command1.getPuidKey())) {
                    command.setSecurityKey(false);
                    command.setPuidKey("");
                }else{
                    command.setSecurityKey(true);
                    command.setPuidKey(command1.getPuidKey());
                }
                command.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从attribute_tv表中取出的");
            }

            command.setTaskIDKey(UUIDUtils.getUUID());
            JSONObject request=new JSONObject();
            request.put(command.getDeviceIdKey(),JSONObject.toJSONString(command));
            gasCommand.setCommand(JSONObject.toJSONString(command));
            JSONObject response=null;
            try {
                response=HttpClientUtils.doPost(url,request);
                commandMapper.insertGasCommand(gasCommand);
            }  catch (Exception e) {
                throw new GlobleException(CodeMsg.COMMAND_ERROR);
            }
            stringBuilder.append(strings[i]).append("发送成功!").append(" ");
        }
        return stringBuilder.toString();
    }

    // 预付费表结束报文
    @Override
    public String postCommand18(RequestCommand18 requestCommand18) {
        String[] strings=requestCommand18.getDeviceIdKeys().trim().split(",");
        Command18 command18=new Command18();
        command18.setOPTypeKey("19");
        command18.setReadIntervalKey(requestCommand18.getReadIntervalKey());
        command18.setUPTimeKey(requestCommand18.getUpTimeKey());
        command18.setUPPeriodKey(requestCommand18.getUpPeriodKey());
        command18.setRemainingSumKey(requestCommand18.getRemainingSumKey()); //余额
        command18.setUnitPriceKey(requestCommand18.getUnitPriceKey());   // 单价
        if(requestCommand18.getSecurityKey()==null) {
            command18.setSecurityKey(false);
        } else {
            command18.setSecurityKey(requestCommand18.getSecurityKey());
        }
        StringBuilder stringBuilder=new StringBuilder();

        GasCommand gasCommand = new GasCommand();
        gasCommand.setStatus(0);
        long time = System.currentTimeMillis();
        gasCommand.setTs_partation(time);
        String stime = String.valueOf(time);
        gasCommand.setTs(stime);
        gasCommand.setCommandCode("12");

        for(int i=0;i<strings.length;i++){
            command18.setDeviceIdKey(strings[i]);
            gasCommand.setDeviceId(strings[i]);

            Command command1 = commandMapper.getAttribute(strings[i]);
            if(command1.getRandNumKey()!=null && !command1.getRandNumKey().equals("")){
                if(command1.getIsEncry()==null || command1.getIsEncry()==0) {
                    command18.setPuidKey("");
                }else{
                    command18.setPuidKey(command1.getPuidKey());
                }
                command18.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从gas_attribute表中取出的");
            }else{
                command1=commandMapper.getCommandAttribute(strings[i]);
                if(command1.getPuidKey()==null || ("").equals(command1.getPuidKey())) {
                    command18.setPuidKey("");
                }else{
                    command18.setPuidKey(command1.getPuidKey());
                }
                command18.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从attribute_tv表中取出的");
            }
            command18.setTaskIDKey(UUIDUtils.getUUID());
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyMMddHHmmss");
            command18.setDateTimeKey(simpleDateFormat.format(new Date()));
            JSONObject request=new JSONObject();
            request.put(command18.getDeviceIdKey(),JSONObject.toJSONString(command18));
            gasCommand.setCommand(JSONObject.toJSONString(command18));
            JSONObject response=null;
            try {
                //response=HttpClientUtils.doPost(url,request);
                response=HttpClientUtils.doPost(urlHold,request);
                commandMapper.insertEndPacket(gasCommand);
            } catch (Exception e) {
                throw new GlobleException(CodeMsg.COMMAND_ERROR);
            }
            stringBuilder.append(strings[i]).append("发送成功!").append(" ");
        }
        return stringBuilder.toString();
    }

    // 预付费表参数配置
    @Override
    public String postCommand19(RequestCommand19 requestCommand19) {
        String[] strings=requestCommand19.getDeviceIdKeys().trim().split(",");
        Command19 command19=new Command19();
        command19.setOPTypeKey("18");
        command19.setCloseRMBKey(requestCommand19.getCloseRMBKey());
        command19.setAlarmRMBKey(requestCommand19.getAlarmRMBKey());
        command19.setLostDaysKey(requestCommand19.getLostDaysKey());
        command19.setUnitPriceKey(requestCommand19.getUnitPriceKey());
        StringBuilder stringBuilder=new StringBuilder();

        GasCommand gasCommand = new GasCommand();
        gasCommand.setStatus(0);
        long time = System.currentTimeMillis();
        gasCommand.setTs_partation(time);
        String stime = String.valueOf(time);
        gasCommand.setTs(stime);
        gasCommand.setCommandCode("B2");

        for(int i=0;i<strings.length;i++){
            command19.setDeviceIdKey(strings[i]);
            gasCommand.setDeviceId(strings[i]);

            Command command1 = commandMapper.getAttribute(strings[i]);
            if(command1.getRandNumKey()!=null && !command1.getRandNumKey().equals("")){
                if(command1.getIsEncry()==null || command1.getIsEncry()==0) {
                    command19.setSecurityKey(false);
                    command19.setPuidKey("");
                }else{
                    command19.setSecurityKey(true);
                    command19.setPuidKey(command1.getPuidKey());
                }
                command19.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从gas_attribute表中取出的");
            }else{
                command1=commandMapper.getCommandAttribute(strings[i]);
                if(command1.getPuidKey()==null || ("").equals(command1.getPuidKey())) {
                    command19.setSecurityKey(false);
                    command19.setPuidKey("");
                }else{
                    command19.setSecurityKey(true);
                    command19.setPuidKey(command1.getPuidKey());
                }
                command19.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从attribute_tv表中取出的");
            }
            command19.setTaskIDKey(UUIDUtils.getUUID());
            JSONObject request=new JSONObject();
            request.put(command19.getDeviceIdKey(),JSONObject.toJSONString(command19));
            gasCommand.setCommand(JSONObject.toJSONString(command19));
            JSONObject response=null;
            try {
                response=HttpClientUtils.doPost(url,request);
                commandMapper.insertGasCommand(gasCommand);
            } catch (Exception e) {
                throw new GlobleException(CodeMsg.COMMAND_ERROR);
            }
            stringBuilder.append(strings[i]).append("发送成功!").append(" ");
        }
        return stringBuilder.toString();
    }

    // 预付费表下载软件更新版本
    @Override
    public String postCommand20(RequestCommand20 requestCommand20) {
        String[] strings = requestCommand20.getDeviceIdKeys().trim().split(",");
        Command20 command20 = new Command20();
        command20.setURLKey(requestCommand20.getuRLKey());
        //System.out.println(requestCommand20.getuRLKey());
        command20.setPartsKey(requestCommand20.getPartsKey());
        //System.out.println(requestCommand20.getPartsKey());
        command20.setOPTypeKey("02");
        StringBuilder stringBuilder=new StringBuilder();

        GasCommand gasCommand = new GasCommand();
        gasCommand.setStatus(0);
        long time = System.currentTimeMillis();
        gasCommand.setTs_partation(time);
        String stime = String.valueOf(time);
        gasCommand.setTs(stime);
        gasCommand.setCommandCode("83");

        for(int i=0;i<strings.length;i++){
            command20.setDeviceIdKey(strings[i]);
            gasCommand.setDeviceId(strings[i]);
            // 根据设备id获取设备属性信息
            Command command1 = commandMapper.getAttribute(strings[i]);
            if(command1.getRandNumKey()!=null && !command1.getRandNumKey().equals("")){
                if(command1.getIsEncry()==null || command1.getIsEncry()==0) {
                    command20.setSecurityKey(false);
                    command20.setPuidKey("");
                }else{
                    command20.setSecurityKey(true);
                    command20.setPuidKey(command1.getPuidKey());
                }
                command20.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从gas_attribute表中取出的");
            }else{
                command1=commandMapper.getCommandAttribute(strings[i]);
                if(command1.getPuidKey()==null || ("").equals(command1.getPuidKey())) {
                    command20.setSecurityKey(false);
                    command20.setPuidKey("");
                }else{
                    command20.setSecurityKey(true);
                    command20.setPuidKey(command1.getPuidKey());
                }
                command20.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从attribute_tv表中取出的");
            }
            command20.setTaskIDKey(UUIDUtils.getUUID());
            JSONObject request=new JSONObject();
            request.put(command20.getDeviceIdKey(),JSONObject.toJSONString(command20));
            gasCommand.setCommand(JSONObject.toJSONString(command20));
            JSONObject response=null;
            try {
                response=HttpClientUtils.doPost(url,request);
                commandMapper.insertGasCommand(gasCommand);
            } catch (Exception e) {
                throw new GlobleException(CodeMsg.COMMAND_ERROR);
            }
            stringBuilder.append(strings[i]).append("发送成功!").append(" ");
        }
        return stringBuilder.toString();
    }

    // NB模组默认值修改
    @Override
    public String postCommand21(RequestCommand21 requestCommand21) {
        String[] strings = requestCommand21.getDeviceIdKeys().trim().split(",");
        Command21 command21 = new Command21();
        command21.setOPTypeKey("21");
        command21.setSotpAddrKey(requestCommand21.getSotpAddrKey());
        command21.setFotaUDPAddrKey(requestCommand21.getFotaUDPAddrKey());
        StringBuilder stringBuilder=new StringBuilder();

        GasCommand gasCommand = new GasCommand();
        gasCommand.setStatus(0);
        long time = System.currentTimeMillis();
        gasCommand.setTs_partation(time);
        String stime = String.valueOf(time);
        gasCommand.setTs(stime);
        gasCommand.setCommandCode("F8");

        for(int i=0;i<strings.length;i++){
            command21.setDeviceIdKey(strings[i]);
            gasCommand.setDeviceId(strings[i]);
            // 根据设备id获取设备属性信息
            Command command1 = commandMapper.getAttribute(strings[i]);
            if(command1.getRandNumKey()!=null && !command1.getRandNumKey().equals("")){
                if(command1.getIsEncry()==null || command1.getIsEncry()==0) {
                    command21.setSecurityKey(false);
                    command21.setPuidKey("");
                }else{
                    command21.setSecurityKey(true);
                    command21.setPuidKey(command1.getPuidKey());
                }
                command21.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从gas_attribute表中取出的");
            }else{
                command1=commandMapper.getCommandAttribute(strings[i]);
                if(command1.getPuidKey()==null || ("").equals(command1.getPuidKey())) {
                    command21.setSecurityKey(false);
                    command21.setPuidKey("");
                }else{
                    command21.setSecurityKey(true);
                    command21.setPuidKey(command1.getPuidKey());
                }
                command21.setRandNumKey(command1.getRandNumKey());
                logger.info("此RandNumKey是从attribute_tv表中取出的");
            }
            command21.setTaskIDKey(UUIDUtils.getUUID());
            JSONObject request=new JSONObject();
            request.put(command21.getDeviceIdKey(),JSONObject.toJSONString(command21));
            gasCommand.setCommand(JSONObject.toJSONString(command21));
            JSONObject response=null;
            try {
                response=HttpClientUtils.doPost(url,request);
                commandMapper.insertGasCommand(gasCommand);
            } catch (Exception e) {
                throw new GlobleException(CodeMsg.COMMAND_ERROR);
            }
            stringBuilder.append(strings[i]).append("发送成功!").append(" ");
        }
        return stringBuilder.toString();
    }
}
*/

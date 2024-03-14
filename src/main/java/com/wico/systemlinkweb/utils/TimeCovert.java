package com.wico.systemlinkweb.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间戳转换工具类
 * @Author XuCheng
 * @Date 2020/4/29 13:49
 */
public class TimeCovert {
    //时间戳国际化
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static String stringToDate(String s){
        String res = null;
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            res = simpleDateFormat1.format(simpleDateFormat.parse(s));
        }catch (Exception e){
            System.out.println("时间转换失败");
        }
        return res;
    }
}

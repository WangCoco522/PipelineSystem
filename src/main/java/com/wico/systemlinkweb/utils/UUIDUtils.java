package com.wico.systemlinkweb.utils;

import java.util.UUID;

/**
 * @Author XuCheng
 * @Date 2020/4/29 15:38
 */
// 生成随机数
public class UUIDUtils {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}

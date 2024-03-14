package com.wico.systemlinkweb.utils;

/**
 * 进制转换工具类
 * @Author XuCheng
 * @Date 2020/4/29 15:12
 */
public class HexToBinaryStr {
    private static String[] binaryArray =
            {"0000","0001","0010","0011",
                    "0100","0101","0110","0111",
                    "1000","1001","1010","1011",
                    "1100","1101","1110","1111"};

    public static byte[] hexStringToByte(String hex){
        int len = (hex.length()) / 2;
        byte[] bytes = new byte[len];
        char[] chars = hex.toCharArray();
        for (int i = 0; i < len; i++){
            int position = i * 2;
            bytes[i] = (byte) (charToByte(chars[position]) << 4 | charToByte(chars[position+1]));
        }
        return bytes;
    }

    private static byte charToByte(char aChar) {
        return (byte)"0123456789ABCDEF".indexOf(aChar);
    }

    public static String stringToHexString(String str){
        StringBuilder hex = new StringBuilder();
        char[] chars = str.toCharArray();
        for (char aChar : chars) {
            hex.append(Integer.toHexString(aChar).toUpperCase());
        }
        return hex.toString();
    }

    public static String bytes2BinaryStr(byte[] bArray){
        String outStr = "";
        int pos = 0;
        for(byte b:bArray){
            //高四位
            pos = (b&0xF0)>>4;
            outStr+=binaryArray[pos];
            //低四位
            pos=b&0x0F;
            outStr+=binaryArray[pos];
        }
        return outStr;
    }

}

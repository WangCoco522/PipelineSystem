package com.wico.systemlinkweb.utils;

import com.wico.systemlinkweb.controller.DeviceController;
import com.wico.systemlinkweb.result.CodeMsg;
import com.wico.systemlinkweb.result.Result;


import jxl.read.biff.BiffException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 导出Excel工具类
 * @Author XuCheng
 * @Date 2020/5/6 9:37
 */
public class ExcelUtils {
    private static Logger logger = LoggerFactory.getLogger(DeviceController.class);
    public static Map<String, String> excelSqlMap = new HashMap<String, String>(){{
        put("设备ID","deviceIdKey");
        put("IMEI","commNoKey");
        put("当前软件版本号","swrlseKey");
        put("采集周期","readIntervalKey");
        put("所在省","deviceProvince");
        put("所在市","deviceCity");
        put("通讯号码(ICCID号)","simNumber");
        put("用户编码","householdId");
        put("户主姓名","householdName");
        put("户主电话","telephone");
        put("安装时间","installTime");
        put("设备类型","deviceType");
        put("销售公司","company");
        put("户主身份证号","householdNumber");
        put("最新采集时间","collectTimeKey");
        put("设备厂商","deviceProduct");
        put("最新抄码","latestCode");
        put("安装地址","communityName");
        put("设备号(不含type)","forNumber");
        put("表的分类、来源","kind");
        put("加密插件ID","puIdKey");
        put("册本号(安装的小区编号)","book");
        put("CIMI号","cimi");
        put("所在区","deviceLocation");
        put("模组型号","module");
        put("详细设备类型","deviceType1");
        put("","");//某些格式的excel会多算一列
    }};
    public static Map<String, String> DeviceProductMap = new HashMap<String, String>(){{
        put("浙江威星","35");
        put("杭州威星","35");
        put("丹东思凯","15");
        put("浙江荣鑫","27");
        put("天津五机","07");
        put("埃创","04");
        put("上海飞奥","22");
 }};
    public static Object getFieldValueByName(Object o, String fieldName) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void setFieldValueByName(Object obj, String fieldName, Object value){
        try {
            // 获取obj类的字节文件对象
            Class c = obj.getClass();
            // 获取该类的成员变量
            Field f = c.getDeclaredField(fieldName);
            // 取消语言访问检查
            f.setAccessible(true);
            // 给变量赋值
            f.set(obj, value);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static Result verifyExcel (Sheet sheet, Result result) throws IOException {
      // 列数
        Row row = sheet.getRow(0);
        List<String> mismatchTemplate = new ArrayList<>();
        int column = sheet.getRow(0).getPhysicalNumberOfCells();
        for (int i = 0; i < column; i++) {
            row.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
            String title = row.getCell(i).getStringCellValue();
            if(!excelSqlMap.containsKey(title)){
                mismatchTemplate.add(title);
            }
        }
        if(!mismatchTemplate.isEmpty()) {
            result.setMsg("标题"+mismatchTemplate.toString()+"与模板不匹配");
            result.setCode(500404);
            return result;
        }
       return result.success("格式正确");
    }
    public static ResponseEntity<byte[]> download(File file) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        List list = new ArrayList<String>();
        list.add("Content-Disposition");
        headers.setAccessControlExposeHeaders(list);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", file.getName());

        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);
    }


    public static String[] getSheet(FileInputStream inputStream) throws BiffException, IOException {
        String[] result;
        //Test1.xls 为你放在java项目下的文件名
        Workbook book = new HSSFWorkbook(inputStream);
        Sheet sheet = book.getSheetAt(0);

        int rows = sheet.getRow(0).getPhysicalNumberOfCells();//行
        int cols =  sheet.getLastRowNum();//列

        System.out.println("总列数：" + cols);
        System.out.println("总行数:" + rows);
        System.out.println("----------------------------");
        result = new String[rows];
        int i = 0;
        //循环读取数据
        for (i = 0; i < rows; i++) {
            //getCell(x,y)   第y行的第x列
            Row row = sheet.getRow(i);
            result[i] = new String(row.getCell(0).getStringCellValue());
        }
        return result;
    }

    public static File CreateFile() {
        //创建excel文件
        File file1 = new File(System.currentTimeMillis() + "_task.xls");
        System.out.println("文件创建成功");
        try {
            file1.createNewFile();
        } catch (IOException e) {
            System.out.println("该文件已存在");
        }
        return file1;
    }
}

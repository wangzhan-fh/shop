package com.fh.shop.util;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.xssf.usermodel.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ExcelUtil {

    public static XSSFWorkbook buildWookBook(List dataList, String sheetName, String[] header, String[] prpos, Class clazz ) {
        //创建xssfworkbook文件
        XSSFWorkbook xwb =new XSSFWorkbook();
        //创建页
        XSSFSheet createSheet = xwb.createSheet(sheetName);
        buidHeader(xwb, createSheet,header);
        buildBody(dataList, xwb, createSheet,prpos,clazz);
        return xwb;
    }
    private static void buildBody(List dataList, XSSFWorkbook xwb, XSSFSheet createSheet,String[] prpos,Class clazz) {
        //样式为日期格式
        XSSFCellStyle cellStyle = xwb.createCellStyle();
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));

        XSSFCellStyle cellStyle1 = xwb.createCellStyle();
        cellStyle1.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        //样式设置为数类型
        for (int i = 0; i < dataList.size(); i++) {
            //创建行
            XSSFRow row = createSheet.createRow(i+1);
            Object o = dataList.get(i);
            buildBodyRow(dataList, cellStyle, cellStyle1,o, row,prpos,clazz);

        }
    }

    private static void buildBodyRow(List list, XSSFCellStyle cellStyle, XSSFCellStyle cellStyle1,Object o,  XSSFRow row, String[] prpos,Class clazz) {
        for (int i = 0; i <prpos.length ; i++) {
            XSSFCell cell = row.createCell(i);
            try {
                Field declaredField = clazz.getDeclaredField(prpos[i]);
                declaredField.setAccessible(true);
                Class<?> type = declaredField.getType();
                Object obj = declaredField.get(o);
                if(type==java.lang.String.class){
                    cell.setCellValue(obj.toString());
                }
                if(type==java.lang.Integer.class){
                    cell.setCellValue(obj.toString());
                }
                if(type==java.lang.Long.class){
                    cell.setCellValue(obj.toString());
                }
                if(type==java.util.Date.class){
                    cell.setCellValue((Date)obj);
                    cell.setCellStyle(cellStyle);
                }
                if(type==java.math.BigDecimal.class){
                    cell.setCellValue(((BigDecimal)obj).doubleValue());
                    cell.setCellStyle(cellStyle1);
                }
                if(type==java.lang.Double.class){
                    cell.setCellValue(((Double)obj).doubleValue());
                    cell.setCellStyle(cellStyle1);
                }


            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }


    }

    private  static void buidHeader(XSSFWorkbook xwb, XSSFSheet createSheet,String[] header) {
        XSSFRow row = createSheet.createRow(0);
        for (int i = 0; i <header.length; i++) {
            row.createCell(i).setCellValue(header[i]);
        }
    }
}

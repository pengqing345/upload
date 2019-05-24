package com.pq.test.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import net.sf.json.JSONArray;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lanquanhe on 2017/7/12.
 * 用于读取Excel文件数据
 */

public class ExcelUtils {


    public static List<LinkedHashMap<String, String>> excel3json(String filePath) {

        File file = new File(filePath);
    /*    if (file != null && file.length() > 1000 * 1000) {
            throw new CoreException("导入的Excel不能超过1MB,请检查Excel内容或重新制作表格!");
        }*/
        // 返回的map
        LinkedHashMap<String, String> excelMap = new LinkedHashMap<>();

        // Excel列的样式，主要是为了解决Excel数字科学计数的问题
        CellStyle cellStyle = null;
        // 根据Excel构成的对象
        Workbook wb = null;
        // 如果是2007及以上版本，则使用想要的Workbook以及CellStyle

        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);

        List<LinkedHashMap<String, String>> result = new ArrayList<>();

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            if (suffix.endsWith("xlsx")) {
                wb = new XSSFWorkbook(fis);
            } else {
                POIFSFileSystem fs = new POIFSFileSystem(fis);
                wb = new HSSFWorkbook(fs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (wb != null) {
                    wb.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        cellStyle = wb.createCellStyle();

        // sheet表个数
        int sheetsCounts = wb.getNumberOfSheets();
        // 遍历每一个sheet
        for (int i = 0; i < sheetsCounts; i++) {
            Sheet sheet = wb.getSheetAt(i);
            // 一个sheet表对于一个List
            List list = new LinkedList();

            // 将第一行的列值作为正个json的key
            String[] cellNames;
            // 取第一行列的值作为key
            Row fisrtRow = sheet.getRow(0);
            // 如果第一行就为空，则是空sheet表，该表跳过
            if (null == fisrtRow) {
                continue;
            }
            // 得到第一行有多少列
            int curCellNum = fisrtRow.getLastCellNum();
            // 根据第一行的列数来生成列头数组
            cellNames = new String[curCellNum];
            // 单独处理第一行，取出第一行的每个列值放在数组中，就得到了整张表的JSON的key
            for (int m = 0; m < curCellNum; m++) {
                Cell cell = fisrtRow.getCell(m);
                // 设置该列的样式是字符串
                cell.setCellStyle(cellStyle);
                cell.setCellType(CellType.STRING);
                // 取得该列的字符串值
                cellNames[m] = cell.getStringCellValue().trim();
            }

            // 从第二行起遍历每一行
            int rowNum = sheet.getPhysicalNumberOfRows();

            for (int j = 1; j <= rowNum; j++) {
                // 一行数据对于一个Map
                LinkedHashMap<String, String> dataMap = new LinkedHashMap();
                // 取得某一行
                Row row = sheet.getRow(j);
                if (row != null && !isRowEmpty(row)) {
                    // 遍历每一列
                    for (int k = 0; k < curCellNum; k++) {
                        String cellValue = "";
                        Cell cell = row.getCell(k);
                        if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                            dataMap.put(cellNames[k], null);
                        } else {
                            switch (cell.getCellType()){
                                case Cell.CELL_TYPE_NUMERIC: //数字
                                    if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
                                        //用于转化为日期格式
                                        Date d = cell.getDateCellValue();
                                        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                                        cellValue = formater.format(d);
                                    } else  {//处理数值格式
                                        cellValue = cell.getNumericCellValue()+"";
                                    }
                                    break;
                                case Cell.CELL_TYPE_STRING: // 字符串
                                    cellValue = String.valueOf(cell.getStringCellValue());
                                    break;
                                case Cell.CELL_TYPE_BOOLEAN: // Boolean
                                    cellValue = String.valueOf(cell.getBooleanCellValue());
                                    break;
                                case Cell.CELL_TYPE_FORMULA: // 公式
                                    cellValue = String.valueOf(cell.getCellFormula());
                                    break;
                                case Cell.CELL_TYPE_ERROR: // 故障
                                    cellValue = "非法字符";
                                    break;
                                default:
                                    cellValue = "未知类型";
                                    break;
                            }
                            // 保存该单元格的数据到该行中
                            dataMap.put(cellNames[k], cellValue);
                        }
                    }
                    // 保存该行的数据到该表的List中
                    result.add(dataMap);
                } else {
                    continue;
                }
            }
            // 将该sheet表的表名为key，List转为json后的字符串为Value进行存储
            excelMap.put(sheet.getSheetName(), JSON.toJSONString(list, false));
        }
        return result;
    }



    //判断是否空行
    public static boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
                return false;
        }
        return true;
    }

}

package com.yimkong;

import com.yimkong.data.ColumnMeta;
import com.yimkong.data.TemplateMeta;
import com.yimkong.setting.ExcelConfig;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * author yg
 * description
 * date 2019/10/12
 */
public class ExcelReader {

    public Collection<TemplateMeta> read(String excel_path) {
        File file = new File(excel_path);
        File[] files = file.listFiles();
        if (files == null) {
            return new ArrayList<>();
        }
        List<TemplateMeta> list = new ArrayList<>();
        for (File input : files) {
            String name = input.getName();
            if (name.startsWith("~$"))
                continue;
            if (input.isFile() && input.canRead() && (name.endsWith(".xls") || (name.endsWith(".xlsx")))) {
                try {
                    Map<String, TemplateMeta> convert = convert(input);
                    list.addAll(convert.values());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    private Map<String, TemplateMeta> convert(File file) throws IOException, InvalidFormatException {
        FileInputStream fileInputStream = new FileInputStream(file);
        Workbook book = WorkbookFactory.create(fileInputStream);
        try {
            Map<String, TemplateMeta> map = new HashMap<>();
            int sheetNum = book.getNumberOfSheets();
            for (int i = 0; i < sheetNum; i++) {
                String sheetName = book.getSheetName(i).trim();
                if (sheetName.indexOf("#") != 0) {
                    continue;
                }
                Sheet sheet = book.getSheetAt(i);
                TemplateMeta meta = parseToTemplateMeta(sheet);
                meta.fileName = file.getName();
                if (meta.columns.isEmpty()) {
                    continue;
                }
                map.put(meta.name, meta);
            }
            return map;
        } finally {
            fileInputStream.close();
        }
    }

    private TemplateMeta parseToTemplateMeta(Sheet sheet) {
        ExcelConfig excelConfig = Start.excelConfig;
        Row row = sheet.getRow(excelConfig.getNameRow() - 1);
        //校验数据
        Cell cell = row.getCell(excelConfig.getNameColumn() - 1);
        if (cell == null) {
            throw new RuntimeException("配置表名字不能为空");
        }
        String name = cell.getStringCellValue();
        if (name == null) {
            throw new RuntimeException("配置表名字不能为空");
        }
        TemplateMeta templateMeta = new TemplateMeta(name);

        Row nameRow = sheet.getRow(excelConfig.getPropertiesName() - 1);
        if (nameRow == null) {
            throw new RuntimeException("配置表表头格式不完整，缺少列名定义行");
        }
        Row typeRow = sheet.getRow(excelConfig.getType() - 1);
        if (typeRow == null) {
            throw new RuntimeException("配置表表头格式不完整，缺少列类型定义行");
        }
        Row flagRow = sheet.getRow(excelConfig.getMark() - 1);
        if (flagRow == null) {
            throw new RuntimeException("配置表表头格式不完整，缺少标记定义行");
        }
        Row descRow = sheet.getRow(excelConfig.getExplainRow() - 1);
        if (descRow == null) {
            throw new RuntimeException("配置表表头格式不完整，缺少描述定义行");
        }
        //得到属性的元信息
        for (int i = nameRow.getFirstCellNum(); i < nameRow.getLastCellNum(); i++) {
            Cell flagCell = flagRow.getCell(i);
            if (flagCell == null) {
                throw new RuntimeException("列标记不能为空，第" + (i + 1) + "列");
            }
            try {
                int flag = (Double.valueOf(flagCell.toString())).intValue();
                if (flag != excelConfig.getMarkFlag()) {
                    continue;
                }
            } catch (Exception ex) {
                System.err.println("flagCell.toString() " + flagCell.toString());
                ex.printStackTrace();
                throw new RuntimeException("列标记数据格式错误，第" + (i + 1) + "列");
            }

            ColumnMeta meta = new ColumnMeta(i);
            Cell nameCell = nameRow.getCell(i);
            if (nameCell == null) {
                throw new RuntimeException("列名不能为空，第" + (i + 1) + "列");
            }
            String fieldName = nameCell.getStringCellValue();
            if (fieldName == null) {
                throw new RuntimeException("列名不能为空，第" + (i + 1) + "列");
            }
            meta.parseName(fieldName);
            Cell typeCell = typeRow.getCell(i);
            if (typeCell == null) {
                throw new RuntimeException("列类型不能为空，第" + (i + 1) + "列");
            }
            String type = typeCell.getStringCellValue();
            meta.parseType(type);
            Cell descCell = descRow.getCell(i);
            if (descCell == null) {
                throw new RuntimeException("列名不能为空，第" + (i + 1) + "列");
            }
            meta.parseDesc(descCell.getStringCellValue());
            templateMeta.addColumnMeta(meta);
        }
        return templateMeta;
    }

}

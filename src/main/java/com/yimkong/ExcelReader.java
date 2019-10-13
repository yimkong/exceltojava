package com.yimkong;

import com.yimkong.data.Table;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * author yg
 * description
 * date 2019/10/12
 */
public class ExcelReader {

    public List<Table> read(String excel_path) {
        File file = new File(excel_path);
        File[] files = file.listFiles();
        List<Table> tables = new LinkedList<>();
        for (File input : files) {
            String name = input.getName();
            if (input.isFile() && input.canRead() && (name.endsWith(".xls") || (name.endsWith(".xlsx")))) {
                try {
                    List<Table> results = convert(input);
                    tables.addAll(results);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return tables;
    }

    private List<Table> convert(File file) throws IOException, InvalidFormatException {
        FileInputStream fileInputStream = new FileInputStream(file);
        Workbook book = WorkbookFactory.create(fileInputStream);
        String fileName = file.getName();
        List<Table> tableList = new ArrayList<Table>();
        try {
            int sheetNum = book.getNumberOfSheets();
            for (int i = 0; i < sheetNum; i++) {
                Table table = getServerTableBySheet(book.getSheetAt(i), fileName);
                if (table != null) {
                    tableList.add(table);
                }
            }
        } finally {
            fileInputStream.close();
        }

        return tableList;
    }

    private Table getServerTableBySheet(Sheet sheet, String fileName) {
//        Table table = new Table();
//        Row row = sheet.getRow(CLASS_NAME_ROW);
//        if (row == null) {
//            return null;
//        }
//
//        if (row.getCell(0) == null || !SERVER_NAME.equals(row.getCell(0).getStringCellValue())) {
//            return null;
//        }
//        if (row.getCell(CLASS_NAME_CELL) == null
//                || row.getCell(CLASS_NAME_CELL).getCellType() != Cell.CELL_TYPE_STRING) {
//            return null;
//        }
//        String clazzName = row.getCell(CLASS_NAME_CELL).getStringCellValue();
//        table.setClazzName(clazzName);
//        table.setFileName(fileName);
//        Row name = sheet.getRow(NAME_ROW);
//        int cellStart = name.getFirstCellNum();
//        int cellEnd = name.getLastCellNum();
//        List<Head> heads = ExcelUtil.parseHead(sheet.getRow(RULE_ROW), sheet.getRow(FLAG_ROW), name, sheet.getRow(TYPE_ROW),
//                sheet.getRow(COMMENT_ROW));
//        table.setHeads(heads);
//        for (int i = TYPE_ROW + 1; i <= sheet.getLastRowNum(); i++) {
//            // 结束标志
//            Row finishRow = sheet.getRow(i);
//            if (finishRow == null) {
//                break;
//            }
//            Cell finish = finishRow.getCell(CLASS_NAME_ROW);
//            if (finish == null) {
//                break;
//            } else if (finish.getCellType() == Cell.CELL_TYPE_STRING) {
//                if (finish.getStringCellValue().equalsIgnoreCase(FINISH_NAME)) {
//                    break;
//                }
//            }
//            List<Object> data = parseServerData(heads, cellStart, cellEnd, sheet.getRow(i));
//            if (data == null)
//                continue;
//            table.addData(data);
//        }
//        ExcelUtil.reviseHead(heads);
//        table.trimServer();
//        return table;
        return null;
    }
}

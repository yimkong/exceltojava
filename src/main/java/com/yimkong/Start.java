package com.yimkong;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.yimkong.setting.ExcelConfig;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * author yg
 * description
 * date 2019/10/12
 */
public class Start {
    public static void main(String[] args) throws FileNotFoundException {
        ExcelReader excelReader = new ExcelReader();
        Properties properties = new Properties();
        try {
            properties.load(new InputStreamReader(new FileInputStream("setting.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        XStream xStream = new XStream(new DomDriver());
        xStream.processAnnotations(ExcelConfig.class);
        ExcelConfig excelConfig = (ExcelConfig)xStream.fromXML(new FileInputStream("excel.xml"));
        String excel_path = properties.getProperty("excel_path");
        String out_path = properties.getProperty("out_path");

        excelReader.read(excel_path);
    }
}

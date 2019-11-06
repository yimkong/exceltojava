package com.yimkong;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.yimkong.data.TemplateMeta;
import com.yimkong.setting.ExcelConfig;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Properties;

/**
 * author yg
 * description
 * date 2019/10/12
 */
public class Start {
    public static ExcelConfig excelConfig;

    static  {
        XStream xStream = new XStream(new DomDriver());
        xStream.processAnnotations(ExcelConfig.class);
        try {
            excelConfig = (ExcelConfig) xStream.fromXML(new FileInputStream("G:\\legends\\exceltojava\\src\\main\\resources\\excel.xml"));
            System.err.println();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        ExcelReader excelReader = new ExcelReader();
        ExcelWriter excelWriter = new ExcelWriter();
        Properties properties = new Properties();
        try {
            properties.load(new InputStreamReader(new FileInputStream("G:\\legends\\exceltojava\\src\\main\\resources\\setting.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String excel_path = properties.getProperty("excel_path");
        String out_path = properties.getProperty("out_path");
        String aPackage = properties.getProperty("package");
        Collection<TemplateMeta> read = excelReader.read(excel_path);
        excelWriter.write(read, aPackage, out_path);
    }
}

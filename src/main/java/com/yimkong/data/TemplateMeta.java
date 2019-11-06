package com.yimkong.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateMeta {

   public final List<ColumnMeta> columns = new ArrayList<>();

    public final Map<String, ColumnMeta> ref = new HashMap<>();

    //类名
    public final String name;

    public String fileName;
    //文件名

    public TemplateMeta(String name) {
        this.name = name;
    }

    public void addColumnMeta(ColumnMeta meta) {
        columns.add(meta);
        ref.put(meta.name, meta);
    }
}

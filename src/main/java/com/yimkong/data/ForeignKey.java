package com.yimkong.data;

/**
 * author yg
 * description
 * date 2019/10/12
 */
public class ForeignKey {

    private String tableName;
    private String fieldName;
    private Object defaultValue;

    public ForeignKey() {
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

}

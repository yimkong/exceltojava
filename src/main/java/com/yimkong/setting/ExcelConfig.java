package com.yimkong.setting;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * author yg
 * description
 * date 2019/10/12
 */
@XStreamAlias("excel")
public class ExcelConfig {
    //类名的位置行和列
    @XStreamAlias("nameRow")
    private int nameRow;
    @XStreamAlias("nameColumn")
    private int nameColumn;
    //属性描述行(注释)
    @XStreamAlias("explainRow")
    private int explainRow;
    //区分客户端和服务器的行
    @XStreamAlias("mark")
    private int mark;
    //属性名行
    @XStreamAlias("propertiesName")
    private int propertiesName;
    //属性类型
    @XStreamAlias("type")
    private int type;
    //代表前端读的标志
    @XStreamAlias("markFlag")
    private int markFlag;

    public int getMarkFlag() {
        return markFlag;
    }

    public int getPropertiesName() {
        return propertiesName;
    }

    public int getNameRow() {
        return nameRow;
    }

    public int getNameColumn() {
        return nameColumn;
    }

    public int getExplainRow() {
        return explainRow;
    }

    public int getMark() {
        return mark;
    }

    public int getType() {
        return type;
    }
}

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
    //注释的行
    @XStreamAlias("explainRow")
    private int explainRow;
    //区分客户端和服务器的行
    @XStreamAlias("mark")
    private int mark;
    //属性名行
    @XStreamAlias("name")
    private int name;
    //属性类型
    @XStreamAlias("type")
    private int type;
}

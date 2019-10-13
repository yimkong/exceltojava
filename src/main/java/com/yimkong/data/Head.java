package com.yimkong.data;

import java.util.List;
import java.util.Set;

public class Head {
	private String name;
	private Type type;
	private ForeignKey foreignKey;// 外键
	private Set<Long> valuesRange;// 取值范围
	private List<Head> children;
	private boolean isServer;
	private boolean isClient;
	private transient boolean language;	//是否需要生成到语言配置表
	private String comment; //描述
	private boolean isPk;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public boolean isServer() {
		return isServer;
	}

	public void setServer(boolean isServer) {
		this.isServer = isServer;
	}

	public boolean isClient() {
		return isClient;
	}

	public void setClient(boolean isClient) {
		this.isClient = isClient;
	}

	public List<Head> getChildren() {
		return children;
	}

	public void setChildren(List<Head> children) {
		this.children = children;
	}

	public ForeignKey getForeignKey() {
		return foreignKey;
	}

	public void setForeignKey(ForeignKey foreignKey) {
		this.foreignKey = foreignKey;
	}

	public Set<Long> getValuesRange() {
		return valuesRange;
	}

	public void setValuesRange(Set<Long> valuesRange) {
		this.valuesRange = valuesRange;
	}

	public boolean isLanguage() {
		return language;
	}

	public void setLanguage(boolean language) {
		this.language = language;
	}
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public boolean getIsPk() {
		return isPk;
	}

	public void setIsPk(boolean isPk) {
		this.isPk = isPk;
	}

}

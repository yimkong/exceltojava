package com.yimkong.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Table {
	private String fileName;
	private String clazzName;
	private List<Head> heads;
	private List<List<Object>> data = new ArrayList<List<Object>>();

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getClazzName() {
		return clazzName;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}

	public List<Head> getHeads() {
		return heads;
	}

	public void setHeads(List<Head> heads) {
		this.heads = heads;
	}

	public void addData(List<Object> data) {
		this.data.add(data);
	}

	public int getDataSize() {
		return data.size();
	}

	public Iterator<List<Object>> iterator() {
		return data.iterator();
	}

	public List<List<Object>> getData() {
		return data;
	}

	public void setData(List<List<Object>> data) {
		this.data = data;
	}

	public void trimServer() {
		Iterator<Head> it = heads.iterator();
		while (it.hasNext()) {
			Head head = it.next();
			if (!head.isServer())
				it.remove();
		}
	}
	
	public void trimClient() {
		Iterator<Head> it = heads.iterator();
		while (it.hasNext()) {
			Head head = it.next();
			if (!head.isClient())
				it.remove();
		}
	}
}
package com.yimkong.data;

import com.google.gson.JsonParser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColumnMeta {
	public String name;
	public String desc;
	public TypeEnum type;
	public Map<String, TypeEnum> subType = new HashMap<>();
	public final int cols;

	public ColumnMeta(int cols) {
		this.cols = cols;
	}

	public void parseName(String str) {
		name = str;
	}

	public void parseDesc(String str) {
		desc = str;
	}

	public void parseType(String type) {
		type = type.toLowerCase();
		this.type = TypeEnum.parse(type);
		if (this.type == null)
			throw new RuntimeException("column name:" + name + " type:" + type);
		switch (this.type) {
		case LIST:
			subType.put("", TypeEnum.parseListSubType(type));
			break;
		case MAP:
			TypeEnum[] tp = TypeEnum.parseMapSubType(type);
			subType.put("key", tp[0]);
			subType.put("value", tp[1]);
			break;
		default:
			break;
		}
	}

	private static final SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	Object parseValue(Object value) {
		try {
			switch (type) {
			case LONG:
				if (value instanceof String)
					return parseValue(TypeEnum.LONG, (String) value);
				else if (value instanceof Double)
					return (long) ((double) value);
				else
					return null;
			case INT:
				if (value instanceof String)
					return parseValue(TypeEnum.INT, (String) value);
				else if (value instanceof Double)
					return (int) ((double) value);
				else
					return null;
			case DOUBLE:
				if (value instanceof String)
					return parseValue(TypeEnum.DOUBLE, (String) value);
				else if (value instanceof Double)
					return value;
				else
					return null;
			case BOOLEAN:
				if (value instanceof String)
					return parseValue(TypeEnum.BOOLEAN, (String) value);
				else if (value instanceof Boolean)
					return value;
				else
					return null;
			case DATE:
				if (value instanceof String)
					return parseValue(TypeEnum.DATE, (String) value);
				else
					return null;
			case STRING:
				return value.toString().trim();
			case MAP:
				return toMap(value.toString());
			case LIST:
				return toList(value.toString());
			case JSON:
				return toJson(value.toString());
			default:
				return null;
			}
		} catch (Exception e) {
			throw new RuntimeException("column name:" + name, e);
		}
	}

	Map<Object, Object> toMap(String str) {
		Map<Object, Object> map = new HashMap<>();
		if (str == null || str.trim().length() == 0)
			return map;
		str = str.trim();
		TypeEnum key = subType.get("key");
		TypeEnum value = subType.get("value");
		String[] temp = str.split(",");
		for (String tp : temp) {
			String[] kv = tp.split("=");
			Object k = parseValue(key, kv[0]);
			Object v = parseValue(value, kv[1]);
			if (k == null || v == null)
				return null;
			map.put(k, v);
		}
		return map;
	}

	private List<Object> toList(String str) {
		List<Object> list = new ArrayList<>();
		if (str == null || str.trim().length() == 0)
			return list;
		str = str.trim();
		TypeEnum value = subType.get("");
		String[] temp = str.split(",");
		for (String tp : temp) {
			Object v = parseValue(value, tp);
			if (v == null)
				return null;
			list.add(v);
		}
		return list;
	}

	private String toJson(String str) {
		JsonParser parser = new JsonParser();
		try {
			parser.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return str;
	}

	private static Object parseValue(TypeEnum type, String str) {
		switch (type) {
		case LONG:
			return Long.parseLong(str);
		case INT:
			return Integer.parseInt(str);
		case DOUBLE:
			return Double.parseDouble(str);
		case BOOLEAN:
			return Boolean.parseBoolean(str);
		case DATE:
			try {
				return sd.parse(str);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		case STRING:
			return str;
		default:
			return null;
		}
	}

}

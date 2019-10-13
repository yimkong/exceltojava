package com.yimkong.data;


public enum Type {
	LONG((byte)0),
	INT((byte)1),
	SHORT((byte)2),
	BYTE((byte)3),
	BOOL((byte)4),
	FLOAT((byte)5),
	DOUBLE((byte)6),
	STRING((byte)7),
	JSON((byte)8),
	;
	
	private byte value;
	
	private Type(byte value) {
		this.value = value;
	}
	
	public static Type valueOf(byte type) {
		switch(type) {
		case 0:
			return LONG;
		case 1:
			return INT;
		case 2:
			return SHORT;
		case 3:
			return BYTE;
		case 4:
			return BOOL;
		case 5:
			return FLOAT;
		case 6:
			return DOUBLE;
		case 7:
			return STRING;
		case 8:
			return JSON;
		default:
			return STRING;
		}
	}
	
	public byte getValue() {
		return value;
	}
}
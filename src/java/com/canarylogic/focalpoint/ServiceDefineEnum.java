package com.canarylogic.focalpoint;

public enum ServiceDefineEnum {
	candidate("Alpha");
	
	private final String tableName;

	public String getTableName() {
		return tableName;
	}
	
	ServiceDefineEnum(String tableName) {
		this.tableName = tableName;
	}
}

package com.canarylogic.focalpoint;

public enum ServiceDefineEnum {
	candidate("Alpha"), //if this value is changed, maek sure EncrytionUtils.ACTION_SERVICE_MAP is also changed
	admin("User");
	
	private final String tableName;

	public String getTableName() {
		return tableName;
	}
	
	ServiceDefineEnum(String tableName) {
		this.tableName = tableName;
	}
}

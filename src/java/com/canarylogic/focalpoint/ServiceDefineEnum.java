package com.canarylogic.focalpoint;

public enum ServiceDefineEnum {
	candidate(Alpha.class), //if this value is changed, maek sure EncrytionUtils.ACTION_SERVICE_MAP is also changed
	admin(User.class);
	
	private final Class tableName;

	public Class getTableName() {
		return tableName;
	}
	
	ServiceDefineEnum(Class tableName) {
		this.tableName = tableName;
	}
	
	public static Class getTableName(String serviceName) {
		if(serviceName.equals(ServiceDefineEnum.candidate.toString() ))
			return ServiceDefineEnum.candidate.getTableName();
		else if( serviceName.equals(ServiceDefineEnum.admin.toString() ))
			return ServiceDefineEnum.admin.getTableName();	
		else
			return null;
	}
	
}

package com.canarylogic.base

class ExMessages {
	def static msgMap = [:]
	
	//System wide messagess
	static String AUTHENCIATION_FAILED = '101'
	static String INVALID_MO='102'
	
	static String CREATE_OBJECT_FAILED='103'
	static String UPDATE_OBJECT_FAILED='104'
	static {
		msgMap[AUTHENCIATION_FAILED] = "Authenciation Failed"
		msgMap[INVALID_MO]="No Record Found"
		msgMap[CREATE_OBJECT_FAILED]="Create Object Failed"
		msgMap[UPDATE_OBJECT_FAILED]="Updte Object Failed"
	}
	
}

package com.canarylogic.base

class ExMessages {
	def static msgMap = [:]
	
	//System wide messagess
	static String AUTHENCIATION_FAILED = '101'
	static String INVALID_MO='102'
	
	static String CREATE_OBJECT_FAILED='103'
	static String UPDATE_OBJECT_FAILED='104'
	static String LIST_OBJECT_FAILED='105'
	static String DELETE_OBJECT_FAILED='105'
	
	
	static {
		msgMap[AUTHENCIATION_FAILED] = "Authenciation Failed"
		msgMap[INVALID_MO]="No Record Found"
		msgMap[CREATE_OBJECT_FAILED]="Create Object Failed"
		msgMap[UPDATE_OBJECT_FAILED]="Update Object Failed"
		msgMap[LIST_OBJECT_FAILED]="List Object Failed"
		msgMap[DELETE_OBJECT_FAILED]="Delete Object Failed"
	}
	
}

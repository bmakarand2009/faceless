package com.canarylogic.base

class ExMessages {
	def static msgMap = [:]
	
	//System wide messagess
	static String AUTHENCIATION_FAILED = '101'
	static String INVALID_MO='102'
	
	static {
		msgMap[AUTHENCIATION_FAILED] = "Authenciation Failed"
		msgMap[INVALID_MO]="No Record Found"
	}
	
}

package com.canarylogic.base

import com.canarylogic.focalpoint.ServiceDefineEnum;
class TestConfig {
	static String CANARY_APP_ID="foc-harvest"
	static String CANARY_USER="mark@harvest.com"
	
	static String EXISTING_GRP="admin"
	static String EXISTING_ROLE="admin"

	public static void setCommonParams(def mockParams,def mockRequest){
		mockParams.userId = CANARY_USER
		mockParams.applicationId = CANARY_APP_ID
		mockParams.timestamp = new Date().time
		mockParams.controller ="user"
		mockParams.action ="createGroup"
		mockParams.service = ServiceDefineEnum.candidate.toString()
		mockParams.signature = "mysingatur"
		mockRequest.contentType = "application/xml"
	}
}

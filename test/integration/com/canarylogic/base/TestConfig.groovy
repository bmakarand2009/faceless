package com.canarylogic.base
import com.canarylogic.focalpoint.utils.EntityConvertor
class TestConfig {
	static String CANARY_APP_ID="canary-test-123"
	static String CANARY_USER="mark@harvest.com"
	
	static String EXISTING_GRP="admin"
	static String EXISTING_ROLE="admin"

	public static void setCommonParams(def mockParams,def mockRequest){
		mockParams.userId = CANARY_USER
		mockParams.applicationId = CANARY_APP_ID
		mockParams.timestamp = new Date().time
		mockParams.controller ="user"
		mockParams.action ="createGroup"
		mockParams.service = EntityConvertor.CAND_SERVICE
		mockParams.signature = "mysingatur"
		mockRequest.contentType = "application/xml"
	}
}

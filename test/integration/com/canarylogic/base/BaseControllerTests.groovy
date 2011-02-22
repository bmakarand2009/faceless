package com.canarylogic.base

import com.canarylogic.focalpoint.*
import com.canarylogic.focalpoint.utils.EncryptionUtils;

import grails.test.ControllerUnitTestCase;

class BaseControllerTests  extends ControllerUnitTestCase {
	def authService
	
	static String CANARY_APP_ID="foc-canary"
	static String CANARY_USER="mark@canary.com"
	
	 protected void setUp() {
		super.setUp()
		mockLogging(BaseController,true)
		controller.authService = authService
	}
	 
	 protected void tearDown() {
		 super.tearDown()
	 }
	 
	 private void setCommonParams(){
		 mockParams.userId = CANARY_USER
		 mockParams.applicationId = CANARY_APP_ID		
		 mockParams.timestamp = new Date().time
		 mockParams.controller ="base"
		 mockParams.action ="auth"
		 mockParams.service = ServiceDefineEnum.CANDIDATE_SERVICE
		 mockParams.signature = calcSignature()
		 mockRequest.contentType = "application/xml"
	  }
	 
	 void testAuth() {
		 setCommonParams()
		 def isAuth = controller.auth()
		 if(!isAuth) {
			 def xmlResp = controller.response.getContentAsString()
			 assert xmlResp == "myhello"
		 }else
		 	assert isAuth == true
	 }
	 
	 
	 private String calcSignature() {
		User curUser = User.findUser(mockParams.userId, mockParams.applicationId)
		String secKey = curUser.password
		String sigUrl = EncryptionUtils.getSignatureUrl(mockParams)
		String signature = EncryptionUtils.calcSignature(sigUrl, secKey)
	 	return signature
	 }
	 


}

package com.canarylogic.base

import com.canarylogic.focalpoint.*
import com.canarylogic.focalpoint.utils.EncryptionUtils;
import com.canarylogic.focalpoint.utils.EntityConvertor;
import grails.test.ControllerUnitTestCase;

class BaseControllerTests  extends ControllerUnitTestCase {
	def authService
	
	static String CANARY_APP_ID="foc-harvest"
	static String CANARY_USER="mark@harvest.com"
	
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
		 mockParams.service = EntityConvertor.CAND_SERVICE
		 mockParams.signature = calcSignature()
		 mockRequest.contentType = "application/xml"
	  }
	 
	 void testAuth() {
		 try {
			 setCommonParams()
		 }catch(RestException ex) {
		 	throw new Exception("$ex.message $ex.additionalInfo")
		 }
		 def isAuth = controller.auth()
		 if(!isAuth) {
			 def xmlResp = controller.response.getContentAsString()
			 assert xmlResp == "myhello"
		 }else
		 	assert isAuth == true
	 }
	 
	 
	 private String calcSignature() {
		User curUser = User.findUser(mockParams.userId, mockParams.applicationId)
		assertNotNull curUser
		String secKey = curUser.password
		String sigUrl = EncryptionUtils.getSignatureUrl(mockParams)
		String signature = EncryptionUtils.calcSignature(sigUrl, secKey)
	 	return signature
	 }
	 


}

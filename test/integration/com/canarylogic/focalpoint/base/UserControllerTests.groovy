package com.canarylogic.focalpoint.base

import grails.test.ControllerUnitTestCase;
import com.canarylogic.focalpoint.utils.EncryptionUtils;
import com.canarylogic.focalpoint.ServiceDefineEnum
import com.canarylogic.focalpoint.*

//grails test-app UserController
class UserControllerTests extends ControllerUnitTestCase {
	def authService
	
	static String CANARY_APP_ID="foc-harvest"
	static String CANARY_USER="mark@harvest.com"
	
	static String EXISTING_GRP="admin"
	
	protected void setUp() {
		super.setUp()
		controller.authService = authService
		mockLogging(UserController,true)
	}
	 
	 protected void tearDown() {
		 super.tearDown()
	 }
	 
	 private void setCommonParams(){
		 mockParams.userId = CANARY_USER
		 mockParams.applicationId = CANARY_APP_ID
		 mockParams.timestamp = new Date().time
		 mockParams.controller ="user"
		 mockParams.action ="createGroup"
		 mockParams.service = ServiceDefineEnum.candidate.toString()
		 mockParams.signature = "mysingatur"
		 mockRequest.contentType = "application/xml"
	 }
	 
	 void testCreateGroup() {
		 setCommonParams()
		 def grpName = "grp${new Date().time}"
		 mockParams.grpName =grpName
		 mockParams.grpLabel ="this is a test group"
		 controller.createGroup()
		 def xmlResp = controller.response.getContentAsString()
		 def cParser = new XmlParser().parseText(xmlResp)
		 assert cParser.groupName.text() == grpName
	 }

	 void testAssignGroup() {
		 //Create a User 
		 def c1 =  Client.findByOrgId(CANARY_APP_ID)
		 String user1="trial@gmail.com"
		 User u1 = new User(username:user1,password:"abcd")
		 u1.parent = c1
		 assertNotNull u1.save()
		 
		 mockParams.userName = user1
		 mockParams.grpName  = EXISTING_GRP
		 controller.assignUsersToGroup()
		 def xmlResp = controller.response.getContentAsString()
		 def cParser = new XmlParser().parseText(xmlResp)
		 assert cParser.groupName.text() == EXISTING_GRP
		 
	 }
	 
	 
	 
}

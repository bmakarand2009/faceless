package com.canarylogic.focalpoint.base



import grails.test.ControllerUnitTestCase;


import com.canarylogic.focalpoint.utils.EntityConvertor;
import com.canarylogic.focalpoint.*
import com.canarylogic.base.TestConfig
import com.canarylogic.sing.Client;
//grails test-app UserController
class UserControllerTests extends ControllerUnitTestCase {
	def authService
	
	static String CANARY_APP_ID="foc-harvest"
	static String CANARY_USER="mark@harvest.com"
	
	static String EXISTING_GRP="admin"
	static String EXISTING_ROLE="admin"
	
	protected void setUp() {
		super.setUp()
		controller.authService = authService
		mockLogging(UserController,true)
	}
	 
	 protected void tearDown() {
		 super.tearDown()
	 }
	 
	 private void setCommonParams(){
		 TestConfig.setCommonParams(mockParams,mockRequest)
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

	 void testCreateRole() {
		 setCommonParams()
		 def roleName = "role${new Date().time}"
		 mockParams.roleName =roleName
		 mockParams.roleLabel ="this is a test role label"
		 controller.createRole()
		 def xmlResp = controller.response.getContentAsString()
		 def cParser = new XmlParser().parseText(xmlResp)
		 assert cParser.roleName.text() == roleName
	 }
	 
	 void testAssignGroup() {
		 //Create a User 
		 def user1 = createTestUser()
		 mockParams.userName = user1
		 mockParams.grpName  = EXISTING_GRP
		 controller.assignUsersToGroup()
		 def xmlResp = controller.response.getContentAsString()
		 def cParser = new XmlParser().parseText(xmlResp)
		 assert cParser.groupName.text() == EXISTING_GRP
		 
	 }
	 
	 void testAssignRoleToUser() {
		 def user1 = createTestUser()
		 mockParams.userName = user1
		 mockParams.roleName = EXISTING_ROLE
		 controller.assignRoleToUser()
		 def xmlResp = controller.response.getContentAsString()
		 def cParser = new XmlParser().parseText(xmlResp)
		 assert cParser.roleName.text() == EXISTING_ROLE
	}
	 
	void testAddServicePrivToRole() {
		 def user1 = createTestUser()
		 mockParams.roleName = EXISTING_ROLE
		 mockParams.serviceName = EntityConvertor.CAND_SERVICE
		 mockParams.isCreate = false
		 mockParams.isUpdate = false
		 
		 controller.addServicePrivToRole()
		 def xmlResp = controller.response.getContentAsString()
		 def cParser = new XmlParser().parseText(xmlResp)
		 assert cParser.serviceName.text() == EntityConvertor.CAND_SERVICE
	}
	 
	 private User createTestUser() {
		 def c1 =  Client.findByOrgId(CANARY_APP_ID)
		 String user1="trial@gmail.com"
		 User u1 = new User(username:user1,password:"abcd")
		 u1.parent = c1
		 assertNotNull u1.save()
		 return u1
	 }
	 
}

package com.canarylogic.focalpoint

import grails.test.*

class OrganizationMoTests extends GrailsUnitTestCase {
	
	def transactional = false
	def sessionFactory
	
	
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }
	
	
/*
    void testSetupOrg() {		
		String testOrgId = "foc-12345",orgName='canaryorg'		
		String grp1Name = "admin",  grp2Name = "marketing"
		
		OrganizationMo orgMo = new OrganizationMo(orgName:orgName, orgId:testOrgId)
		assertNull orgMo.groups
		assertNull orgMo.roles
		orgMo.save()
		
		//add groups
		orgMo.groups = []
		GroupMo adminGroup =  new GroupMo(grpName:grp1Name,grpDesc:"sampledesc")
		GroupMo mkgGroup   =  new GroupMo(grpName:grp2Name,grpDesc:"sample mkg group")
		orgMo.groups << adminGroup
		orgMo.groups << mkgGroup
		
		orgMo.groups.each { it.parent = orgMo }
	
		//add roles
		orgMo.roles = []
		RoleMo adminRole = new RoleMo(roleName:"admin",isAccess:true,isDelete:true,isUpdate:true,isSelfGroup:false,roleDesc:"sample Desc") 
		orgMo.roles <<  adminRole
		RoleMo recRole = new RoleMo(roleName:"recruiter",isAccess:true,isDelete:true,isUpdate:true,isSelfGroup:true,roleDesc:"sample desc") 
		orgMo.roles << recRole
		orgMo.roles.each { it.parent = orgMo }
			
		//add Users
		UserMo u1 = new UserMo(userName:"mark@harvestinfotech.com",password:"admin123")
		UserMo u2 = new UserMo(userName:"anu@harvestinfotech.com",password:"admin123")
		
		adminRole.users =[]
		adminRole.users << u1
		
//		adminGroup.addToUsers(u1)
//		mkgGroup.addToUsers(u2)
		
		assertNotNull orgMo.save(flush:true)
		assertEquals 2, orgMo.roles.size()
		assertEquals 2, orgMo.groups.size()

		sessionFactory.currentSession.clear()
		//Find All Users by orgId
		def myOrgMo = OrganizationMo.findByOrgId(testOrgId)
		assertNotNull myOrgMo
		
//		def myGroupList = myOrgMo.groups
//		assertEquals 2,myGroupList.size()
		
//		def myUserList = myOrgMo.groups*.users
//		assertEquals 2, myUserList.size()
//		String userNames=""
//		String roleNames=""
//		myUserList.each{
//			String userName = it.userName
//			String roleName  = it.role.roleName
//			userNames="$userName $userNames"
//			roleNames="$roleName $roleNames"
//		}
//		assert userNames.startsWith("mark")
//		
		
    }
    */
}

package com.canarylogic.focalpoint

import grails.test.*

class OrganizationMoTests extends GrailsUnitTestCase {
	
	def transactional = true
	def sessionFactory
	
	
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

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
//		orgMo.groups << new GroupMo(grpName:grp1Name,grpDesc:"sampledesc")
//		orgMo.groups << new GroupMo(grpName:grp2Name,grpDesc:"sample mkg group")
		orgMo.groups << adminGroup
		orgMo.groups << mkgGroup
		
		orgMo.groups.each { it.parent = orgMo }
	
		//add roles
		orgMo.roles = []
		orgMo.roles << new RoleMo(roleName:"admin",isAccess:true,isDelete:true,isUpdate:true,isSelfGroup:false,roleDesc:"sample Desc")
		orgMo.roles << new RoleMo(roleName:"recruiter",isAccess:true,isDelete:true,isUpdate:true,isSelfGroup:true,roleDesc:"sample desc")
		orgMo.roles.each { it.parent = orgMo }
			
		//add Users
		UserMo u1 = new UserMo(userName:"mark@harvestinfotech.com",password:"admin123")
		UserMo u2 = new UserMo(userName:"anu@harvestinfotech.com",password:"admin123")
		
		adminGroup.addToUsers(u1)
		mkgGroup.addToUsers(u2)
		
		assertNotNull orgMo.save(flush:true)
		assertEquals 2, orgMo.roles.size()
		assertEquals 2, orgMo.groups.size()
		
    }
}

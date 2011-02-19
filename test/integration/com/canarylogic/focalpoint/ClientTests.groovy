package com.canarylogic.focalpoint

import grails.test.*

class ClientTests extends GrailsUnitTestCase {
	
	def transactional = false
	def sessionFactory
	
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testSetupClient() {
		
		String c1OrgId = "foc-1000",c1OrgName='canary1'
		String c2OrgId = "foc-2000",c2OrgName='canary2'
		String grp1Name = "admin",  grp2Name = "marketing"
		Client c1 = new Client(orgName:c1OrgName, orgId:c1OrgId)
		assertNull c1.groups
		assertNull c1.roles
		c1.save()
		
		Client c2 = new Client(orgName:c2OrgName, orgId:c2OrgId)
		assertNull c2.groups
		assertNull c2.roles
		c2.save()
		
		
		//Add roles to C1 and C2		
		//Create Roles for Organization
		sessionFactory.currentSession.clear()
		Role adminRole1 = new Role(roleName:"admin",isAccess:true,isDelete:true,isUpdate:true,isSelfGroup:false,label:"sample Desc")		
		Role recRole1 = new Role(roleName:"recruiter",isAccess:true,isDelete:true,isUpdate:true,isSelfGroup:true,label:"sample desc")
		assertNotNull adminRole1.addRole (c1OrgId)
		assertNotNull recRole1.addRole (c1OrgId)

		Role adminRole2 = new Role(roleName:"admin",isAccess:true,isDelete:true,isUpdate:true,isSelfGroup:false,label:"sample Desc")
		Role recRole2 = new Role(roleName:"recruiter",isAccess:true,isDelete:true,isUpdate:true,isSelfGroup:true,label:"sample desc")
		assertNotNull adminRole2.addRole (c2OrgId)
		assertNotNull recRole2.addRole (c2OrgId)
		
		sessionFactory.currentSession.clear()		
		c1 =  Client.findByOrgId(c1OrgId)
		c2 = Client.findByOrgId(c2OrgId)
		assertEquals 2,c1.roles.size()
		assertEquals 2,c2.roles.size()
		
		//Create Groups
		sessionFactory.currentSession.clear()
		Groups adminGroup1 =  new Groups(grpName:grp1Name,grpLabel:"c1 admin")
		Groups mkgGroup1   =  new Groups(grpName:grp2Name,grpLabel:"c1 mkg")
		Groups adminGroup2 =  new Groups(grpName:grp1Name,grpLabel:"c2 admin")
		Groups mkgGroup2   =  new Groups(grpName:grp2Name,grpLabel:"c2 mkg")
		assertNotNull adminGroup1.addGroup (c1OrgId)
		assertNotNull mkgGroup1.addGroup (c1OrgId)
		assertNotNull adminGroup2.addGroup (c2OrgId)
		assertNotNull mkgGroup2.addGroup (c2OrgId)
		
		sessionFactory.currentSession.clear()
		c1 =  Client.findByOrgId(c1OrgId)
		c2 = Client.findByOrgId(c2OrgId)
		assertEquals 2,c1.groups.size()
		assertEquals 2,c2.groups.size()
		
		//Add Users to Groups
		String user1="mark@gmail.com"
		User u1 = new User(username:user1,password:"abcd")
		u1.groups = []
		assertNotNull u1.save()
		sessionFactory.currentSession.clear()
		
		def myAdminGroup1 = Groups.findByGrpName(grp1Name)		
		u1 = User.findByUsername(user1)
		myAdminGroup1.addToUsers(u1)
		assertEquals 1, myAdminGroup1.users.size()
		assertNotNull myAdminGroup1.save(flush:true)
		
		sessionFactory.currentSession.clear()
		myAdminGroup1 = Groups.findByGrpName(grp1Name)
		assertEquals 1,myAdminGroup1.users.size()
		
    }
}

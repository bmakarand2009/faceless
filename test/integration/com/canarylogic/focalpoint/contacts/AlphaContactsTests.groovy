package com.canarylogic.focalpoint.contacts

import grails.test.*

import com.canarylogic.focalpoint.*

class AlphaContactsTests extends GrailsUnitTestCase {
	
	def transactional = false
	def sessionFactory
	
	
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testContacts() {
		String c1OrgId = "foc-3000",c1OrgName='canary20'
		String c2OrgId="foc-4000",c2OrgName='canary30'
		Client c1 = new Client(orgName:c1OrgName, orgId:c1OrgId)
		assertNull c1.groups
		assertNull c1.roles
		c1.save()
		
		sessionFactory.currentSession.clear()
		String grp1Name = "myadmin"
		Groups adminGroup1 =  new Groups(grpName:grp1Name,grpLabel:"c1 admin")
		assertNotNull adminGroup1.addGroup (c1OrgId)
		
		sessionFactory.currentSession.clear()
		def clientA = Client.findByOrgId(c1OrgId)
		def alpha = new AlphaContacts(emailAddr:"abc@gmail.com",sourceType:"WEBSITE",createdBy:"user1",updatedBy:"heloo1")
		alpha.parent = clientA
		alpha.owner = adminGroup1
//		assertNotNull alpha.save(flush:true)
		alpha.validate()
		def val=""
		if(alpha.hasErrors()) {
			alpha.errors.allErrors.each { val="$val$it"}
			assert val == "hello"
		}else {
			assertNotNull alpha.save(flush:true)
		}
		
		
		Client clientB = new Client(orgName:c2OrgName, orgId:c2OrgId)
		assertNull clientB.groups
		assertNull clientB.roles
		
		clientB.save()
		String grp2Name = "myadmin2"
		Groups adminGroup2 =  new Groups(grpName:grp2Name,grpLabel:"c1 admin")
		assertNotNull adminGroup2.addGroup (c2OrgId)

		sessionFactory.currentSession.clear()
		
		def alphaB = new AlphaContacts(emailAddr:"abc@gmail.com",sourceType:"WEBSITE",createdBy:"user1",updatedBy:"heloo1")
		alphaB.parent = clientB
		alphaB.owner = adminGroup2
		assertNotNull alphaB.save(flush:true)

		sessionFactory.currentSession.clear()
		def alphaC = new AlphaContacts(emailAddr:"abc@gmail.com",sourceType:"WEBSITE",createdBy:"user3",updatedBy:"heloo1")
		alphaC.parent = clientB
		alphaC.owner = adminGroup2
		
		assertNull alphaC.save(flush:true)
		
		
		
		
		
		
    }
}

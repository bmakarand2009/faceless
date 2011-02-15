package com.canarylogic.focalpoint

import grails.test.*

class FocalAdminServiceTests extends GrailsUnitTestCase {
	def focalAdminService
	def transactional = false
    protected void setUp() {
        super.setUp()
		mockLogging(FocalAdminService,true)
    }

    protected void tearDown() {
        super.tearDown()
    }

   void testServiceMethod() {
		String result = focalAdminService.serviceMethod()
		assert result == "passed"
	}
   
   void testSetupOrg() {
	   def testOrgId = "foc-12345"
	   def testGrp1Name = "Admin"
	   //create Org
//	   def orgParams=[orgName:"canaryorg", orgId:testOrgId]
//	   def orgMo = focalAdminService.setupOrganization(orgParams)
//	   assert orgMo.orgId == testOrgId
	   
	   //Add Groups
	   def grpParams = [grpName:testGrp1Name,groupDesc:"AdminGroup Desc"]
	   focalAdminService.createGroup(testOrgId,grpParams)
//	   
//	   def grpMo = focalAdminService.getGroup(testOrgId,testGrp1Name)	   
//	   assert grpMo.grpName == testGrp1Name
//	   assert grpMo.organization.orgId == testOrgId
	   
   }
}

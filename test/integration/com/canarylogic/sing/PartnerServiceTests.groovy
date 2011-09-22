package com.canarylogic.sing

import com.canarylogic.focalpoint.Client
import org.junit.*
import grails.test.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
class PartnerServiceTests extends GrailsUnitTestCase {
	
	def partnerService
	static String CANARY_APP_ID="canary-test-123"
	static String FOC_HARVEST_APP_ID="foc-harvest"
	
	
	protected void setUp() {
		super.setUp()
		mockLogging(PartnerService,true)
		
	}
	void testCreatePartner() {
		def params=[suffix:"Mr",firstName:"john",lastName:"martin",createdBy:"testUser",updatedBy:"testUser"]
		def client = Client.findByOrgId(CANARY_APP_ID)
		assert client!= null
		String streetName = "statebridge road"
		ContactAddress aContactAddress = new ContactAddress(street:streetName,city:"alpharetta")
		def contactAddressList = [aContactAddress]
		String emailType = 'email'
		ContactDetails aContactDetails = new ContactDetails(contactType:emailType,contactValue:'hello@abc.com',category:'home')
		def contactDetailsList = [aContactDetails]
		
		def aPartner = partnerService.createContact(params,client,contactAddressList,contactDetailsList)
		assertNotNull aPartner
		def aList = aPartner.addressList
		assert aList.size() == 1
		aList.each{
			assert it.street == streetName
		}
		def cList = aPartner.contactDetailsList
		assert cList.size() ==1
		cList.each{
			assert it.contactType == emailType
		}
		
		
		//get partner
		def aContact = Contact.findBy
    }
}

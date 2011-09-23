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
		def client = Client.findByOrgId(CANARY_APP_ID)
		assert client!= null
		String streetName = "statebridge road"
				
		def resultMap = createContactObjectParams("john","martin","john.martin@ipilong.com",streetName,"alpharetta")
		def contactAddressList = resultMap.contactAddresses
		contactAddressList.each{
			log.debug it.street
		}
		def aPartner = partnerService.createContact(resultMap.paramsMap,client,contactAddressList,resultMap.contactDetailsList)
		assertNotNull aPartner
		assertNotNull aPartner.dateCreated
		assertNotNull aPartner.contactAddresses
		
		def aList = aPartner.contactAddresses
		assert aList.size() == 1
		aList.each{
			assertNotNull it.id
		}
		def cList = aPartner.contactDetailsList
		assert cList.size() ==1
		cList.each{
			assertNotNull it.id
		}
    }
	
	private def createContactObjectParams(String firstNameStr, String lastNameStr,String emailStr,String streetName,String cityName){
		ContactAddress aContactAddress = new ContactAddress(street:streetName,city:cityName)
		def contactAddressList = [aContactAddress]
		
		String emailType = 'email'
		ContactDetails aContactDetails = new ContactDetails(contactType:emailType,contactValue:emailStr,category:'home')
		def contactDetailsList = [aContactDetails]
		
		def paramsMap=[suffix:"Mr",firstName:firstNameStr,lastName:lastNameStr,createdBy:"testUser",updatedBy:"testUser"]
		
		def resultMap=[:]
		resultMap.contactAddresses = contactAddressList
		resultMap.contactDetailsList = contactDetailsList
		resultMap.paramsMap = paramsMap
		resultMap.emailType = emailType
		return resultMap
	}
}

package com.canarylogic.sing

import com.canarylogic.focalpoint.Client
import org.junit.*
import grails.test.*

/**
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
		def client1 = Client.findByOrgId(CANARY_APP_ID)
		assert client1!= null
		
//		def client2 = Client.findByOrgId(FOC_HARVEST_APP_ID)
//		assert client2!= null
		String streetName = "statebridge road"
				
		def resultMap = createContactObjectParams("john","martin","john.martin@ipilong.com",streetName,"alpharetta")
		def contactAddressList = resultMap.contactAddresses
		contactAddressList.each{
			log.debug it.street
		}
		def aPartner = partnerService.createContact(client1,resultMap.paramsMap,contactAddressList,resultMap.contactDetailsList)
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
		
		//create one more record for same client to see it fails
		def resultMap1 = createContactObjectParams("john1","martin","john.martin@ipilong.com",streetName,"alpharetta")
		def aPartner1 = partnerService.createContact(client1,resultMap1.paramsMap,resultMap1.contactAddresses,resultMap1.contactDetailsList)
		assertNotNull aPartner1.id
		
//		def resultMap2 = createContactObjectParams("john","martin","john.martin@ipilong.com",streetName,"alpharetta")
//		def aPartner2 = partnerService.createContact(client2,resultMap2.paramsMap,resultMap2.contactAddresses,resultMap2.contactDetailsList)
//		assertNotNull aPartner2
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

package com.canarylogic.sing
import com.canarylogic.focalpoint.Client
class PartnerService {

    def serviceMethod() {

    }
	
	def createContact(def paramsMap,Client parent,def addressList, def contactDetailsList=null){
		Contact c = new Contact(paramsMap)
		c.parent = parent
		if(addressList)
			c.addressList = addressList
		if(contactDetailsList)
			c.contactDetailsList = contactDetailsList
		c.save(failOnError: true)
	}
	
}

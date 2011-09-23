package com.canarylogic.sing
import com.canarylogic.focalpoint.Client
class PartnerService {

    def serviceMethod() {

    }
	
	def createContact(def paramsMap,Client parent,def addressList, def contactDetailsList=null){
		
		
		Contact c = new Contact(paramsMap)
		c.parent = parent

		if(addressList){
			c.contactAddresses = addressList
			c.contactAddresses.each{
				it.contact = c
			}
		}
		if(contactDetailsList){
			c.contactDetailsList = contactDetailsList
			c.contactDetailsList.each{
				it.contact = c
			}
		}
		c.save(failOnError:true)
		return c
	}
	
}

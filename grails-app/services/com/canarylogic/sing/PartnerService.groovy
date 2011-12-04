package com.canarylogic.sing
import com.canarylogic.focalpoint.Client
class PartnerService {

    def serviceMethod() {

    }
	
	def listRecords(Client parent, Object domainName, def params){
		def tableClass = domainName.class
		String orderType=params.order?params.String(order):'asc'
		def max = params.max?params.max:10
		def offset = params.offset?params.offset:0
		String orderField = params.orderField?params.String(orderField):'id'
		
		def resultList = tableClass.withCriteria {
			maxResults(max)
			firstResult(offset)
			order(orderField,orderType)
			and{
				eq('parent', parent)
			}
//			or {
//				searchParamsMap.each{k,v->
//					like(k,"%$v%")
//				}
//			 }
        }
        return resultList
		
	}
	def createContact(Client parent,def paramsMap,def addressList, def contactDetailsList=null){
		
		
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

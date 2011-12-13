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
//				params.each{k,v->
//					like(k,"%$v%")
//				}
//			 }
        }
        return resultList
		
	}
	def createContact(Client parent,def paramsMap,def addressList, def contactDetailsList=null){
    	Person c = new Person(paramsMap)
		c.parent = parent

		if(addressList){
			c.contactAddressList = addressList
			c.contactAddressList.each{
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

    def create(Client parent,def createXmlBody){
        def proot = new XmlParser().parseText(createXmlBody)
        def personObj = pxmlClosure(proot)
        return personObj
    }


    def domainObjectsMap=[person:new Person(), address:new ContactAddress()]

    def pxmlClosure = { pRoot ->
         def xmlMap=[:]
         def domainObj = domainObjectsMap."${pRoot.name()}"
         println "domain class is ${domainObj.class.name}"

         def xmlElementsMap = domainObj.XML_ELEMENT_MAP
         xmlElementsMap.each{k,v->
            def childElem = pRoot."$k"[0]
            if(childElem){
                def childElemName = childElem.name()
                def childElemVal = childElem.value()[0]
                String aType = childElem.attribute("type")
                if(!aType){
                   if(childElemName.endsWith("_list")){
                       def xmlListObj=[]
                       childElem.children().each{ it ->
                          xmlListObj<<pxmlClosure(it)
                       }
                      xmlMap."$v" = xmlListObj
                   }else
                     xmlMap."$v" = childElemVal
                }
                else if(aType=="Integer") xmlMap."$v" = childElemVal.toInteger()
                else if(aType=="datetime")xmlMap."$v" = new Date().parse("yyyy-M-d H:m:s",childElemVal.toString())

            }
         }
         def aObj = domainObj.createObj(xmlMap)
        return aObj
     }

	
}

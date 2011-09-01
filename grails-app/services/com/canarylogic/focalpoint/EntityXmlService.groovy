package com.canarylogic.focalpoint

import com.canarylogic.focalpoint.utils.*;
import com.canarylogic.base.RestException
import com.canarylogic.base.ExMessages

class EntityXmlService {

    static transactional = false
	
	def aznS3Service
	
	def podsFile = {
		aznS3Service.getPodContent(it)
	}
	
//	def convertEntityListToXml(def entityList, String applicationId, String serviceName) {
//		String aPod = podsFile(applicationId)
//		EntityConvertor.convertEntityListToXml(entityList, aPod, serviceName)
//	}
//		
//
	def getDomainClass(String applicationId, String serviceName ) {
		String aPod = podsFile(applicationId)
		EntityConvertor.getDomainClass(aPod, serviceName)
	}

		
	def String findOrlistRecords(def params){
		String aPod = podsFile(params.applicationId)
		def tableClass = EntityConvertor.getDomainClass(aPod, params.service)
		if(!tableClass) throw new RestException(ExMessages.LIST_OBJECT_FAILED,"No Domain  found for $params.service")
		
		def searchParamsMap = convertToEntityMap(params,aPod, params.service)
		def client = Client.findByOrgId(params.applicationId)
		if(!client) throw new RestException(ExMessages.LIST_OBJECT_FAILED,"Invalid applicationI $params.applicationId")
		String orderField = params.orderField?params.String(orderField):'id'
		String orderType=params.order?params.String(order):'asc'

		def resultList = tableClass.withCriteria {
			maxResults(params.max)
			firstResult(params.offset)
			order(orderField,orderType)
			and{
				eq('parent', client)
			}
			or {
				searchParamsMap.each{k,v->
					like(k,"%$v%")
				}
			 }
		}
		String xmlResp = EntityConvertor.convertEntityListToXml(resultList, aPod, params.service)
		return xmlResp
	}
	
	def String createRecord(def params){
		//check if mandatory params are present
		log.debug "Create Record  params recieved are $params"
		String xmlResp=null;
		String aPod = podsFile(params.applicationId)
		def tableClass = EntityConvertor.getDomainClass(aPod, params.service)
		//log.debug "tableClass received is $tableClass"
		def tableParams = convertToEntityMap(params,aPod, params.service)
		//log.debug "converted tableParams are $tableParams"
		def client = Client.findByOrgId(params.applicationId)
		if(!client) throw new RestException(ExMessages.LIST_OBJECT_FAILED,"Invalid applicationI $params.applicationId")
		tableParams.put("parent",client)
		
		def tableInst = tableClass.newInstance()
		tableInst.properties = tableParams
		
		if (!tableInst.hasErrors() && tableInst.save(flush: true)) {
			xmlResp = EntityConvertor.convertEntityToXml(tableInst, aPod, params.service)
			log.debug "$xmlResp"
			log.debug "new tableInst createed"
		}else{
			tableInst.errors.each {
				log.debug it
				xmlResp = it
		   }
		}
		return xmlResp	
	}
	
	def String updateRecord(def params){
		String xmlResp=null;
		String aPod = podsFile(params.applicationId)
		def tableClass = EntityConvertor.getDomainClass(aPod, params.service)
		def tableInst = tableClass.get(params.id)
		if(!tableInst)throw new RestException(ExMessages.UPDATE_OBJECT_FAILED,"No Record found for $params.service and $params.id")
		def tableParams = convertToEntityMap(params,aPod, params.service)
		tableInst.properties = tableParams
		def updatedInst = tableInst.save()
		if(updatedInst) {
			xmlResp = EntityConvertor.convertEntityToXml(updatedInst, aPod, params.service)
		}
		return xmlResp
	}
	
	def String getRecord(def params){
		String xmlResp = null
		String aPod = podsFile(params.applicationId)
		def tableClass = EntityConvertor.getDomainClass(aPod, params.service)
		def tableInst = tableClass.get(params.id)
		if(tableInst) 
			xmlResp = EntityConvertor.convertEntityToXml(tableInst, aPod, params.service)
		return xmlResp	
	}
	
	
	
	/////////////////////////
	///Following methods are private and static, copied form EntityConvertor, slowly
	//we will move metods from EntityConvertor to this class to avoid confusion of two classes
	
	
	/*
	* @param attribMap = [firstName:"john", lastName="martin"]
	* @param serviceName e.g "candidateService
	* @clientParser : new XmlParser().parseText(xml)
	* @return [c1:john,c2:martin]
	*
	*/
   
    def convertToEntityMap(def attribMap, String aPod, String serviceName) {
	   //find the domainObject
	  // log.debug "convertToEntityMap called attribMap is $attribMap"
	   def clientParser = new XmlParser().parseText(aPod)
	   
	   def resultAttribMap=[:]
	   def serviceRecord = clientParser.service.find{it.@name == serviceName }
	   //log.debug "service record found $serviceRecord"
	   def  entityMapping = serviceRecord.entityMapping[0]
	   def columnMap = [:]
	   entityMapping.column.each{
		  columnMap.put(it.@alias,it.@name)
	   }
	  // log.debug "columnMap value is $columnMap"
	   String entityName = entityMapping.@name
	   
	   def viewMappingRecord = serviceRecord.viewMapping[0]
	   
	   //log.debug "start looping for viewMapping Records $viewMappingRecord"
	   viewMappingRecord.attribute.each{
		  String nodeName = it.@nodeName
		 // log.debug "finding mapping for $nodeName"
		  if(columnMap[nodeName] != null &&  attribMap[nodeName] != null){
			  resultAttribMap.put(columnMap[nodeName], attribMap[nodeName])
		   }
		  //log.debug "going for next nodeName"
	   }
        log.debug "convertToEntityMap  for service $serviceName returned $resultAttribMap"
	   return resultAttribMap
   }
   
   
   

}

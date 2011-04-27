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
//	
//	def convertToEntityMap(def attribMap, String applicationId, String serviceName) {
//		EntityConvertor.convertToEntityMap(attribMap,podsFile(applicationId), serviceName)		
//	}
//	
//	def String convertEntityToXml(def aEntity, String applicationId, String serviceName) {
//		EntityConvertor.convertEntityToXml(aEntity, podsFile(applicationId), serviceName)
//	}
//	
		
	def String findOrlistRecords(def params){
		String aPod = podsFile(params.applicationId)
		def tableClass = EntityConvertor.getDomainClass(aPod, params.service)
		if(!tableClass) throw new RestException(ExMessages.LIST_OBJECT_FAILED,"No Domain  found for $params.service")
		
		def searchParamsMap = EntityConvertor.convertToEntityMap(params,aPod, params.service)
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
		def tableParams = EntityConvertor.convertToEntityMap(params,aPod, params.service)
		def tableInst = tableClass.newInstance()
		tableInst.properties = tableParams
		def tableNewInst= tableInst.save()
		if(tableNewInst!=null)
			xmlResp = EntityConvertor.convertEntityToXml(tableNewInst, aPod, params.service)
		return xmlResp	
	}
	
	def String updateRecord(def params){
		String xmlResp=null;
		String aPod = podsFile(params.applicationId)
		def tableClass = EntityConvertor.getDomainClass(aPod, params.service)
		def tableInst = tableClass.get(params.id)
		if(!tableInst)throw new RestException(ExMessages.UPDATE_OBJECT_FAILED,"No Record found for $params.service and $params.id")
		def tableParams = EntityConvertor.convertToEntityMap(params,aPod, params.service)
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

}

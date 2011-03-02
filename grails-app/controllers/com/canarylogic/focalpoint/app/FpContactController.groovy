package com.canarylogic.focalpoint.app

import com.canarylogic.base.*
import com.canarylogic.focalpoint.*;
import com.canarylogic.focalpoint.utils.*;

class FpContactController extends BaseController{

	def static int DEFAULT_PAGE_SIZE=50
    def index = { }
		
	//list or searchRecords(searcParams from a list and need to create a query based on that)
	//searchRecords([searchKey:SearchValue],service,applicationId,offset,max,orderField,order(asc,desc)
	def listRecords = {
		log.debug "search Records Called for $params.service"
		try  {
			//check if mandatory params are present
			log.debug "Search Record  params recieved are $params"
			params.max = Math.min(params.max ? params.int('max') : DEFAULT_PAGE_SIZE,100)
			params.offset = params.offset? params.int('offset'):0
			String orderField = params.orderField?params.String(orderField):'id'
			String orderType=params.order?params.String(order):'asc'
			
			def tableClass = EntityConvertor.getDomainClass(params.applicationId, params.service)
			if(!tableClass) throw new RestException(ExMessages.LIST_OBJECT_FAILED,"No Domain  found for $params.service")
			
			def searchParamsMap = EntityConvertor.convertToEntityMap(params,params.applicationId, params.service)
			def client = Client.findByOrgId(params.applicationId)
			if(!client) throw new RestException(ExMessages.LIST_OBJECT_FAILED,"Invalid applicationI $params.applicationId")
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
			String xmlResp = EntityConvertor.convertEntityListToXml(resultList,params.applicationId,params.service)
			render(text: xmlResp as String, contentType:"text/xml", encoding:"UTF-8")			
		}catch(Exception ex) {
			displayError(ex)
		}
	}
	
	//GetRecord(id,service)
	def getRecord = {
		log.debug "get Record Called for $params.service"
		try {
//			def tableClass = ServiceDefineEnum.getTableName(params.service)
			def tableClass = EntityConvertor.getDomainClass(params.applicationId, params.service)
			def tableInst = tableClass.get(params.id)
			if(tableInst) {
				String xmlResp =  EntityConvertor.convertEntityToXml(tableInst, params.applicationId, params.service)
				render(text: xmlResp as String, contentType:"text/xml", encoding:"UTF-8")					
			}else {
				throw new RestException(ExMessages.LIST_OBJECT_FAILED,"No Record found for $params.service and $params.id")
			}
		}catch(Exception ex) {
			displayError(ex)
		}
	}
	
	def updateRecord = {
		log.debug "Update Record Called for $params.service and $params.id"
		try {
			def tableClass = EntityConvertor.getDomainClass(params.applicationId, params.service)
			def tableInst = tableClass.get(params.id)
			if(!tableInst)throw new RestException(ExMessages.UPDATE_OBJECT_FAILED,"No Record found for $params.service and $params.id")
			def tableParams = EntityConvertor.convertToEntityMap(params, params.applicationId, params.service)
			tableInst.properties = tableParams
			def updatedInst = tableInst.save()
			if(updatedInst) {
				String xmlResp =  EntityConvertor.convertEntityToXml(updatedInst, params.applicationId, params.service)
				render(text: xmlResp as String, contentType:"text/xml", encoding:"UTF-8")					
			}else {
				throw new RestException(ExMessages.UPDATE_OBJECT_FAILED,"No Record found for $params.service and $params.id")
			}
		}catch(Exception ex) {
			displayError(ex)
		}
	}
	
	def deleteRecord = {
		log.debug "Delete Record Called for $params.service and $params.id"
		try {
			def tableClass = EntityConvertor.getDomainClass(params.applicationId, params.service)
			def tableInst = tableClass.get(params.id)
			if(tableInst) {
				tableInst.delete(flush: true)
				displayXmlRes("deleteRecord",[id:tableInst.id])
			}else
				throw new RestException(ExMessages.DELETE_OBJECT_FAILED,"No Record found for $params.service and $params.id")
		}catch(Exception ex) {
			displayError(ex)
		}
	}
	
//	createRecord(recParams)
	def  createRecord = {
		log.debug "created Record Called for $params.service"
		try  {
			//check if mandatory params are present
			log.debug "Create Record  params recieved are $params"
			def tableClass = EntityConvertor.getDomainClass(params.applicationId, params.service)
			def tableParams = EntityConvertor.convertToEntityMap(params, params.service)
			def tableInst = tableClass.newInstance()
			tableInst.properties = tableParams
			def tableNewInst= tableInst.save()	
			if(tableNewInst!=null){
				String xmlResp =  EntityConvertor.convertEntityToXml(tableNewInst, params.applicationId, params.service)
				render(text: xmlResp as String, contentType:"text/xml", encoding:"UTF-8")
				//displayXmlRes("createRecord",[id:result.id])
			}else {		
				throw new RestException(ExMessages.CREATE_OBJECT_FAILED,"Create Record failed for $params.service")
			}
			
		}catch(Exception ex) {
			displayError(ex)
		}
	}
	
	
	
		
}

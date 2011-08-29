package com.canarylogic.focalpoint.app

import com.canarylogic.base.*
import com.canarylogic.focalpoint.*;
import com.canarylogic.focalpoint.utils.*;

class FpContactController extends BaseController{

	def entityXmlService
	def static int DEFAULT_PAGE_SIZE=50
    def index = { }
		
	static String CANARY_APP_ID="canary-test-123"
	static String CANARY_USER="mark@harvest.com"
	
	static String EXISTING_GRP="admin"
	static String EXISTING_ROLE="admin"
	
	//list or searchRecords(searcParams from a list and need to create a query based on that)
	//searchRecords([searchKey:SearchValue],service,applicationId,offset,max,orderField,order(asc,desc)
	def listRecords = {
		log.debug "search Records Called for $params.service"

		try  {
			//check if mandatory params are present
			log.debug "Search Record  params recieved are $params"
			
			params.max = Math.min(params.max ? params.int('max') : DEFAULT_PAGE_SIZE,100)
			params.offset = params.offset? params.int('offset'):0
			
			String xmlResp = entityXmlService.findOrlistRecords(params)
			log.debug ("xmlResp recieved is $xmlResp")
			render(text: xmlResp as String, contentType:"text/xml", encoding:"UTF-8")			
		}catch(Exception ex) {
			displayError(ex)
		}
	}
	
	//GetRecord(id,service)
	def getRecord = {
		log.debug "get Record Called for $params.service"
		try {
			def xmlResp = entityXmlService.getRecord(params)
			if(!xmlResp)
				throw new RestException(ExMessages.LIST_OBJECT_FAILED,"No Record found for $params.service and $params.id")
			render(text: xmlResp as String, contentType:"text/xml", encoding:"UTF-8")					
		}catch(Exception ex) {
			displayError(ex)
		}
	}
	
	def updateRecord = {
		log.debug "Update Record Called for $params.service and $params.id"
		try {
			String xmlResp = entityXmlService.updateRecord(params)
			if(!xmlResp)
				throw new RestException(ExMessages.UPDATE_OBJECT_FAILED,"No Record found for $params.service and $params.id")
			render(text: xmlResp as String, contentType:"text/xml", encoding:"UTF-8")
		}catch(Exception ex) {
			displayError(ex)
		}
	}
	
	def deleteRecord = {
		log.debug "Delete Record Called for $params.service and $params.id"
		try {
			def tableClass = entityXmlService.getDomainClass(params.applicationId, params.service)
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
	
	def  createRecord = {
		log.debug "created Record Called for $params.service"
		try  {
			String xmlResp  = entityXmlService.createRecord(params)
			if(!xmlResp)
				throw new RestException(ExMessages.CREATE_OBJECT_FAILED,"Create Record failed for $params.service")
			render(text: xmlResp as String, contentType:"text/xml", encoding:"UTF-8")
				
		}catch(Exception ex) {
			displayError(ex)
		}
	}
		
}

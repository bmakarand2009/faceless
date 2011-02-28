package com.canarylogic.focalpoint.app

import com.canarylogic.base.*
import com.canarylogic.focalpoint.*;
import com.canarylogic.focalpoint.utils.*;

class FpContactController {//extends BaseController{

    def index = { }
	
	def listRecords = {
		params.max = Math.min(params.max ? params.int('max') : 3, 100)
		params.offset = params.offset? params.int('offset'):0		
		def pagnParams = [max:params.max,offset:params.offset]
		def tableClass = ServiceDefineEnum.getTableName(params.service)
		
		try {
			if(!tableClass) throw new RestException(ExMessages.LIST_OBJECT_FAILED,"No Domain  found for $params.service")
			def entityList = tableClass.list(pagnParams)
			String xmlResp = EntityConvertor.convertEntityListToXml(entityList,params.applicationId,params.service)
			render(text: xmlResp as String, contentType:"text/xml", encoding:"UTF-8")
		}catch(Exception ex) {
			displayError(ex)
		}
	}
	
	//GetRecord(id,service)
	def getRecord = {
		log.debug "get Record Called for $params.service"
		try {
			def tableClass = ServiceDefineEnum.getTableName(params.service)
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
			def tableClass = ServiceDefineEnum.getTableName(params.service)
			def tableInst = tableClass.get(params.id)
			if(!tableInst)throw new RestException(ExMessages.UPDATE_OBJECT_FAILED,"No Record found for $params.service and $params.id")
			def tableParams = EntityConvertor.convertToEntityMap(params, params.service)
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
			def tableClass = ServiceDefineEnum.getTableName(params.service)
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
			def tableClass = ServiceDefineEnum.getTableName(params.service)
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
	
	
	
	
	protected def displayError(Exception ex) {
		def info = "NA"
		if(ex instanceof RestException)   {
			def restEx = (RestException) ex
			info = restEx.additionalInfo
		}
		
		def messageStr = ExMessages.msgMap[ex.message]
		if(!messageStr) messageStr = 'Reason Not Available'

		render(contentType:"text/xml"){
			capi{
				errors(){
					error(code:ex.message){
						message(messageStr)
						additionalInfo(info)
					}
				}
			}
		 }
		 return
	}
	
	def displayXmlResult = {responseName,fieldName,fieldVal ->
		String respName = responseName
		render(contentType:"text/xml"){
			"${respName}Response" {
				"$fieldName"(fieldVal)
				"status"("success")
			}
		}//end of contenttype
	}
 
	def displayXmlRes = {responseName,fieldMap ->
		String respName = responseName
		render(contentType:"text/xml"){
			"${respName}Response" {
				fieldMap.each{key,val ->
					"$key"("$val")
				}
				"status"("success")
			}
		}//end of contenttype
	}
	
	
}

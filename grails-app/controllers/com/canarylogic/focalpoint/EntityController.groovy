package com.canarylogic.focalpoint

import com.canarylogic.base.BaseController
import com.canarylogic.focalpoint.utils.EntityConvertor;

class EntityController extends BaseController{

    def index = { }
	
	def create = {
		log.debug "create called for $params.appId"
		
	}
	
	def listRecords = {		
		params.max = Math.min(params.max ? params.int('max') : 3, 100)		
		params.offset = params.offset? params.int('offset'):0		
		def pagnParams = [max:params.max,offset:params.offset]
		try  {
			def entityList = Alpha.list(pagnParams)			
			String xmlResp = EntityConvertor.convertEntityListToXml(entityList,"applicationId","candidateService")
			render(text: xmlResp as String, contentType:"text/xml", encoding:"UTF-8")
		}catch(Exception ex) {
			displayError(ex)
		}
	}
	

	
}

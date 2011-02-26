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
	
	
	def trel= {
		log.debug ("Setting up the Development Database")
		String testClient = "testClient123"		
		//create group
//		def groupMo = new GroupMo(clientId:testClient,moName:"testDefault",moDesc:"this is a default testgroup")
//						.addToUsers(moName:"user2@gmail.com",clientId:"client123",password:"admin123").save()
						

//		def groupMo = 	GroupMo.get(1)
//		UserMo user2 = new UserMo(moName:"user5@gmail.com",clientId:"myOtherClient",password:"admin1234")
//		user2.save()
//		groupMo.addUserToGroup(user2)					
//		return "hello"
		
	}

	
}

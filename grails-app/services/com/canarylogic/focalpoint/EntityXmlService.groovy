package com.canarylogic.focalpoint

import com.canarylogic.base.RestException
import com.canarylogic.base.ExMessages

import groovy.xml.MarkupBuilder
import com.canarylogic.focalpoint.contacts.*

import com.canarylogic.sing.Client

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
		getDomainClassWithaPod(aPod, serviceName)
	}

		
	def String findOrlistRecords(def params){
		log.debug "findOrlistRecords called"
		String aPod = podsFile(params.applicationId)
		def tableClass = getDomainClassWithaPod(aPod, params.service)
		if(!tableClass) throw new RestException(ExMessages.LIST_OBJECT_FAILED,"No Domain  found for $params.service")
		log.debug "tableClassf found is $tableClass"
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
		
		resultList.each{
			log.debug "${it.c1} ${it.c2}"
			it.child1.each{
				log.debug it.c2
			}
		}
		String xmlResp = convertEntityListToXml(resultList, aPod, params.service)
		return xmlResp
	}
	
	def String createRecord(def params){
		//check if mandatory params are present
		log.debug "Create Record  params recieved are $params"
		String xmlResp=null;
		String aPod = podsFile(params.applicationId)
		def tableClass = getDomainClassWithaPod(aPod, params.service)
		//log.debug "tableClass received is $tableClass"
		def tableParams = convertToEntityMap(params,aPod, params.service)
		log.debug "converted tableParams are $tableParams"
		def client = Client.findByOrgId(params.applicationId)
		if(!client) throw new RestException(ExMessages.LIST_OBJECT_FAILED,"Invalid applicationI $params.applicationId")
		tableParams.put("parent",client)
		
		def tableInst = tableClass.newInstance()
		tableInst.properties = tableParams
		
		if (!tableInst.hasErrors() && tableInst.save(flush: true)) {
			xmlResp = convertEntityToXml(tableInst, aPod, params.service)
			log.debug "$xmlResp"
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
		def tableClass = getDomainClassWithaPod(aPod, params.service)
		def tableInst = tableClass.get(params.id)
		if(!tableInst)throw new RestException(ExMessages.UPDATE_OBJECT_FAILED,"No Record found for $params.service and $params.id")
		def tableParams = convertToEntityMap(params,aPod, params.service)
		tableInst.properties = tableParams
		def updatedInst = tableInst.save()
		if(updatedInst) {
			xmlResp = convertEntityToXml(updatedInst, aPod, params.service)
		}
		return xmlResp
	}
	
	def String getRecord(def params){
		String xmlResp = null
		String aPod = podsFile(params.applicationId)
		def tableClass = getDomainClassWithaPod(aPod, params.service)
		def tableInst = tableClass.get(params.id)
		if(tableInst) 
			xmlResp = convertEntityToXml(tableInst, aPod, params.service)
		return xmlResp	
	}
	
	
	
	/////////////////////////
	///Following methods are private and static, copied form EntityConvertor, slowly
	//we will move metods from EntityConvertor to this class to avoid confusion of two classes
	
	
	public static String CAND_SERVICE="candidate"
	public static String ADMIN_SERVICE="admin"
	public static String VENDOR_SERVICE="vendor"
	
	def static SERVICE_DOMAIN_MAP=['admin':User.class]
	def static ENTITY_MAP=['Alpha':Alpha.class,'VendorsMo':VendorsMo.class]
	
	
	
	
	/*
	* @param attribMap = [firstName:"john", lastName="martin"]
	* @param serviceName e.g "candidateService
	* @clientParser : new XmlParser().parseText(xml)
	* @return [c1:john,c2:martin]
	*
	*/
   
   
   
	
	/**
	* Convert List of Candidates to corresponding XML based on client
	* Note: for each enity defined make sure getFieldVal() method is implemented in i
	* @param entityList
	* @param applicationId
	* @param serviceName
	* @returns
	*/
   def String convertEntityListToXml(def entityList, String aPod, String serviceName) {
	   def cParser = new XmlParser().parseText(aPod)
	   def clientList = convertToClientMap(entityList,cParser,serviceName)
	   String clientXml = convertToXml(clientList)
	   return clientXml
   }
   
   
   
   public static String convertEntityToXml(def aEntity, String aPod, String serviceName) {
	   def cParser = new XmlParser().parseText(aPod)
	   def clientList = convertToClientMap([aEntity],cParser,serviceName)
	   String clientXml = convertToXml(clientList)
	   return clientXml
   }
   
   
    def  getDomainClassWithaPod(String aPod, String serviceName ) {
	   def clientParser = new XmlParser().parseText(aPod)
	   def domainClass = SERVICE_DOMAIN_MAP.get(serviceName)
	   if(!domainClass) {
		   def serviceRecord = clientParser.service.find{it.@name == serviceName }
		   def  entityMapping = serviceRecord.entityMapping[0]
		   String entityName = entityMapping.@name
		   domainClass = ENTITY_MAP[entityName]
	   }
	   log.debug "getDomainName for $serviceName returned $domainClass.name"
	   return domainClass
   }
   
	
	
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
	    //log.debug "columnMap value is $columnMap"
		String entityName = entityMapping.@name
		
		def viewMappingRecord = serviceRecord.viewMapping[0]
		
		//log.debug "start looping for viewMapping Records $viewMappingRecord"
		viewMappingRecord.attribute.each{
		   String nodeName = it.@nodeName
		  // log.debug "finding mapping for $nodeName"
		   //log.debug "${attribMap[nodeName]} "
		   if(columnMap[nodeName] != null &&  attribMap[nodeName] != null){
			   resultAttribMap.put(columnMap[nodeName], attribMap[nodeName])
			}
		   //log.debug "going for next nodeName"
		}
		// log.debug "convertToEntityMap  for service $serviceName returned $resultAttribMap"
		return resultAttribMap
	}
   
   /*
	* @param attribMap = [firstName:"john", lastName="martin"]
	* @param serviceName e.g "candidateService
	* @clientParser : new XmlParser().parseText(xml)
	* @return [c1:john,c2:martin]
	*
	*/
   /*
   public def  convertToEntityMap(def attribMap, String aPod, String serviceName) {
	   //find the domainObject
	   log.debug "convertToEntityMap called attribMap is $attribMap"
	   def clientParser = new XmlParser().parseText(aPod)
	   
	   def resultAttribMap=[:]
	   def serviceRecord = clientParser.service.find{it.@name == serviceName }
	   def  entityMapping = serviceRecord.entityMapping[0]
	   def columnMap = [:]
	   entityMapping.column.each{
		  columnMap.put(it.@alias,it.@name)
	   }
	   String entityName = entityMapping.@name
	   
	   def viewMappingRecord = serviceRecord.viewMapping[0]

			   
	   viewMappingRecord.attribute.each{
		  String nodeName = it.@nodeName
		  log.debug "finding mapping for $nodeName"
		  if(columnMap[nodeName] != null &&  attribMap[nodeName] != null){
			  resultAttribMap.put(columnMap[nodeName], attribMap[nodeName])
		   }
	   }
	   log.debug "convertToEntityMap  for service $serviceName returned $resultAttribMap"
	   return resultAttribMap
   }
   */
	
   
   /////////////////////////////////////////////////////////////
   
   private def static convertToClientMap(def entityList,def clientParser, String serviceName) {
	   println "convertToClientMap called for $serviceName"
	   def clientList=[]
	   def serviceRecord = clientParser.service.find{it.@name == serviceName }
	   def  entityMapping = serviceRecord.entityMapping[0]
	   String entityName = entityMapping.@name
	   println "convertToClientMap: processing for entityName $entityName and $serviceName"

	   def entityMapAttribs=[:]
	   entityMapping.column.each{
			entityMapAttribs.put(it.@name,it.@alias)
	   }
	 
	  entityList.each{ entity ->
		   def aRecord =[:]
		   entityMapAttribs.each{
				String clField = it.value
				String c1Value = entity.getFieldVal(it.key)
				aRecord.put(clField,c1Value)
		   }
//            println "**********convertToClientMap Processing record $i"
		   if(aRecord)   clientList.add(aRecord)
	  }
	  println "convertToClientMap for $serviceName returning ${clientList.size()} records"
	  return clientList
}
   
   private def static convertToXml(def clientList){
	   def writer = new StringWriter()
	   def xml= new MarkupBuilder(writer)
	   xml.records() {
			clientList.each{ data->
				record(){
					data.each{ key,val ->
						"$key"("$val")
					}
				}
			}
	   }
//      String resultXml = " <?xml version='1.0'?>\n${writer.toString()}"
	  String resultXml = writer.toString()
	 return resultXml
}
   
   

}

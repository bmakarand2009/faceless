package com.canarylogic.focalpoint.utils
import groovy.xml.MarkupBuilder
import com.canarylogic.focalpoint.contacts.*

import com.canarylogic.focalpoint.*
class EntityConvertor {

	
	public static String CAND_SERVICE="candidate"
	public static String ADMIN_SERVICE="admin"
	public static String VENDOR_SERVICE="vendor"
	
	def static SERVICE_DOMAIN_MAP=['admin':User.class]
	def static ENTITY_MAP=['Alpha':Alpha.class,'VendorsMo':VendorsMo.class]
	/**
	 * Convert List of Candidates to corresponding XML based on client
	 * Note: for each enity defined make sure getFieldVal() method is implemented in i
	 * @param entityList
	 * @param applicationId
	 * @param serviceName
	 * @returns
	 */
	public static String convertEntityListToXml(def entityList, String aPod, String serviceName) {
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
	
	
	public def static getDomainClass(String aPod, String serviceName ) {
		def clientParser = new XmlParser().parseText(aPod)
		def domainClass = SERVICE_DOMAIN_MAP.get(serviceName)
		if(!domainClass) {
			def serviceRecord = clientParser.service.find{it.@name == serviceName }
			def  entityMapping = serviceRecord.entityMapping[0]
			String entityName = entityMapping.@name
			domainClass = ENTITY_MAP[entityName]		
		}
		println "getDomainName for $serviceName returned $domainClass.name"
		return domainClass
	}	
	
	
	/*
	 * @param attribMap = [firstName:"john", lastName="martin"]
	 * @param serviceName e.g "candidateService
	 * @clientParser : new XmlParser().parseText(xml)
	 * @return [c1:john,c2:martin]
	 * 
	 */
	
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
	
	
	
	
	/////////////////////////////////////////
		
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

package com.canarylogic.focalpoint.utils
import groovy.xml.MarkupBuilder

import com.canarylogic.focalpoint.*
class EntityConvertor {

	public static String CAND_SERVICE="candidate"
	public static String ADMIN_SERVICE="admin"
	
	def static SERVICE_DOMAIN_MAP=['admin':User.class]
	def static ENTITY_MAP=['Alpha':Alpha.class]
	/**
	 * Convert List of Candidates to corresponding XML based on client
	 * Note: for each enity defined make sure getFieldVal() method is implemented in i
	 * @param entityList
	 * @param applicationId
	 * @param serviceName
	 * @return
	 */
	public static String convertEntityListToXml(def entityList, String applicationId, String serviceName) {
		def cParser = new XmlParser().parseText(CLIENT_TEMPLATE)
		def clientList = convertToClientMap(entityList,cParser,serviceName)
		String clientXml = convertToXml(clientList)
		return clientXml
	}
	
	public static String convertEntityToXml(def aEntity, String applicationId, String serviceName) {
		def cParser = new XmlParser().parseText(CLIENT_TEMPLATE)
		def clientList = convertToClientMap([aEntity],cParser,serviceName)
		String clientXml = convertToXml(clientList)
		return clientXml
	}
	
	
	public def static getDomainClass(String applicationId, String serviceName ) {
		def clientParser = new XmlParser().parseText(CLIENT_TEMPLATE)
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
	
    public def static convertToEntityMap(def attribMap, String applicationId, String serviceName) {
        //find the domainObject
//        log.debug "convertToEntityMap called attribMap is $attribMap"
		def clientParser = new XmlParser().parseText(CLIENT_TEMPLATE)
		
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
           if(columnMap[nodeName] != null &&  attribMap[nodeName] != null){
               resultAttribMap.put(columnMap[nodeName], attribMap[nodeName])
            }
        }
//        log.debug "convertToEntityMap  for service $serviceName returned $resultAttribMap"
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
	
	

	static def CLIENT_TEMPLATE = '''
	   <client applicationId= 'b653b3e0-cc6f-4115-8e93-02142b03d8dd-foc' name='internalClient'>
	   
	
			<service name='candidate' isHotList='true' isDeleteCol='true'>
				
				<entityMapping name='Alpha'>
					<column name='id' alias='id'/>
					<column name='pkey' alias='pkey'/>
					<column name='c1' alias='firstName'/>
					<column name='c2' alias='lastName'/>
				</entityMapping>
				
				
				
				<viewMapping name='CandidateView'>
					<attribute nodeName='id' displayLabel='Id' listPanelIdx='-1' isSearchField='true'
						toolTip='false'  isDetailsPanel='true' >
						<attributeType type='textBox' tabName='1' columnNo='0'/>
					</attribute>
					
					<attribute nodeName='pkey' displayLabel='email' listPanelIdx='-1' isSearchField='true'
						toolTip='false'  isDetailsPanel='true' >
						<attributeType type='textBox' tabName='1' columnNo='0'/>
					</attribute>
					

					<attribute nodeName='firstName' displayLabel='First Name'   listPanelIdx='1' isSearchField='true'
						toolTip='false'  isDetailsPanel='true' >
						<attributeType type='textBox' tabName='1' columnNo='1'/>
					</attribute>
					
					<attribute nodeName='lastName' displayLabel='Last Name' listPanelIdx='-1' isSearchField='true'
						toolTip='false'  isDetailsPanel='true' >
						<attributeType type='textBox' tabName='1' columnNo='1'/>
					</attribute>
				</viewMapping>
				
			</service>
			
    		<service name='admin' isHotList='false' isDeleteCol='false'>
			 <entityMapping name='User'>
				 <column name='username' alias='userName'/>
				 <column name='enabled' alias='enabled'/>
				 <column name='accountExpired' alias='accountExpired'/>
				 <column name='accountLocked' alias='accountLocked'/>
				 <column name='passwordExpired' alias='passwordExpired'/>
			 </entityMapping>
		 
			 <viewMapping name='UserView'>
				 <attribute nodeName='userName' displayLabel='User Name'   listPanelIdx='1' isSearchField='true'
					 toolTip='false'  isDetailsPanel='true' >
					 <attributeType type='textBox' tabName='1' columnNo='1'/>
				 </attribute>
				 
				 <attribute nodeName='lastName' displayLabel='Last Name' listPanelIdx='-1' isSearchField='true'
					 toolTip='false'  isDetailsPanel='true' >
					 <attributeType type='textBox' tabName='1' columnNo='1'/>
				 </attribute>
			 </viewMapping>
		 
			</service>

			
			
	   </client>
	'''

}

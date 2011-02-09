package com.canarylogic.focalpoint.utils
import groovy.xml.MarkupBuilder

class EntityConvertor {

	
	public static String convertEntityListToXml(def entityList, String applicationId, String serviceName) {
		def cParser = new XmlParser().parseText(CLIENT_TEMPLATE)
		def clientList = convertToClientMap(entityList,cParser,serviceName)
		String clientXml = convertToXml(clientList)
		return clientXml
	}
	
	
	def static getDomainName(def clientParser, String serviceName ) {
		def serviceRecord = clientParser.service.find{it.@name == serviceName }
		def  entityMapping = serviceRecord.entityMapping[0]
		String entityName = entityMapping.@name
//		log.debug "getDomainName for $serviceName returned $entityName"
		return entityName
	}	
	
	
	/*
	 * @param attribMap = [firstName:"john", lastName="martin"]
	 * @param serviceName e.g "candidateService
	 * @clientParser : new XmlParser().parseText(xml)
	 * 
	 */
	
    def static convertToEntityMap(def attribMap, def clientParser, String serviceName) {
        //find the domainObject
//        log.debug "convertToEntityMap called attribMap is $attribMap"
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
	
	
		
	def static convertToClientMap(def entityList,def clientParser, String serviceName) {
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
	
	def static convertToXml(def clientList){
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
			<service name='candidateService' isHotList='true' isDeleteCol='true'>
				
				<entityMapping name='Alpha'>
					<column name='c1' alias='firstName'/>
					<column name='c2' alias='lastName'/>
				</entityMapping>
				
				
				
				<viewMapping name='CandidateView'>
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
			
			
	   </client>
	'''

}

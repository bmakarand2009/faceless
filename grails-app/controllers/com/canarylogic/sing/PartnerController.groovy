package com.canarylogic.sing

import groovy.xml.MarkupBuilder

class PartnerController {

    def partnerService

    def index() { }

    def VALID_DOMAINS=["$SingUtils.PERSON_ROOT","$SingUtils.COMPANY_ROOT",
                       "$SingUtils.KASE_ROOT","$SingUtils.OPPORTUNITY_ROOT",
                        "$SingUtils.TAG_ROOT",
                        "$SingUtils.CUSTOM_FIELD_DEFINITION_ROOT",
                       "$SingUtils.NOTE_ROOT","$SingUtils.TASK_ROOT"]

    /*
    (authkey,appId,domain('Contact,Company,,max,offset,order,orderField,
     */

//        def auth = request.getHeader('Authorization')
//
//        if (!auth) {
//             response.addHeader("WWW-Authenticate", "Basic realm=\"Sing Realm\"")
//             response.sendError(401, "Authorization required")
//             return false
//        }

    def show = {
        log.debug(params)
        log.debug(VALID_DOMAINS)
        try{
            if(!VALID_DOMAINS.contains("$params.domain"))
                throw new Exception("Resource Url not found for domain ${params?.domain}")
            def client = Client.findByOrgId(params?.applicationId)
           // if(!client)
             //   throw new Exception("client not found for applicationId ${params?.applicationId}")

            populateEntityId()
            populateEntityNameOrAction()
            boolean  isList = true

            if(params.entityOrAction && params.entityId) isList = true
            else if(params.entityOrAction) isList = true
            else if(params.entityId) isList = false

            def results = isList?partnerService.listRecords(client,params):partnerService.getRecord(client,params)
            def xmlResp = convToXml(isList,results)
            render(text: xmlResp as String, contentType: "text/xml", encoding: "UTF-8")
        }catch(Exception ex) {
            response.status = 400//Bad Request
			displayError(ex)
		}
    }



    /*
      * POST Request
      *
      *
      *  <person>
            <firstName>john</firstName><!-- required -->
            <lastName>martin</lastName><!-- required -->
            <address_list>
              <address>
                <street>statebridge road</street>
                <city>alpharetta</city>
                <state />
                <zip />
                <country />
              </address>
            </address_list>
            <contact_data_list>
              <email>
                <value>john.martin@ipilong.com</value>
                <category>home</category><!-- home | work| other -->
                <additionalInfo />
              </email>
            </contact_data_list>
            <date_created type='datetime'>2011-11-30 23:06:08.0</date_created>
            <last_updated type='datetime'>2011-11-30 23:06:08.0</last_updated>
            <createdBy>testUser</createdBy>
          </person>
     */
    def save = {
        createOrUpdateCall(true)
    }

    def update = {
          createOrUpdateCall(false)
    }

    def delete = {

    }


    private def createOrUpdateCall(boolean isCreate){
         def createXmlBody = request.reader.getText()
         def client = Client.findByOrgId("canary-test-123")

         def domainObj = partnerService.createOrUpdate(isCreate,client,"mytestuser",createXmlBody)
         formatResult(domainObj)
    }



    private def formatResult(def domainObj){
        withFormat {
            html {
                return [modObj: domainObj]
            }
            xml {
                def writer = new StringWriter()
                def xmlbldr= new MarkupBuilder(writer)
                domainObj.toXml(xmlbldr)
                def xmlResp =writer.toString()
                log.debug("xmlResp recieved is $xmlResp")
                render(text: xmlResp as String, contentType: "text/xml", encoding: "UTF-8")
            }

        }
    }

    private String convToXml(def isList,def results){
        def writer = new StringWriter()
        def xmlbldr= new MarkupBuilder(writer)
        if(isList){
            xmlbldr.list(size:results.size()) {
                results.each{ aContact->
                          aContact.toXml(xmlbldr)
                }
                requestId(new Date().time)
            }
        }else{
              results.toXml(xmlbldr)
        }
        def xmlResp =writer.toString()
        log.debug("xmlResp recieved is $xmlResp")
        return xmlResp
    }

    private def populateEntityId(){
        Integer entityId = null
        if(params.id) entityId = params.id.toInteger()
        else if(params.entityOrAction && params.entityOrAction.isNumber())
            entityId = params.entityOrAction.toInteger()
        params.entityId = entityId
    }
    private def populateEntityNameOrAction(){
        String entityName =null
        if(params.entityOrAction && !params.entityOrAction?.isNumber())
           entityName = params.entityOrAction
        params.entityOrAction = entityName
    }

    protected def displayError(Exception ex) {
		def messageStr = ex.message
		render(contentType:"text/xml"){
			capi{
                error(code:ex.message){
                    message(messageStr)
                }
			}
		 }
		 return
	}
}

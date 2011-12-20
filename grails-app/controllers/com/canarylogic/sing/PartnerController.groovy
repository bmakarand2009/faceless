package com.canarylogic.sing

import groovy.xml.MarkupBuilder

class PartnerController {


    static String PERSON_DOMAIN="Person"

    static def URL_DOMAIN_MAP=["/person":Person]

    def partnerService

    def index() { }

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
        def dummy = request
        def isTest = params.test
        String requestPath
        if(isTest){
            requestPath="/person"
        }else{
            requestPath = request.getRequest().request.requestDispatcherPath
        }
        def domainClz = URL_DOMAIN_MAP.get(requestPath)
        log.debug(params)
//        if (params.domain == PERSON_DOMAIN) domainName = new Person()
        def client = Client.findByOrgId(params.applicationId)


        def recList = partnerService.listRecords(client, domainClz, params)

        def writer = new StringWriter()
        def xmlbldr= new MarkupBuilder(writer)
        xmlbldr.list(size:recList.size()) {
            recList.each{ aContact->
                      aContact.toXml(xmlbldr)
            }
            requestId(new Date().time)
        }
        def xmlResp =writer.toString()
        log.debug("xmlResp recieved is $xmlResp")
        render(text: xmlResp as String, contentType: "text/xml", encoding: "UTF-8")

//        withFormat {
//            html {
//                return [recList: recList]
//            }
//            xml {
//                def writer = new StringWriter()
//                def xmlbldr= new MarkupBuilder(writer)
//                xmlbldr.list(size:recList.size()) {
//                    recList.each{ aContact->
//                              aContact.toXml(xmlbldr)
//                    }
//                    requestId(new Date().time)
//                }
//                def xmlResp =writer.toString()
//                log.debug("xmlResp recieved is $xmlResp")
//                render(text: xmlResp as String, contentType: "text/xml", encoding: "UTF-8")
//            }
//        }
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
}

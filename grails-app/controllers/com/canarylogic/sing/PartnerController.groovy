package com.canarylogic.sing

import com.canarylogic.focalpoint.Client
import groovy.xml.MarkupBuilder

class PartnerController {


    static String PERSON_DOMAIN="Person"

    def partnerService

    def index() { }

    /*
    (authkey,appId,domain('Contact,Company,,max,offset,order,orderField,
     */
    def list = {
        def abc = request
//        def auth = request.getHeader('Authorization')
//
//        if (!auth) {
//             response.addHeader("WWW-Authenticate", "Basic realm=\"Sing Realm\"")
//             response.sendError(401, "Authorization required")
//             return false
//        }

        def domainName
        log.debug(params)
        if (params.domain == PERSON_DOMAIN) domainName = new Person()
        def client = Client.findByOrgId(params.applicationId)


        def recList = partnerService.listRecords(client, domainName, params)
        withFormat {
            html {
                return [recList: recList]
            }
            xml {
                def writer = new StringWriter()
                def xmlbldr= new MarkupBuilder(writer)
                xmlbldr.list(size:recList.size()) {
                    recList.each{ data->
//                          if(domainName==CONTACT_DOMAIN){
                              Person contact = data as Person
                              contact.toXml(xmlbldr)
//                          }
                    }
                    requestId(new Date().time)
                }
                def xmlResp =writer.toString()
                log.debug("xmlResp recieved is $xmlResp")
                render(text: xmlResp as String, contentType: "text/xml", encoding: "UTF-8")
            }

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
    def create = {
          def createXmlBody = request.reader.getText()
          def client = Client.findByOrgId("canary-test-123")
          partnerService.create(client,createXmlBody)
          def xmlResp="<hello>helloworld</hello>"
          log.debug("xmlResp recieved is $xmlResp")
          render(text: xmlResp as String, contentType: "text/xml", encoding: "UTF-8")
    }
}

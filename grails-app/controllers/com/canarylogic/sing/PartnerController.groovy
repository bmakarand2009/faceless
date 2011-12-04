package com.canarylogic.sing

import com.canarylogic.focalpoint.Client
import groovy.xml.MarkupBuilder

class PartnerController {


    static String CONTACT_DOMAIN="Contact"

    def partnerService

    def index() { }

    /*
    (authkey,appId,type,max,offset,order,orderField,
     */
    def list = {->
        def domainName
        log.debug(params)
        if (params.domain == CONTACT_DOMAIN) domainName = new Contact()
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
                              Contact contact = data as Contact
                              contact.toXml(xmlbldr)
//                          }
                    }
                }


                def xmlResp =writer.toString()
                log.debug("xmlResp recieved is $xmlResp")
                render(text: xmlResp as String, contentType: "text/xml", encoding: "UTF-8")
            }

        }

    }
}

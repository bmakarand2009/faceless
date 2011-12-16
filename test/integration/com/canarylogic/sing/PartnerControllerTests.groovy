package com.canarylogic.sing

import grails.test.ControllerUnitTestCase
import groovy.xml.MarkupBuilder

/**
 * Created by IntelliJ IDEA.
 * User: bmakarand
 * Date: 12/3/11
 * Time: 1:48 PM
 * To change this template use File | Settings | File Templates.
 */
class PartnerControllerTests extends ControllerUnitTestCase {
    static String CANARY_APP_ID="canary-test-123"
    static String FOC_HARVEST_APP_ID="foc-harvest"


    protected void setUp() {
        super.setUp()
		mockLogging(PartnerController,true)

    }

    protected void tearDown() {
        super.tearDown()
    }

    void testList() {
		mockParams.domain="Person"
        mockParams.max=15
        mockParams.applicationId=CANARY_APP_ID
        mockRequest.contentType = "application/xml"
		controller.list()
		def xmlResp = controller.response.getContentAsString()

        def respParser = new XmlSlurper().parseText(xmlResp)
        println respParser.@size
        assertNotNull respParser.requestId
        assert respParser.person.size() > 5
	}

    void testCreate(){
       mockRequest.method = "POST"
       setPostRequestContent(getSampleCreateXml("ritestest${new Date().timeString}"))
       controller.create()
       def xmlResp = controller.response.getContentAsString()
       assertNotNull xmlResp
       def respParser = new XmlSlurper().parseText(xmlResp)
       assertNotNull respParser.id
    }

    void testUpdate(){
        mockRequest.method = "PUT"
        String personId ="15" //lookup in the db
        String addressId="4" //loookup in the db
        setPostRequestContent(getSampleUpdateXml("updateLastName${new Date().timeString}",personId,addressId))
        controller.update()
        def xmlResp = controller.response.getContentAsString()
        assertNotNull xmlResp
        def respParser = new XmlSlurper().parseText(xmlResp)
        String s = respParser.id
        assertEquals(personId,s)


    }

    def getSampleUpdateXml(String changedLastName,String personId,String addressId){
        assertNotNull(personId)
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)

        xml.person() {
          id("$personId")
          lastName("$changedLastName")
           address_list(){
              address(){
                  id(addressId)
                  street("statebridge_rd${new Date().timeString}")
              }
              address(){
                  street('norcross road')
                  city('norcorss')
                  state('GA')
                  zip('30071')
                  country('USA')
              }
          }
        }
        String xmlStr = writer.toString()
        return xmlStr

    }

    def getSampleCreateXml(String myLastName){
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)
        xml.person() {
          firstName('John')
          lastName("$myLastName")
          suffix('Mr')
          address_list(){
              address(){
                  street('statebridge_rd')
                  city('alpharetta')
                  state('GA')
                  zip('30022')
                  country('USA')
              }
          }
          contact_data_list(){
              email(){
                  value('john.martin@ipilong.com')
                  category('home')
                  additionalInfo('this is his home email')

              }
          }
        }
        String xmlStr = writer.toString()
        return xmlStr

    }

    def setPostRequestContent(String xmlContent) {
        String encoding="UTF-8"
        mockRequest.contentType = "application/xml"
        mockRequest.content = xmlContent.getBytes(encoding)
    }




}

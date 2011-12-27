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
    static String CANARY_APP_ID ="canary-test-123"
    static String FOC_HARVEST_APP_ID="foc-harvest"

    Client client

    protected void setUp() {
        super.setUp()
        client = Client.findByOrgId(CANARY_APP_ID)
        assertNotNull(client)
		mockLogging(PartnerController,true)

    }

    protected void tearDown() {
        super.tearDown()
    }

    void estShow() {
		mockParams.test="true"
        mockParams.max="15"
        mockParams.applicationId=CANARY_APP_ID
        mockRequest.contentType = "application/xml"
		controller.show()
		def xmlResp = controller.response.getContentAsString()

        def respParser = new XmlSlurper().parseText(xmlResp)
        println respParser.@size
        assertNotNull respParser.requestId
        assert respParser.person.size() > 5
	}



    /** Create Tests for Various Entities */
    static String NOTE_TYPE="Note"
    static String TAG_TYPE="Tag"

    void estCreatePerson(){
          commonPart(getSampleCreateXml(SingUtils.PERSON_TYPE,"ritestest${new Date().timeString}"))
    }
    void estCreateNote(){
        commonPart(getSampleCreateXml(NOTE_TYPE,"This is a sample note which was run for the partnercontroller test${new Date().timeString}"))
    }
    void testCreateTag(){
        commonPart(getSampleCreateXml(TAG_TYPE,"testTag${new Date().timeString}"))

    }


    private def commonPart = {  content ->
       mockRequest.method = "POST"
       setPostRequestContent(content)
       controller.save()
       def xmlResp = controller.response.getContentAsString()
       assertNotNull xmlResp
       def respParser = new XmlSlurper().parseText(xmlResp)
       assertNotNull respParser.id
    }


    ////End of Create Tests

    void estUpdate(){
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

    def getSampleUpdateXml( String changedLastName,String personId,String addressId){

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

    def getSampleCreateXml(String entityType,String sampleParam){
        def writer = new StringWriter()
        def xmlBldr = new MarkupBuilder(writer)
        if(entityType == SingUtils.PERSON_TYPE) createPersonXml(xmlBldr, sampleParam)
        else if(entityType == NOTE_TYPE) createNoteXml(xmlBldr,sampleParam)
        else if(entityType == TAG_TYPE) createTagXml(xmlBldr,sampleParam)
        writer.toString()
    }



    def setPostRequestContent(String xmlContent) {
        String encoding="UTF-8"
        mockRequest.contentType = "application/xml"
        mockRequest.content = xmlContent.getBytes(encoding)
    }






    /////////////Sample Xmls

    private def createTagXml(xmlBldr,sampleTag){
         assertNotNull(sampleTag)
         xmlBldr.tag(){
             name(sampleTag)
         }
    }

    private  def createNoteXml(MarkupBuilder xmlBldr, String aSampleNote) {
           def pList = client.getPersonList()
           assertNotNull pList
           def person = pList[0]
           xmlBldr.note(){
                  body(aSampleNote)
                  entity_id(person.id)
                  entity_type(SingUtils.PERSON_TYPE)
           }
    }
    private def createPersonXml(MarkupBuilder xml, String myLastName) {
       xml.person() {
           firstName('John')
           lastName("$myLastName")
           suffix('Mr')
           address_list() {
               address() {
                   street('statebridge_rd')
                   city('alpharetta')
                   state('GA')
                   zip('30022')
                   country('USA')
               }
           }
           contact_data_list() {
               email() {
                   value('john.martin@ipilong.com')
                   category('home')
                   additionalInfo('this is his home email')

               }
           }
       }
    }



}

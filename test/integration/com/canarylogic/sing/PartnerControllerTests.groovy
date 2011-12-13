package com.canarylogic.sing

import grails.test.ControllerUnitTestCase

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
      //  byte[] authBytes = Encoding.UTF8.GetBytes("user:password".ToCharArray());

		controller.list()
		def xmlResp = controller.response.getContentAsString()
//        assert xmlResp == "hello"

        def respParser = new XmlSlurper().parseText(xmlResp)
        println respParser.@size
        assertNotNull respParser.requestId
        assert respParser.person.size() > 5
	}

    void testCreate(){
       mockRequest.method = "POST"
       setXmlRequestContent(getSampleCreateXml())
       controller.create()
       def xmlResp = controller.response.getContentAsString()
        assertNotNull xmlResp
    }

    protected void setXmlRequestContent(String content) {
        String encoding="UTF-8"
        mockRequest.contentType = "application/xml"
        mockRequest.content = content.getBytes(encoding)
    }

    def getSampleCreateXml(){
        def myCreateXml = """
          <person>
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
        """
    }

}

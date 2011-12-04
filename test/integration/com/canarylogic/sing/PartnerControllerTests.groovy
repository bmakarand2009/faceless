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
		mockParams.domain="Contact"
        mockParams.max=15
        mockParams.applicationId=CANARY_APP_ID
        mockRequest.contentType = "application/xml"
		controller.list()
		def xmlResp = controller.response.getContentAsString()
        assert xmlResp == "hello"
//		def cParser = new XmlSlurper().parseText(xmlResp)
//		assert cParser10
	}

}

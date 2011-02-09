package com.canarylogic.focalpoint

import grails.test.*

class EntityControllerTests extends ControllerUnitTestCase {
    protected void setUp() {
        super.setUp()
		mockLogging(EntityController,true)
		
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testListRecords() {
		mockRequest.contentType = "application/xml" //"//'text/html' //"application/xml"
		controller.listRecords()
		def xmlResult = controller.response.getContentAsString()
		def cParser = new XmlSlurper().parseText(xmlResult)
		assert cParser.record.size() == 2
    }
	
	
}

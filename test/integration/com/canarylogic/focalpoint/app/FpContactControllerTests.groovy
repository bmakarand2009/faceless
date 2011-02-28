package com.canarylogic.focalpoint.app

import grails.test.*

class FpContactControllerTests extends ControllerUnitTestCase {
    protected void setUp() {
        super.setUp()
		mockLogging(FpContactController,true)
		
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testListRecords() {
		mockRequest.contentType = "application/xml" //"//'text/html' //"application/xml"
		mockParams.service = "candidate"
		controller.listRecords()
		def xmlResult = controller.response.getContentAsString()
		def cParser = new XmlSlurper().parseText(xmlResult)
		assert cParser.record.size() == 2
    }
	
	void testCreateRecord() {
		mockRequest.contentType = "application/xml" //"//'text/html' //"application/xml"
		mockParams.service = "candidate"
		mockParams.firstName = "myFirstCandName"
		mockParams.lastName = "mylastName"
		
		controller.createRecord()
		def xmlResp = controller.response.getContentAsString()
		def cParser = new XmlParser().parseText(xmlResp)
		assertNotNull cParser.record.id.text()
		cParser.record.firstName.text() == mockParams.lastName
	}
	
	void testGetRecord() {
		mockRequest.contentType = "application/xml" //"//'text/html' //"application/xml"
		mockParams.service = "candidate"
		mockParams.id=1 //TBD : make sure this record exists
		
		controller.getRecord()
		def xmlResp = controller.response.getContentAsString()
		def cParser = new XmlParser().parseText(xmlResp)
		assertNotNull cParser.record.id.text()
	}
	
	void testUpdateRecord() {
		mockRequest.contentType = "application/xml" //"//'text/html' //"application/xml"
		mockParams.service = "candidate"
		mockParams.id=1 //TBD : make sure this record exists
		mockParams.firstName = "updatedName"
		controller.updateRecord()
		def xmlResp = controller.response.getContentAsString()
		def cParser = new XmlParser().parseText(xmlResp)
		assert cParser.record.firstName.text() == "updatedName"
	}
	
	void testDeleteRecord() {
		mockRequest.contentType = "application/xml" //"//'text/html' //"application/xml"
		mockParams.service = "candidate"
		mockParams.id=1 //TBD : make sure this record exists
		mockParams.firstName = "updatedName"
		controller.deleteRecord()
		def xmlResp = controller.response.getContentAsString()
		def cParser = new XmlParser().parseText(xmlResp)
		assert cParser.id.text() == "1"
	}
}

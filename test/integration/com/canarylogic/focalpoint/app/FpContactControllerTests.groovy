package com.canarylogic.focalpoint.app

import grails.test.*
import com.canarylogic.focalpoint.*;
import com.canarylogic.base.TestConfig;
import com.canarylogic.focalpoint.utils.EntityConvertor
import com.canarylogic.focalpoint.contacts.VendorsMo


class FpContactControllerTests extends ControllerUnitTestCase {
	
	
    protected void setUp() {
        super.setUp()
		mockLogging(FpContactController,true)
		
    }

    protected void tearDown() {
        super.tearDown()
    }

	private void setCommonParams(){
		TestConfig.setCommonParams(mockParams,mockRequest)
		
	}
	
	
	/*
	 * 		for (i in 1..10) {
			def alphaInst1 = new Alpha(c1:"Burt$i",c2:"Charles$i",pkey:"key$i",parent:c1).save()
			def alphaInst2 = new Alpha(c1:"Bob$i",c2:"Cat$i",pkey:"bobkey$i",parent:c1).save()
			def alphaInst3 = new Alpha(c1:"Zing$i",c2:"Zend$i",pkey:"key$i",parent:c2).save()
			def alphaInst4 = new Alpha(c1:"Zeta$i",c2:"Jones$i",pkey:"zetakey$i",parent:c2).save()

	 */
	void testSearchRecords() {
		setCommonParams()
		def searchParamsMap = [:]
		if(mockParams.service == EntityConvertor.VENDOR_SERVICE){
			searchParamsMap.put("firstName","myfirstVendorName")
		}else{
			 searchParamsMap.put("firstName","bob")
		}
		searchParamsMap.each{k,v ->
			 mockParams."${k.toString()}" = v.toString()
		}
		controller.listRecords()
		def xmlResp = controller.response.getContentAsString()
		def cParser = new XmlSlurper().parseText(xmlResp)
		
		assert cParser.record.size() == 10
//		assert xmlResp == "hello"
	}
	
    void testListRecords() {
		setCommonParams()
		controller.listRecords()
		def xmlResult = controller.response.getContentAsString()
		def cParser = new XmlSlurper().parseText(xmlResult)
		assert cParser.record.size() >= 10
    }
	
	void testCreateRecord() {
		setCommonParams()
		if(mockParams.service == EntityConvertor.VENDOR_SERVICE){
			mockParams.company = "${new Date().time}company"
			mockParams.firstName = "myfirstVendorName"
			mockParams.lastName  = "mylastVendorName"
			mockParams.hasClients = "true2"
			mockParams.hasCandidates = "false2"
			mockParams.city = "alpharetta"
			mockParams.state ="ga"
			mockParams.zip = "30022"
			mockParams.expertiseArea = "java"
			mockParams.notes="has a strong foothold in atlanta"
			mockParams.isDesi ="true2"
		}else{
		    mockParams.pkey = "${new Date().time}cand"
			mockParams.firstName = "myFirstCandName"
			mockParams.lastName = "mylastName"
		}
		
		controller.createRecord()
		def xmlResp = controller.response.getContentAsString()
//		assert xmlResp == "hello"
		def cParser = new XmlParser().parseText(xmlResp)
		assertNotNull cParser.record.id.text()
		cParser.record.firstName.text() == mockParams.lastName
	}
	
	void testGetRecord() {
		setCommonParams()
		mockParams.id=1 //TBD : make sure this record exists
		controller.getRecord()
		def xmlResp = controller.response.getContentAsString()
		def cParser = new XmlParser().parseText(xmlResp)
		assertNotNull cParser.record.id.text()
	}
	
	void testUpdateRecord() {
		setCommonParams()
		mockParams.id=9 //TBD : make sure this record exists
		mockParams.firstName = "updatedName"
		controller.updateRecord()
		def xmlResp = controller.response.getContentAsString()
		def cParser = new XmlParser().parseText(xmlResp)
		assert cParser.record.firstName.text() == "updatedName"
	}
	
	void testDeleteRecord() {
		setCommonParams()
		mockParams.id=1 //TBD : make sure this record exists
		mockParams.firstName = "updatedName"
		controller.deleteRecord()
		def xmlResp = controller.response.getContentAsString()
		def cParser = new XmlParser().parseText(xmlResp)
		assert cParser.id.text() == "1"
	}
	
}

package com.canarylogic.sing

import com.canarylogic.focalpoint.Client
import org.junit.*
import grails.test.*

/**
 */
class PartnerServiceTests extends GrailsUnitTestCase {
	
	def partnerService
	static String CANARY_APP_ID="canary-test-123"
	static String FOC_HARVEST_APP_ID="foc-harvest"
	
	
	protected void setUp() {
		super.setUp()
		mockLogging(PartnerService,true)
		
	}

}

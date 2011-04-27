package com.canarylogic.cloud

import grails.test.*

class AznS3ServiceTests  extends GrailsUnitTestCase {
	
	def  aznS3Service
	protected void setUp() {
		super.setUp()
		mockLogging(AznS3Service,true)
		
	}
	
	void testGetPodContent()    {
		String podName="canary-test-123"
		String podContent = aznS3Service.getPodContent(podName)
		assert podContent.length() > 20
	}

}

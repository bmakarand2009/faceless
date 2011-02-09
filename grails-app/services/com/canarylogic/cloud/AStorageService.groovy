package com.canarylogic.cloud

import org.jets3t.service.impl.rest.httpclient.RestS3Service
import org.jets3t.service.security.AWSCredentials
import org.jets3t.service.model.S3Object
import org.jets3t.service.model.S3Bucket
import org.jets3t.service.acl.AccessControlList
import org.jets3t.service.utils.ServiceUtils;
import org.jets3t.service.Constants


import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.jets3t.service.S3ServiceException
import org.jets3t.service.S3Service
import org.jets3t.service.utils.signedurl.SignedUrlHandler;

class AStorageService {

    static transactional = false

    static S3Service s3
    static AWSCredentials awsCredentials
    static SignedUrlHandler signedUrlHandler
    def baseBucket
    public AStorageService() {
    	init()
    }
	
	def init(){
//    	log.debug ("init() called for AznS3Service")
		def accessKey = ConfigurationHolder.config.awsS3.accessKey
		def secretKey = ConfigurationHolder.config.awsS3.secretKey
		baseBucket = ConfigurationHolder.config.awsS3.baseBucket
		
//    	log.debug ("AccessKey is $accessKey and SecretKey is $secretKey")
//    	println("ACCESSKEY IS $accessKey and baseBucket is $baseBucket")
		awsCredentials =   new AWSCredentials(accessKey,secretKey );
		s3 = new RestS3Service(awsCredentials)
		signedUrlHandler = new RestS3Service(null);
	}
	
	
	def getPodsFile(String applicationId){
		log.debug ("getPodsFile called for $applicationId")
		StringBuffer sb = new StringBuffer()
		try{
			S3Bucket confBucket = new S3Bucket("$baseBucket/conf")
			log.debug "ConfBucket is $confBucket"
			S3Object xmlObj = s3.getObject(confBucket, "${applicationId}.xml");
//        	log.debug("S3Object, complete: " + xmlObj);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(xmlObj.getDataInputStream()));
				String data = null;
				while ((data = reader.readLine()) != null) {
					sb.append(data)
				}
		}catch(Exception ex){
			log.debug ex
			//throw new RestException(FocalExMessages.NO_DATA_FOUND,"Exception occured while Retrieving Configuration for $applicationId")
		}
	

//    	log.debug sb.toString()
		return sb.toString()

	}
	
}

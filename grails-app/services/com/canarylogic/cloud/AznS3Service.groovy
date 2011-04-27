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

class AznS3Service {

    static transactional = false
	
    static S3Service s3
    static AWSCredentials awsCredentials
    static SignedUrlHandler signedUrlHandler
    def baseBucket
	
	
	public AznS3Service() {
		init()
	}
	
	def init(){
	    	log.debug ("init() called for AznS3Service")
		def accessKey = ConfigurationHolder.config.awsS3.accessKey
		def secretKey = ConfigurationHolder.config.awsS3.secretKey
		baseBucket = ConfigurationHolder.config.awsS3.baseBucket
		
		log.debug ("AccessKey is $accessKey and SecretKey is $secretKey")
	//    	println("ACCESSKEY IS $accessKey and baseBucket is $baseBucket")
		awsCredentials =   new AWSCredentials(accessKey,secretKey );
		s3 = new RestS3Service(awsCredentials)
		signedUrlHandler = new RestS3Service(null);
	}

    def getPodContent(String podFile) {
		StringBuffer sb = new StringBuffer()
		try{
			String bb = "$baseBucket/conf"
			S3Bucket confBucket = new S3Bucket(bb)
			S3Object xmlObj = s3.getObject(confBucket, "${podFile}.xml");
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(xmlObj.getDataInputStream()));
				String data = null;
				while ((data = reader.readLine()) != null) {
					sb.append(data)
				}
		}catch(Exception ex){
			log.debug "Exception occured in getPodContent $ex $ex.message"
			//throw new RestException(FocalExMessages.NO_DATA_FOUND,"Exception occured while Retrieving Configuration for $applicationId")
		}
		return sb.toString()
		
		
    }
}

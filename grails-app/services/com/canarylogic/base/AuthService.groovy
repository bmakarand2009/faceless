package com.canarylogic.base


import com.canarylogic.focalpoint.*;
import com.canarylogic.focalpoint.utils.EncryptionUtils;
import org.codehaus.groovy.grails.commons.ConfigurationHolder
/**
 * For Authoization and Authencation
 * @author makarand
 * 
 * common params
 * clientId,userName,signature,timestamp,actionName
 *
 */
//TODO, Encrypt password on the User table, before creating or updating the password field 
class AuthService {

    static transactional = false
	
	

	//http://headless.harvestinfotech.com/$controller/$action?userId=kdioe@gmail.com&timestamp=121212121&signature=dfd&appId
	//paramsMap=[action=getAll,controller=contact,userId:kdio@gmail.com,appId:23232,serviceId=candidate,timestamp,signature]
	public boolean authUser(def paramsMap) {
		println "AuthencateUser called for $paramsMap "
		boolean isAuth
		 EncryptionUtils.checkMandatoryParams(paramsMap)
		if(!EncryptionUtils.authCredentials(paramsMap))
			throw new RestException(ExMessages.AUTHENCIATION_FAILED,"Invalid Credentials")
		isAuth = EncryptionUtils.isActionAuthorized(paramsMap);
		return isAuth
    }

}

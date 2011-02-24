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
		if(!authCredentials(paramsMap))
			throw new RestException(ExMessages.AUTHENCIATION_FAILED,"Invalid Credentials")
		isAuth = isActionAuthorized(paramsMap);
		return true
    }
	
	private boolean authCredentials(def paramsMap) {
		User curUser = User.findUser(paramsMap.userId, paramsMap.applicationId)
		if(!curUser)
			throw new RestException(ExMessages.AUTHENCIATION_FAILED,"Invalid user")
		EncryptionUtils.validateSignature(paramsMap,curUser.password)
		return true
	}
	
	def actionServiceMap=['auth': Services.IS_ACCESS]
	private boolean isActionAuthorized(def paramsMap) {
		User curUser = User.findUser(paramsMap.userId, paramsMap.applicationId)
			if(!curUser) throw new RestException(ExMessages.AUTHENCIATION_FAILED,"No valid user found for $paramsMap.userId with $paramsMap.applicationId")

		Role curRole = curUser.findRole()
		if(!curRole) 
			throw new RestException(ExMessages.AUTHENCIATION_FAILED,"No Role defined for ${paramsMap.userId}")
		
		String serviceName = paramsMap.service
		Services servicePriv = Services.findByServiceNameAndRole(serviceName,curRole)	
		if(!servicePriv) throw new RestException(ExMessages.AUTHENCIATION_FAILED,"No Valid service priviledges found with name $serviceName for role $curRole.roleName")
		String privName = actionServiceMap.get(paramsMap.action)
		boolean isAuthorized = servicePriv.isAuthroized(privName)
		
		if(!isAuthorized)
			throw new RestException(ExMessages.AUTHENCIATION_FAILED,"User ${paramsMap.userId} does not have priviledges for $paramsMap.action and $privName")
		return isAuthorized
	}
	
	
	

}

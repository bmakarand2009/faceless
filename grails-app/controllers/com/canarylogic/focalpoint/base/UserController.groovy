package com.canarylogic.focalpoint.base


import com.canarylogic.base.*
import com.canarylogic.focalpoint.*;
//serviceName is "admin"
class UserController extends BaseController{

	String serviceName = "admin"
    def index = { }
	def transactional = false
	
	//createGroup(grpName:"mygroup",grpLabel:"grpDesc"
	def createGroup = {
            try  {
                log.debug "Create Record  params recieved are $params"				
				def groupInstance = new Groups(grpName:params.grpName, grpLabel:params.grpLabel)
				def savedInstance = groupInstance.createGroup(params.applicationId)
				if (savedInstance!=null) {
					displayXmlResult("createGroup","groupName",savedInstance.grpName)
				}else {
					String myErrs=""
					groupInstance.errors.allErrors.each { myErrs = "$myErrs ${it.defaultMessage} $it" }
					throw new RestException(ExMessages.CREATE_OBJECT_FAILED,myErrs)
				}
            }catch(Exception ex) {
                displayError(ex)
            }      
     }
	
	 //createRole(roleName,roleLabel,applicationId)
	 def createRole = {
		 try {
			 String appId = params.applicationId
			 String roleName = params.roleName
			 String roleLabel = params.roleLabel
			 if(!roleName || !roleLabel) throw new RestException(ExMessages.CREATE_OBJECT_FAILED,"No Valid roleName/roleLabel found for $roleName ")
			 
			 Role aRole = new Role(roleName:roleName,label:roleLabel)
			 def roleInstance = aRole.addRole (appId)
			 if (roleInstance!=null) {
				 displayXmlResult("createRole","roleName",roleInstance.roleName)
			 }else {
				 String myErrs=""
				 roleInstance.errors.allErrors.each { myErrs = "$myErrs ${it.defaultMessage} $it" }
				 throw new RestException(ExMessages.CREATE_OBJECT_FAILED,myErrs)
			 }
		 }catch(Exception ex) {
                displayError(ex)
        }
		 
	 }

	 //addServicePrivToRole(service,isAccess,isUpdate,isDelete,isSelfGroup,isCreate)
	 def addServicePrivToRole= {
		 
	 }	
	  	
	//assginUsersToGroup(grpName:, userName:)
	def assignUsersToGroup() {
		try {
			String userName = params.userName
			String grpName = params.grpName
			if(!userName || !grpName) throw new RestException(ExMessages.UPDATE_OBJECT_FAILED,"No Valid user/group found for $userName $grpName")
			User u = User.findByUsername(userName)
			Groups grpInstance = u.assignGroup (grpName)
			///////////////standard block
			if (grpInstance!=null) {
				displayXmlResult("assignUsersToGroup","groupName",grpInstance.grpName)				
			}else {
				String myErrs=""
				grpInstance.errors.allErrors.each { myErrs = "$myErrs ${it.defaultMessage} $it" }
				throw new RestException(ExMessages.UPDATE_OBJECT_FAILED,myErrs)
			}
			////////////////////////
		}catch(Exception ex) {
                displayError(ex)
        }
	}
	
	//assignRoleToUser(userName:abc@gmail.com, roleName:admin)
	def assignRoleToUser() {
		String userName = params.userName
		String roleName = params.roleName
		try {
			if(!userName || !roleName) throw new RestException(ExMessages.UPDATE_OBJECT_FAILED,"No Valid user/role found for $userName $roleName")
			User curUser = User.findByUsername(userName)
			if(!curUser)	throw new RestException(ExMessages.UPDATE_OBJECT_FAILED,"No Valid user found for $userName ")
			def result = curUser.assignRole(roleName)
			if(result)
				displayXmlResult("assignRoleToUser","roleName",result.role.roleName)
			else
				throw new RestException(ExMessages.UPDATE_OBJECT_FAILED,"role $roleName could not be user")
		}catch(Exception ex) {
			displayError(ex)
		}
	}
	
	
	
	def displayXmlResult = {responseName,fieldName,fieldVal ->
		String respName = responseName
		render(contentType:"text/xml"){
			"${respName}Response" {
				"$fieldName"(fieldVal)
				"status"("success")
			}
		}//end of contenttype
	}
	
}

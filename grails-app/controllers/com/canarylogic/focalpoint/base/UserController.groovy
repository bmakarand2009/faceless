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
					render(contentType:"text/xml"){
						"createGroupResponse" {
							"groupName"(savedInstance.grpName)
							"status"("success")
						}
					}//end of contenttype
				}else {
					String myErrs=""
					groupInstance.errors.allErrors.each { myErrs = "$myErrs ${it.defaultMessage} $it" }
					throw new RestException(ExMessages.CREATE_OBJECT_FAILED,myErrs)
				}
            }catch(Exception ex) {
                displayError(ex)
            }      
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
				render(contentType:"text/xml"){
					"assignUsersToGroupResponse" {
						"groupName"(grpInstance.grpName)
						"status"("success")
					}
				}//end of contenttype
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
	
	
}

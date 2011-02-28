package com.canarylogic.base

class BaseController {
	AuthService authService
	
    def index = { }
	
	def beforeInterceptor=[action:this.&auth,except:['goldSuccess','goldFailure','mchook']]	
	
	def auth(){
		try{			
				println "AUTH method called for $params"
				authService.authUser(params)				
		   }catch(Exception ex){
			  displayError(ex)
			  return false
		   }
		return true
	 }
	
	protected def displayError(Exception ex) {
		def info = "NA"		
		if(ex instanceof RestException)   {
            def restEx = (RestException) ex
            info = restEx.additionalInfo
        }
		
		def messageStr = ExMessages.msgMap[ex.message]
		if(!messageStr) messageStr = 'Reason Not Available'

		render(contentType:"text/xml"){
			capi{
				errors(){
					error(code:ex.message){
						message(messageStr)
						additionalInfo(info)
					}
				}
			}
		 }
		 return
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
 
	def displayXmlRes = {responseName,fieldMap ->
		String respName = responseName
		render(contentType:"text/xml"){
			"${respName}Response" {
				fieldMap.each{key,val ->
					"$key"("$val")
				}
				"status"("success")
			}
		}//end of contenttype
	}
}

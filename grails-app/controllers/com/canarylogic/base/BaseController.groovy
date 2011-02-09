package com.canarylogic.base

class BaseController {

    def index = { }
	
	def beforeInterceptor=[action:this.&auth,except:['goldSuccess','goldFailure','mchook']]	
	
	def auth(){
//		try{			
//			if(params.applicationId == null || params.service == null )
//			    throw new RestException(ExMessages.AUTHENCIATION_FAILED, "Invalid applicationId or serviceName")
//		   }catch(Exception ex){
//			  displayError(ex)
//			  return false
//		   }
//		 return true
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
 
}

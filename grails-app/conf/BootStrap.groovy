import com.canarylogic.focalpoint.Alpha;
import com.canarylogic.focalpoint.GroupMo;
import com.canarylogic.focalpoint.UserMo;


import org.codehaus.groovy.grails.commons.GrailsApplication
import grails.util.GrailsUtil
import grails.util.Environment

class BootStrap {

	def grailsApplication
 	def init = { servletContext ->
		 println "GrailsUtil.environment is $GrailsUtil.environment"
		 
		 addMetaMethods()
		 switch(GrailsUtil.environment){
			 case "development":
			   println "#### Development Mode (Start Up)"
			   initDev()
			   break
			 case "test":
			   println "#### Test Mode (Start Up)"
			   initTest()
			   break
			 case "production":
			   println "#### Production Mode (Start Up)"
			   initProd()
			   break
		 }
	}
 
	def addMetaMethods() {
		 String.metaClass.shout = {->
			 return delegate.toUpperCase()+"META"
		  }
		 
		 GroupMo.metaClass.addUserToGroup= { userMo ->
			 userMo.clientId = delegate.clientId
			 delegate.addToUsers(userMo)			
		 }
		   
	 }
    def destroy = {
    }
	
	
	def initTest = {
		
		//new Alpha(c1:"shyam",c2:"sundar").save()
		//new Alpha(c1:"shivaji",c2:"sardar").save()

	}
	 
	def initDev = {		
		
		//Create default Group
//		def groupMo = GroupMo.findByMoNameAndClientId("testGroup",testClient)
//		if(groupMo)
//			log.debug"GroupMo findByMoNameAndClientId returned a GroupName"
//		else
//			log.debug "Group Mo not found"
//		if(groupMo == null) {
//			log.debug("Create Default Admin Group")
//			groupMo = new GroupMo(clientId:testClient,moName:"testDefault",moDesc:"this is a default testgroup")
//			groupMo.save();
//		}
//		
		
				
		new Alpha(c1:"sgg",c2:"sar").save()
		new Alpha(c1:"devJohn",c2:"walker").save()
		
	}
 
	def initProd = {
		initDev()
	}

}

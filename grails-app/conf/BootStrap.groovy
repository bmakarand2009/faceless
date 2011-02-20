import com.canarylogic.focalpoint.Alpha;

import org.codehaus.groovy.grails.commons.GrailsApplication
import grails.util.GrailsUtil
import grails.util.Environment

import com.canarylogic.focalpoint.*;

class BootStrap {

	def sessionFactory
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
//		 
//		 GroupMo.metaClass.addUserToGroup= { userMo ->
//			 userMo.clientId = delegate.clientId
//			 delegate.addToUsers(userMo)			
//		 }
		   
	 }
    def destroy = {
    }
	
	
	def initTest = {
		
		//new Alpha(c1:"shyam",c2:"sundar").save()
		//new Alpha(c1:"shivaji",c2:"sardar").save()

	}
	 
	private def createClient(String orgId,String orgDesc) {
		Client c1 = new Client(orgName:orgDesc, orgId:orgId)
		c1.save()
	}
	def initDev = {		
		String c1OrgId = "foc-canary",c1OrgName='canary'
		def client = Client.findByOrgId(c1OrgId)
		if(!client)
			setupDevClient()
		
	}
		
	private def setupDevClient() {
		//Step1: Create Clients Canary and Harvest
		String c1OrgId = "foc-canary",c1OrgName='canary'
		String c2OrgId = "foc-harvest",c2OrgName='harvest'
		createClient(c1OrgId,c1OrgName)
		createClient(c2OrgId,c2OrgName)
		
		//Step2: Add a AdminRole
		sessionFactory.currentSession.clear()
		Role adminRole1 = new Role(roleName:"admin",isAccess:true,isDelete:true,isUpdate:true,isSelfGroup:false,label:"sample Desc")
		Role adminRole2 = new Role(roleName:"admin",isAccess:true,isDelete:true,isUpdate:true,isSelfGroup:true,label:"sample desc")
		adminRole1.addRole (c1OrgId)
		adminRole2.addRole (c2OrgId)

		//Step3: Add a AdminGroup
		String grp1Name = "admin"
		sessionFactory.currentSession.clear()
		Groups adminGroup1 =  new Groups(grpName:grp1Name,grpLabel:"canary admin")
		Groups adminGroup2 =  new Groups(grpName:grp1Name,grpLabel:"harvest admin")
		adminGroup1.createGroup (c1OrgId)
		adminGroup2.createGroup (c2OrgId)
		
		//Step3: Create Users and assign to groups
		User u1 = new User(username:"mark@canary",password:"abcd")
		u1.create(grp1Name,c1OrgId)
		u1.assignRole( adminRole1.roleName,c1OrgId)
		
		User u2 = new User(username:"mark@harvest",password:"abcd")
		u2.create(grp1Name,c2OrgId)
		u2.assignRole( adminRole2.roleName,c2OrgId)
		
		new Alpha(c1:"sgg",c2:"sar").save()
		new Alpha(c1:"devJohn",c2:"walker").save()
		
	}
 
	def initProd = {
		initDev()
	}

}

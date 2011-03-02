
import org.codehaus.groovy.grails.commons.GrailsApplication
import grails.util.GrailsUtil
import grails.util.Environment

import com.canarylogic.focalpoint.*;
import com.canarylogic.focalpoint.utils.EntityConvertor;

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
		Role adminRole1 = new Role(roleName:"admin",label:"sample Desc")
		Role adminRole2 = new Role(roleName:"admin",label:"sample desc")
		adminRole1.addRole (c1OrgId)
		adminRole2.addRole (c2OrgId)
		
		//Step3:Add Services to Roles		
		Services candServiceRole1 = new Services(serviceName:EntityConvertor.CAND_SERVICE,
			isAccess:true,isDelete:true,isUpdate:true,isSelfGroup:false,isCreate:true)
		candServiceRole1.role = adminRole1
		candServiceRole1.save()
		
		Services candServiceRole2 = new Services(serviceName:EntityConvertor.CAND_SERVICE,
			isAccess:true,isDelete:true,isUpdate:true,isSelfGroup:true,isCreate:true)
		candServiceRole2.role = adminRole2
		candServiceRole2.save()
		

		//Step3: Add a AdminGroup
		String grp1Name = "admin"
		sessionFactory.currentSession.clear()
		Groups adminGroup1 =  new Groups(grpName:grp1Name,grpLabel:"canary admin")
		Groups adminGroup2 =  new Groups(grpName:grp1Name,grpLabel:"harvest admin")
		adminGroup1.createGroup (c1OrgId)
		adminGroup2.createGroup (c2OrgId)
		
		//Step3: Create Users and assign to groups
		
		def c1 =  Client.findByOrgId(c1OrgId)
		def c2 = Client.findByOrgId(c2OrgId)
		User u1 = new User(username:"mark@canary.com",password:"abcd")
		u1.parent = c1 
		u1.save()
		u1.assignGroup(grp1Name)

				User u2 = new User(username:"mark@harvest.com",password:"abcd")
		u2.parent = c2
		u2.save()
		u2.assignGroup(grp1Name)
		u2.assignRole( adminRole2.roleName)
		
		for (i in 1..10) {
			def alphaInst1 = new Alpha(c1:"Burt$i",c2:"Charles$i",pkey:"key$i",parent:c1).save()		
			def alphaInst2 = new Alpha(c1:"Bob$i",c2:"Cat$i",pkey:"bobkey$i",parent:c1).save()	
			def alphaInst3 = new Alpha(c1:"Zing$i",c2:"Zend$i",pkey:"key$i",parent:c2).save()
			def alphaInst4 = new Alpha(c1:"Zeta$i",c2:"Jones$i",pkey:"zetakey$i",parent:c2).save()
			if(!alphaInst1 || !alphaInst2 || !alphaInst3 || !alphaInst4)
				throw new Exception("Alpa records could not be initialized")
		}		
	}
 
	def initProd = {
		initDev()
	}

}


import org.codehaus.groovy.grails.commons.GrailsApplication
import grails.util.GrailsUtil
import grails.util.Environment
import com.canarylogic.focalpoint.contacts.VendorsMo
import com.canarylogic.focalpoint.contacts.ChildVendorsMo

import com.canarylogic.focalpoint.*;
import com.canarylogic.sing.*
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
	}
	 
	private def createClient(String orgId,String orgDesc) {
		Client c1 = new Client(orgName:orgDesc, orgId:orgId)
		c1.save()
	}
	def initDev = {		
		String c1OrgId = "canary-test-123",c1OrgName='canary'
		def client = Client.findByOrgId(c1OrgId)
		//if(!client)
			setupDevClient(c1OrgId,c1OrgName)
		
	}
		
	private def setupDevClient(String c1OrgId,String c1OrgName) {
		//Step1: Create Clients Canary and Harvest
		def client = Client.findByOrgId(c1OrgId)
		if(!client){
			createClient(c1OrgId,c1OrgName)
			createClient(c2OrgId,c2OrgName)
		}
		
		//Step2: Add a AdminRole
//		sessionFactory.currentSession.clear()
//		Role adminRole1 = new Role(roleName:"admin",label:"sample Desc")
//		Role adminRole2 = new Role(roleName:"admin",label:"sample desc")
//		adminRole1.addRole (c1OrgId)
//		adminRole2.addRole (c2OrgId)
		
		//Step3:Add Services to Roles		
//		Services candServiceRole1 = new Services(serviceName:EntityConvertor.CAND_SERVICE,
//			isAccess:true,isDelete:true,isUpdate:true,isSelfGroup:false,isCreate:true)
//		candServiceRole1.role = adminRole1
//		candServiceRole1.save()
//		
//		Services candServiceRole2 = new Services(serviceName:EntityConvertor.CAND_SERVICE,
//			isAccess:true,isDelete:true,isUpdate:true,isSelfGroup:true,isCreate:true)
//		candServiceRole2.role = adminRole2
//		candServiceRole2.save()
		

		//Step3: Add a AdminGroup
//		String grp1Name = "admin"
//		sessionFactory.currentSession.clear()
//		Groups adminGroup1 =  new Groups(grpName:grp1Name,grpLabel:"canary admin")
//		Groups adminGroup2 =  new Groups(grpName:grp1Name,grpLabel:"harvest admin")
//		adminGroup1.createGroup (c1OrgId)
//		adminGroup2.createGroup (c2OrgId)
		
		//Step3: Create Users and assign to groups
//		
//		def c1 =  Client.findByOrgId(c1OrgId)
//		def c2 = Client.findByOrgId(c2OrgId)
//		User u1 = new User(username:"mark@canary.com",password:"abcd")
//		u1.parent = c1 
//		u1.save()
//		u1.assignGroup(grp1Name)
//
//				User u2 = new User(username:"mark@harvest.com",password:"abcd")
//		u2.parent = c2
//		u2.save()
//		u2.assignGroup(grp1Name)
//		u2.assignRole( adminRole2.roleName)
//		
//		for (i in 1..10) {
//			def alphaInst1 = new Alpha(c1:"Burt$i",c2:"Charles$i",pkey:"key$i",parent:c1).save()		
//			def alphaInst2 = new Alpha(c1:"Bob$i",c2:"Cat$i",pkey:"bobkey$i",parent:c1).save()	
//			def alphaInst3 = new Alpha(c1:"Zing$i",c2:"Zend$i",pkey:"key$i",parent:c2).save()
//			def alphaInst4 = new Alpha(c1:"Zeta$i",c2:"Jones$i",pkey:"zetakey$i",parent:c2).save()
//			if(!alphaInst1 || !alphaInst2 || !alphaInst3 || !alphaInst4)
//				throw new Exception("Alpa records could not be initialized")
//		}		
		
//		for (i in 1..10) {
//			println "creating vendorsMo"
//			def vendorInst1 = new VendorsMo(pkey:"${new Date().time}company$i",c1:"myfirstVendorName$i",c2:"mylastVendorName",
//				c3:"true",c4:"false",c5:"alpharetta",c6:"ga",c7:"30022",c8:"java",c9:"has a strong foothold in atlanta",
//				c10:"true2",parent:client)
//			
//			println "Vendor parent assigent as $client.orgId"
//			if (!vendorInst1.hasErrors() && vendorInst1.save(flush: true)) {
//				def childVendorsMo = new ChildVendorsMo(pkey:"abc@gmail.com",c2:"hello",c3:"world",c4:"goodness")
//				childVendorsMo.parent = vendorInst1
//				println "new tableInst createed"
//				childVendorsMo.save(flush:true)
//				//Add some childs over htere
//				
//			}else{
//				vendorInst1.errors.each {
//					throw new Exception(it)
//			   }
//			}
//		}
	
		for( i in 1..10){
			def paramsMap=[suffix:"Mr",firstName:"manmohan$i",lastName:"singh$i",createdBy:"testUser",updatedBy:"testUser"]
			Contact c = new Contact(paramsMap)
			c.parent = client
			c.save(failOnError: true)
			ContactAddress aContactAddress = new ContactAddress(street:"oldmilton$i",city:"johnscreek")
			def contactAddressList = [aContactAddress]
			ContactDetails aContactDetails = new ContactDetails(contactType:"email",contactValue:"richtest@gmail.com$i",category:'home')
			def contactDetailsList = [aContactDetails]
			c.save(failOnError: true)
		}
		
		
		
	}
 
	def initProd = {
		initDev()
	}

}

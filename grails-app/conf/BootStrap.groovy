import com.canarylogic.focalpoint.Alpha
import org.codehaus.groovy.grails.commons.GrailsApplication
import grails.util.GrailsUtil
import grails.util.Environment

class BootStrap {

	def grailsApplication
 	def init = { servletContext ->
		 println "GrailsUtil.environment is $GrailsUtil.environment"
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
			   break
		 }
	}
 
    def destroy = {
    }
	
	
	def initTest = {
		new Alpha(c1:"shyam",c2:"sundar").save()
		new Alpha(c1:"shivaji",c2:"sardar").save()

	}
	 
	def initDev = {
		log.debug ("Setting up the Development Database")
		new Alpha(c1:"sgg",c2:"sar").save()
		new Alpha(c1:"devJohn",c2:"walker").save()
	}
 
	def initProd = {
	
	}

}

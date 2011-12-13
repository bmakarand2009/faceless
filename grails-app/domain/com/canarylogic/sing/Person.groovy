package com.canarylogic.sing

import java.util.Date;

import com.canarylogic.focalpoint.Client

class Person {

    def static XML_ELEMENT_MAP = [firstName:"firstName",lastName:"lastName",
                  count:"count",last_updated:"lastUpdated",date_created:"dateCreated",
                  address_list:"contactAddressList"]

	static hasMany = [contactAddressList:ContactAddress,contactDetailsList:ContactDetails]


	Date dateCreated, lastUpdated
	String createdBy
	String updatedBy
	Client parent
	
	
	String suffix
	String firstName
	String lastName

	String toString(){
		"$firstName $lastName ${(suffix) ? (suffix) :''}"
	}
	
	//static searchable = {
	//	contactDetailsList component: true
	//}

//	static mapping = {
//		contactAddresses cascade: "all-delete-orphan"
//	}
	static constraints = {
		parent(nullable:false)
		firstName(unique:['lastName', 'suffix','parent'])
		lastName(blank:false)
		suffix(blank:true)
	}

    def toXml(def builder,boolean isList=false){
      def mkp = builder.getMkp()
      builder.person(){
          id(id)
          firstName(firstName)
          mkp.comment("required")
          lastName(lastName)
          mkp.comment("required")
          address_list(){
              contactAddressList.each { aAddress ->
                  aAddress.toXml(builder)
              }
          }
//        if(!isList){
              contact_data_list(){
                  contactDetailsList.each { cdetails ->
                      cdetails.toXml(builder)
                  }
              }
//        }
          date_created(type:'datetime',dateCreated)
          last_updated(type:'datetime',lastUpdated)
          createdBy(createdBy)

      }
    }

     def createObj(def aMap){
         Person pBean = new Person()
         pBean.firstName = aMap.firstName
         pBean.lastName = aMap.lastName
         aMap.contactAddressList.each{ aAddress->
            aAddress.person = pBean
         }
         pBean.contactAddressList = aMap.contactAddressList
       //  pBean.save()
         return pBean
      }


    
}

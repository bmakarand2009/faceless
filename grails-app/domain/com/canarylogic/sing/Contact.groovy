package com.canarylogic.sing

import java.util.Date;

import com.canarylogic.focalpoint.Client

class Contact {

	static hasMany = [contactAddresses:ContactAddress,contactDetailsList:ContactDetails]
	
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
	
	static searchable = {
		contactDetailsList component: true
	}

	static mapping = {
		contactAddresses cascade: "all-delete-orphan"
	}
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
              contactAddresses.each { aAddress ->
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


    
}

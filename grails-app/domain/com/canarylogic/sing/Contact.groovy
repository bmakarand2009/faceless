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
	
	String getFullName() {
		"$firstName $lastName ${(suffix) ? (suffix) :''}"
	}
	
	
	static searchable = {
		contactDetailsList component: true
	}
	//static transients = ["fullName"]
//	
	static constraints = {
		parent(nullable:false)
	//	fullName(unique:'parent')
		firstName(blank:false)
		lastName(blank:false)
		suffix(blank:true)
	}

    
}

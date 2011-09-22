package com.canarylogic.sing

import java.util.Date;

import com.canarylogic.focalpoint.Client

class Contact {

	Date dateCreated, lastUpdated
	String createdBy
	String updatedBy
	Client parent
	
	
	String suffix
	String firstName
	String lastName
	transient String fullName
	
	
	String getFullName() {
		toString()
	}
	
	String toString() {
		"$firstName $lastName ${(suffix) ? (suffix) :''}"
	}
	static constraints = {
		fullName(unique:'parent',nullable:false)
		firstName(blank:false)
		lastName(blank:false)
		suffix(blank:true)
	}
	static hasMany = [contactDetailsList:ContactDetails, addressList:ContactAddress]

    
}

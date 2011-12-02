package com.canarylogic.sing

import java.util.Date;

import com.canarylogic.focalpoint.Client;

class Emp {

	Date dateCreated, lastUpdated
	String createdBy
	String updatedBy
	
	String suffix
	String firstName
	String lastName
	String myFullName
	
	
	String toString(){
		"$firstName $lastName ${(suffix) ? (suffix) :''}"
	}
	
    static constraints = {
		myFullName(nullable:false)
		
    }
}

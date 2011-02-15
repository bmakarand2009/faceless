package com.canarylogic.focalpoint

class UserMo {

		
	String userName
	String password
	
	String userDesc
	static hasMany =[groups:GroupMo]
	static belongsTo = GroupMo
		
    static constraints = {
		userName(unique:true,email:true) //note here, userName has to be unique thruout the db as the userName is email, and email belongs to org anyways
		userDesc(nullable:true)	
    }
}   

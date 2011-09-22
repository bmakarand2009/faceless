package com.canarylogic.sing

class ContactDetails {

	String contactType // is it email/webaddress/simpleaddrss/phonenumber
	String contactValue //value of te field, can be address too
	String category //if home/work/other
	String additionalInfo //in case of webaddress, it can be linkedin,facebook,bing,blog..
	static belongsTo = [contact:Contact]

    static constraints = {
		contactType(inList:['email','phone','website'])
		category(nullable:true)
		additionalInfo(nullable:true)
    }
}

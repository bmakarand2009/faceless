package com.canarylogic.sing

class ContactDetails {

	static searchable = {
	     root false
	}
	
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

    def toXml(def builder){
        def mkp = builder.getMkp()
        builder."$contactType"(){
            value(contactValue)
            category(category)
            mkp.comment("home | work| other")
            additionalInfo(additionalInfo)
        }
    }
}

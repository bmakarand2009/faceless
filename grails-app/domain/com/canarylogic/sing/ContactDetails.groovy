package com.canarylogic.sing

class ContactDetails  extends AbstractCanaryDomain{
    def static XML_ELEMENT_MAP=[value:"contactValue",category:"category",additionalInfo:"additionalInfo"]

	static searchable = {
	     root false
	}
	
	String contactType // is it email/webaddress/simpleaddrss/phonenumber
	String contactValue //value of te field, can be address too
	String category //if home/work/other
	String additionalInfo //in case of webaddress, it can be linkedin,facebook,bing,blog..


	static belongsTo = [person:Person]

    static constraints = {
		contactType(inList:['email','phone','website'])
		category(nullable:true)
		additionalInfo(nullable:true)
    }

    @Override
    def toXml(def builder,boolean isList=false){
        def mkp = builder.getMkp()
        builder."$contactType"(){
            id(id)
            value(contactValue)
            category(category)
            mkp.comment("home | work| other")
            additionalInfo(additionalInfo)
        }
    }

    static void saveBean(String xmlRootName,def aMap,def pBean, def parent, boolean isUpdateCall){
       pBean.contactType=xmlRootName.toLowerCase()
       if(isUpdateCall)
           pBean.save(failOnError:true)
    }


}

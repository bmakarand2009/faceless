package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

class ContactDetails  extends AbstractCanaryDomain implements Serializable{
    def static XML_ELEMENT_MAP=[value:"contactValue",category:"contactCategory",additionalInfo:"additionalInfo"]

//	static searchable = {
//	     root false
//	}
//
	String contactType // is it email/webaddress/simpleaddrss/phonenumber
	String contactValue //value of te field, can be address too
	String contactCategory //if home/work/other
	String additionalInfo //in case of webaddress, it can be linkedin,facebook,bing,blog..


	static belongsTo = [person:Person,company:Company]

    static constraints = {
		contactType(inList:['email','phone','website'])
        contactValue(validator: { val, obj ->
               boolean  isValid = false
               if(!isValid)
                   return 'duplicate ContactValue'
            })
        contactCategory(inList:['home','work','other'])
		additionalInfo(nullable:true)
        company(nullable: true)
        person(nullable: true)
    }

    @Override
    def toXml(def builder,boolean isList=false){
        def mkp = builder.getMkp()
        builder."$contactType"(){
            id(id)
            value(contactValue)
            category(contactCategory)
            mkp.comment("home | work| other")
            additionalInfo(additionalInfo)
        }
    }


    /*
      boolean isValid = obj?.person !=null || obj?.company!=null
               if(isValid){
                   def cList=null
                   if(obj?.person) cList = ContactDetails.findByPerson(obj.person)
                   else if(obj?.company) cList = ContactDetails.findByCompany(obj.company)
                   cList.each{
                       if(it.contactValue == val) isValid = false
                   }
               }
    */


    static void saveBean(String xmlRootName,def aMap,def pBean, def parent, boolean isUpdateCall){
       pBean.contactType=xmlRootName.toLowerCase()
       if(isUpdateCall)
           pBean.save(failOnError:true)
    }


    @Override
    boolean equals(other){
         if(! (other instanceof ContactDetails )){
             return false
         }
         boolean isTempBool =false
         if(this.person){
             isTempBool = other?.person == this.person
         }else if(this.company){
             isTempBool = other?.company == this.company
         }

         isTempBool && other?.id == this.id
    }

    @Override
	public int hashCode() {
        def builder = new HashCodeBuilder()
        builder.append(contactType).append(contactValue)
        if(this.company) builder.append(company)
        else if(this.person) builder.append(person)
        builder.toHashCode()
	}

    @Override
    String toString(){
        return "$contactCategory - $contactType:$contactValue"
    }


}

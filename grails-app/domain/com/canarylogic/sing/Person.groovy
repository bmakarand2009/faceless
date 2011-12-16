package com.canarylogic.sing

import java.util.Date;

import com.canarylogic.focalpoint.Client

class Person extends AbstractCanaryDomain{

    def static XML_ELEMENT_MAP = [firstName:"firstName",lastName:"lastName", suffix:"suffix",
                  count:"count", address_list:"contactAddressList",contact_data_list:"contactDetailsList"]  //"id:id

	static hasMany = [contactAddressList:ContactAddress,contactDetailsList:ContactDetails]

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



    @Override
    def toXml(def builder,boolean isList=true){
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
          contact_data_list(){
              contactDetailsList.each { cdetails ->
                  cdetails.toXml(builder)
              }
          }
          date_created(type:Constants.DATETIME_TYPE,dateCreated)
          last_updated(type:Constants.DATETIME_TYPE,lastUpdated)
          createdBy(createdBy)

      }
    }



    static void saveBean(String xmlRootName,def aMap,def pBean, def parent, boolean isUpdateCall){
        if(!pBean.parent)
             pBean.parent = parent
        if(aMap.contactAddressList){
            pBean.contactAddressList.each{
                if(!it.person) it.person = pBean
            }
        }

        if(aMap.contactDetailsList){
            pBean.contactDetailsList.each{
                if(!it.person) it.person = pBean
            }
        }

        pBean.save(failOnError:true,flush:true)
        pBean.errors.each{
            println it
        }

    }




    
}

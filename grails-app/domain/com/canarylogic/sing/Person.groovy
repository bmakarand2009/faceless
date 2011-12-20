package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

//TBD : Can be optimized by using osome one to Many Techniques for https://github.com/bjornerik/sandbox/
//https://mrpaulwoods.wordpress.com/2011/02/07/implementing-burt-beckwiths-gorm-performance-no-collections/

//hashcode and equals method need to be implemented to take the advantage of Hibernate Second level cache
class Person extends AbstractCanaryDomain implements Serializable{

    def static XML_ELEMENT_MAP = [firstName:"firstName",lastName:"lastName", suffix:"suffix",
                  count:"count", address_list:"contactAddressList",contact_data_list:"contactDetailsList"
                  ]  //"id:id

	static hasMany = [contactAddressList:ContactAddress,contactDetailsList:ContactDetails,
            taskList:Tasks,tagList:Tags, customFieldList:CustomFields]

	Client client
    Company company
	
	String firstName
	String lastName

    String createdBy
    String updatedBy



    def getOppMemberList(){
       OppMember.findAllByPerson(this)
    }

    def getNotesList(){
        Notes.findAllByOpportunity(this)
    }

    def beforeDelete() {
        OppMember.withNewSession(){
            oppMemberList*.delete()
        }
        Notes.withNewSession {
            notesList*.delete()
        }

    }

	static constraints = {
        firstName(nullable:false,blank:false, unique:['lastName','client'])
		lastName(blank:false)
        company(nullable:true)
        createdBy(editable:false)
        updatedBy(nullable:true)
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


    @Override
    boolean equals(other){
         if(! (other instanceof Person )){
             return false
         }
         other?.firstName == this.firstName &&
                 other?.lastName == this.lastName  &&
                    other?.client == this.client
    }

    @Override
    int hashCode(){
        def builder = new HashCodeBuilder()
        builder.append(firstName).append(lastName).append(client)
        builder.toHashCode()
    }

    @Override
    String toString(){
        "$firstName $lastName for client $client"
    }






    
}

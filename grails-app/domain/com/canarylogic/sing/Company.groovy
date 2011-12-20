package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

class Company extends AbstractCanaryDomain implements Serializable{


    Client client
    String companyName

    String createdBy
    String updatedBy


    static hasMany = [contactAddressList:ContactAddress,contactDetailsList:ContactDetails,
                        taskList:Tasks,tagList:Tags, customFieldList:CustomFields]

    def getPersonList(){
       Person.findAllByCompany(this,[sort:'firstName'])
    }

    def getOppMemberList(){
        OppMember.findAllByCompany(this)
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
       companyName(nullable:false,unique:'client')
       createdBy(editable:false)
       updatedBy(nullable:true)
    }



    //////     Standard methods
    @Override
    String toString(){
        "$companyName for client $client"
    }

    @Override
    boolean equals(other){
         if(! (other instanceof Company )){
             return false
         }
         other?.companyName == this.companyName &&
                    other?.client == this.client
    }
    @Override
    int hashCode(){
        def builder = new HashCodeBuilder()
        builder.append(companyName).append(client)
        builder.toHashCode()
    }

}

package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

class Opportunity extends AbstractCanaryDomain implements Serializable{

    static hasMany = [tagList:Tags,taskList:Tasks, customFieldList:CustomFields]


    Client client
    String createdBy
    String updatedBy

    String title
    Person person
    Company company
    String oppDesc
    Integer dealValue

    String dealType
    Integer dealPeriod
    OppCategory oppCategory


    def getNotesList(){
        Notes.findAllByOpportunity(this)
    }

    def getOppMemberList(){
        OppMember.findAllByOpportunity(this)
    }


    def beforeDelete() {
        Notes.withNewSession {
            notesList*.delete()
        }
        OppMember.withNewSession {
            oppMemberList*.delete()
        }
    }



    //static methods
    static constraints = {
        title(nullable:false)
        client(nullable:false)
        createdBy(editable:false)
        updatedBy(nullable:true)
        person(nullable: true)
        company(nullable: true)
        dealType(inList:['hourly','monthly','yearly','fixedbid'])
        oppCategory(nullable: true)

    }

     static mapping = {
        oppDesc type: 'text'
    }



    //////     Standard methods
    @Override
    String toString(){
        "$title for client $client"
    }

    @Override
    boolean equals(other){
         if(! (other instanceof Opportunity )){
             return false
         }
         other?.id == this.id
    }

    @Override
    int hashCode(){
        def builder = new HashCodeBuilder()
        builder.append(client).append(id)
        builder.toHashCode()
    }

}

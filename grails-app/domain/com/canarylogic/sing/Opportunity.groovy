package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

class Opportunity extends AbstractCanaryDomain implements Serializable{

    static hasMany = [taskList:Tasks, customFieldList:CustomFields]


    Client client
    String createdBy
    String updatedBy

    String title
    Person person
    Company company
    String oppDesc


    String probability
    Integer dealValue
    String dealType  //hourly,monthly,yearly
    Integer duration  //12

    Date expectedCloseDate
    Date actualCloseDate

    String status

    OppCategory oppCategory //Consulting,Training,Developemnt

    def getNotesList(){
        Notes.findAllByOpportunity(this)
    }

    def getOppMemberList(){
        OppMember.findAllByOpportunity(this)
    }


    Set<Tag> getTagList(){
        OpportunityTag.findAllByOpportunity(this).collect { it.tag } as Set
    }

    boolean hasTag(Tag tag) {
        OpportunityTag.countByOpportunityAndTag(this, tag) > 0
    }


    def beforeDelete() {
        Notes.withNewSession {
            notesList*.delete()
        }
        OppMember.withNewSession {
            oppMemberList*.delete()
        }
        OpportunityTag.withNewSession {
            OpportunityTag.removeAllWithOpportunity(this)
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
        status(inList: ['won','lost','pending','abadoned'])

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

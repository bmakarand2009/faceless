package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

class Cases extends AbstractCanaryDomain implements Serializable{

   static hasMany = [taskList:Tasks, customFieldList:CustomFields]

    String subject
    Client client
    String createdBy
    String updatedBy

    def getOppMemberList(){
           OppMember.findAllByOpportunity(this)
    }


    def getNotesList(){
            Notes.findAllByCases(this)
    }

    def beforeDelete() {
        Notes.withNewSession {
            notesList*.delete()
        }
        OppMember.withNewSession {
            oppMemberList*.delete()
        }
    }

    static constraints = {
        subject(unique: 'client')

    }

            //////     Standard methods
    @Override
    String toString(){
        "Case subject $subject for $client"
    }

    @Override
    boolean equals(other){
         if(! (other instanceof Cases )){
             return false
         }
        other?.client == this.client && other?.subject == this.subject
    }

    @Override
    int hashCode(){
        def builder = new HashCodeBuilder()
        builder.append(client).append(subject)
        builder.toHashCode()
    }


}

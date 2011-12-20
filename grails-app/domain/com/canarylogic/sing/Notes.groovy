package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

class Notes extends AbstractCanaryDomain implements Serializable{


    Opportunity opportunity
    Company company
    Person person
    String createdBy
    String updatedBy

    String note

    static constraints = {
        note(nullable: false)
        opportunity(nullable: true)
        company(nullable:true)
        person(nullable: true)
        createdBy(editable:false)
        updatedBy(nullable:true)


    }

    static mapping = {
        note type: 'text'
    }

        //////     Standard methods
    @Override
    String toString(){
        "Note with noteId $this.id"
    }

    @Override
    boolean equals(other){
         if(! (other instanceof Notes )){
             return false
         }
        boolean isTempBool =false
        if(person) isTempBool = other?.person == person
        else if(company)  isTempBool = other?.company == company
        else if(opportunity)  isTempBool = other?.opportunity == opportunity

        isTempBool && other?.id == this.id
    }

    @Override
    int hashCode(){
        def builder = new HashCodeBuilder()
        if(person) builder.append(person)
        else if(company) builder.append(company)
        else if(opportunity) builder.append(opportunity)
        builder.append(note)
        builder.toHashCode()
    }

}

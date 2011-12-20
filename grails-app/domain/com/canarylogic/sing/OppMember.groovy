package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

//Person / company can be member to many Oppurtunities

class OppMember  extends AbstractCanaryDomain implements  Serializable{

    Opportunity opportunity
    Cases cases

    Person person
    Company company
    OppMemberCategory memberCategory

    static constraints = {
        opportunity(nullable: true)
        person(nullable: true)
        company(nullable: true)
        cases(nullable:true)
        memberCategory(nullable: false)
    }

    @Override
    boolean equals(other){
         if(! (other instanceof OppMember )){
             return false
         }
         other?.opportunity == this.opportunity  && other?.memberCategory == this.memberCategory
    }

    @Override
    public int hashCode() {
        def builder = new HashCodeBuilder()
        if(opportunity)builder.append(opportunity)
        else if(cases) builder.append(cases)
        builder.append(memberCategory)
        builder.toHashCode()
    }

    @Override
    String toString(){
        return "$memberCategory - $opportunity"
    }

}

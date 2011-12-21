package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

/*
We can have some internal categories to show
Resources : people working on the case or opp, internal team
Other categories might include
--shortlisted,applied,interested
 */
class OppMemberCategory extends AbstractCanaryDomain implements  Serializable{
    static belongsTo = [client:Client]


    String mCategory
    static constraints = {
        mCategory(blank: false,unique: 'client')
    }


    @Override
    boolean equals(other){
         if(! (other instanceof OppMemberCategory )){
             return false
         }
         other?.client == this.client  && other?.mCategory == this.mCategory
    }

    @Override
    public int hashCode() {
        def builder = new HashCodeBuilder()
        builder.append(client).append(mCategory)
        builder.toHashCode()
    }

    @Override
    String toString(){
        return "$mCategory - $client"
    }



}

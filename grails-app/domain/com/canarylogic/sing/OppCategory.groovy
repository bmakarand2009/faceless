package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

/*
OppCategory e.g Consulting,Training,SoftwareDev etc.
 */
class OppCategory  extends AbstractCanaryDomain implements  Serializable{
    static belongsTo = [client:Client]
    String oCategory
    static constraints = {
        oCategory(blank: false,unique: 'client')
    }



    @Override
    boolean equals(other){
         if(! (other instanceof OppCategory )){
             return false
         }
         other?.client == this.client  && other?.oCategory == this.oCategory
    }

    @Override
    public int hashCode() {
        def builder = new HashCodeBuilder()
        builder.append(client).append(oCategory)
        builder.toHashCode()
    }

    @Override
    String toString(){
        return "$oCategory - $client"
    }


}

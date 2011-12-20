package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

class Tags extends AbstractCanaryDomain implements Serializable{

    String tagName
    Client client

    static belongsTo = [person:Person,company:Company,opportunity:Opportunity]




    static constraints = {
        tagName(unique: 'client')
        person(nullable: true)
        company(nullable: true)
        opportunity(nullable:true)

    }




    @Override
    boolean equals(other){
         if(! (other instanceof Tags )){
             return false
         }
        other?.client = client && other?.tagName == this.tagName

    }

    @Override
    public int hashCode() {
        def builder = new HashCodeBuilder()
        builder.append(tagName).append(client)
        builder.toHashCode()
    }

    @Override
    String toString(){
        return "$tagName - $client"
    }

}

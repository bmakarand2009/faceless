package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

class Tag extends AbstractCanaryDomain implements Serializable{

    String tagName
    Client client

    static constraints = {
        tagName(unique: 'client')
    }

    def beforeDelete() {
        CompanyTag.withNewSession {
            CompanyTag.remoteAllWithTag(this)
        }
        PersonTag.withNewSession {
            PersonTag.remoteAllWithTag(this)
        }
        CasesTag.withNewSession {
            CasesTag.remoteAllWithTag(this)
        }

    }

    @Override
    boolean equals(other){
         if(! (other instanceof Tag )){
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

package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

class Tag extends AbstractCanaryDomain implements Serializable{
    static XML_ELEMENT_MAP = [name:"name"]

    String name
    Client client

    static constraints = {
        name(unique: 'client')
    }

    def beforeDelete() {
        CompanyTag.withNewSession {
            CompanyTag.removeAllWithTag(this)
        }
        PersonTag.withNewSession {
            PersonTag.removeAllWithTag(this)
        }
        KaseTag.withNewSession {
            KaseTag.removeAllWithTag(this)
        }
    }

    @Override
    def toXml(def builder){
        builder."$SingUtils.TAG_ROOT"(){
            id(type:SingUtils.INTEGER_TYPE, id)
            name(name)
        }
    }

    static void saveBean(String xmlRootName,def aMap,def pBean, def client, boolean isUpdateCall){
        if(!isUpdateCall && !pBean.client) pBean.client = client
        pBean.save(failOnError:true)
    }

    @Override
    boolean equals(other){
         if(! (other instanceof Tag )){
             return false
         }
        other?.client = client && other?.name == this.name

    }

    @Override
    public int hashCode() {
        def builder = new HashCodeBuilder()
        builder.append(name).append(client)
        builder.toHashCode()
    }

    @Override
    String toString(){
        return "$name - $client"
    }

}

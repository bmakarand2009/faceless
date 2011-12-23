package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

class OppStatus extends AbstractCanaryDomain implements  Serializable{
    static XML_ELEMENT_MAP = [name:"name"]

    static constraints = {
        name(blank:false)
    }

    static belongsTo = [client:Client]

    String name

    def beforeDelete() {
        Opportunity.withNewSession {
            Opportunity.remoteAllWithOppStatus(this)
        }
    }

    @Override
    def toXml(def builder){
         builder."$SingUtils.OPP_STATUS_ROOT"(){
             id(type:SingUtils.INTEGER_TYPE, id)
             name(name)
         }
    }

    static void saveBean(String xmlRootName,def aMap,def pBean, def client, boolean isUpdateCall){
         if(!isUpdateCall) pBean.client = client
         pBean.save().save(failOnError:true)
    }


    @Override
    boolean equals(other){
        if(! (other instanceof OppStatus )){
            return false
        }
        other?.client == this.client  && other?.name == this.name
    }

   @Override
   public int hashCode() {
       def builder = new HashCodeBuilder()
       builder.append(client).append(name)
       builder.toHashCode()
   }

   @Override
   String toString(){
       return "OppStatus[name:$name,client:$client]"
   }

}

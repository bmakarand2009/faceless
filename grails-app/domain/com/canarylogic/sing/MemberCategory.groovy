package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

/*
We can have some internal categories to show
Resources : people working on the case or opp, internal team
Other categories might include
--shortlisted,applied,interested
 */
class MemberCategory extends AbstractCanaryDomain implements  Serializable{
    def static XML_ELEMENT_MAP = [name:"name"]

    static belongsTo = [client:Client]

    String name
    static constraints = {
        name(blank: false,unique: 'client')
    }

    def beforeDelete() {
        Member.withNewSession {
            Member.remoteAllWithMemberCategory(this)
        }
    }

    @Override
    def toXml(def builder){
        builder."$SingUtils.MEMBER_CATEGORY_ROOT"(){
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
         if(! (other instanceof MemberCategory )){
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
        return "$name - $client"
    }



}

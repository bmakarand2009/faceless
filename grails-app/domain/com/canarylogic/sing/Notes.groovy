package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

class Notes extends AbstractCanaryDomain implements Serializable{
    def static XML_ELEMENT_MAP = [body:"body",entity_id:"entityId",entity_type:"entityType"]
    static constraints = {
        body(nullable: false)
        opportunity(nullable: true)
        company(nullable:true)
        person(nullable: true)
        kase(nullable:true)
        createdBy(editable:false)
        updatedBy(nullable:true)

    }
    static minCriteria = [
          [ 'person' ],
          [ 'company' ],
          ['opportunity'] ,
          [ 'kase' ]
    ]

    static mapping = {
        body type: 'text'
    }

    String body

    Opportunity opportunity
    Company company
    Person person
    Kase kase

    String createdBy
    String updatedBy


    @Override
    def toXml(def builder){
       builder.note(){
           id(type:SingUtils.INTEGER_TYPE, id)
           body(body)
           entity_id(findEntityId())
           entity_type(findEntityType())
           updated_by(updatedBy)
           date_created(type:SingUtils.DATETIME_TYPE,dateCreated)
           last_updated(type:SingUtils.DATETIME_TYPE,lastUpdated)
       }
    }

    static void saveBean(String xmlRootName,def aMap,def pBean, def parent, boolean isUpdateCall){
        String entityId = aMap.entityId
        String entityType = aMap.entityType

        def entityObj = SingUtils.findEntityObj(entityId,entityType)
        def entityVar = SingUtils.findEntityVariable(entityType)
        if(entityObj && entityVar)
            pBean."$entityVar" = entityObj
        pBean.save(failOnError:true)
    }



        //////     Standard methods
    @Override
    String toString(){
        if(body){
            int len = body.length()>100?100:body.length()
            "Note ${this.body.substring(0,len)}"
        }else{
            "Note uninitialized yet"
        }
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
        builder.append(body)
        builder.toHashCode()
    }

}

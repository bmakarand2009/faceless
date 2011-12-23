package com.canarylogic.sing
import org.apache.commons.lang.builder.HashCodeBuilder

class CustomFieldsDefinition  extends  AbstractCanaryDomain implements Serializable{

    static XML_ELEMENT_MAP = [name:"name",description:"description",type:"type", options:"options",
                 sequence:"cSequence",validation_rule:"validationRule",entity_type:"entityType"]

    static belongsTo = [client:Client]

    static hasMany = [customFieldsList:CustomFields]

    static constraints = {
        name(blank:false)
        optionType(inList:['text','boolean','datetime','multichoice'])
        options(blank:true,nullable:true)
        description(nullable: true)
        validationRule(nullable: true)
        customFieldsList(nullable: true)
        updatedBy(nullable:true)
        entityType(inList:["Person","Company","Opportunity","Opportunity"])
   }


    String name
    String description
    String optionType //text,boolean,datetime,multichoice,generatedLink
    String options //in case list, it cabe a comma spearated string with allowed values
    Integer cSequence
    String validationRule
    String entityType

    String createdBy
    String updatedBy

    @Override
    def toXml(builder,isList=true){
       builder."$SingUtils.CUSTOM_FIELD_DEFINITION_ROOT"(){
           id(id)
           name(name)
           description(description)
           optionType(optionType)
           options(options)
           sequence(cSequence)
           validation_rule(validationRule)
           entity_type(entityType)
           date_created(type:SingUtils.DATETIME_TYPE,dateCreated)
           last_updated(type:SingUtils.DATETIME_TYPE,lastUpdated)
           created_by(createdBy)
           updated_by(updatedBy)
       }
    }

    static void saveBean(String xmlRootName,def aMap,def pBean, def parent, boolean isUpdateCall){
        if(!isUpdateCall)
            pBean.client = parent
        pBean.save(failOnError:true)
    }

    //////     Standard methods
    @Override
    String toString(){
        "name:$name,optionType:$optionType,entityType:$entityType"
    }

    @Override
    boolean equals(other){
         if(! (other instanceof CustomFieldsDefinition )){
             return false
         }
         other?.client == this.client && other?.id == this.id
    }

    @Override
    int hashCode(){
        def builder = new HashCodeBuilder()
        builder.append(name).append(client).append(entityType)
        builder.toHashCode()
    }


}


package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

//TBD if customFields should not have duplicate cLabel, a validator like which can check the count can be written for it
class CustomFields extends AbstractCanaryDomain implements Serializable {

    static XML_ELEMENT_MAP = [value:"value",custom_fields_definition_id:"customFieldsDefinitionId"]

    static belongsTo = [customFieldsDefinition:CustomFieldsDefinition,
            person:Person,company:Company,opportunity:Opportunity,kase:Kase]

    static constraints = {
        value(blank: false)
        person(nullable: true)
        company(nullable: true)
        opportunity(nullable: true)
        kase(nullable: true)
    }

    static minCriteria = [
          [ 'person' ],
          [ 'company' ],
          ['opportunity'] ,
          [ 'kase' ]
    ]

    String value

    @Override
    def toXml(def builder,boolean isListView){
        builder."$SingUtils.CUSTOM_FIELD_ROOT"(){
           id(type:SingUtils.INTEGER_TYPE, id)
           name(customFieldsDefinition.name)
           value(value)
       }
    }

    static void saveBean(String xmlRootName,def aMap,def pBean, def parent, boolean isUpdateCall){
       if(isUpdateCall){
           pBean.save(failOnError:true)
       }else{
           def customFieldsDefinition = CustomFieldsDefinition.get(aMap.customFieldsDefinitionId)
           pBean.customFieldsDefinition = customFieldsDefinition
       }
    }

    @Override
    boolean equals(other){
         if(! (other instanceof CustomFields )){
             return false
         }
         boolean isTempBool =false
         if(this.person)
             isTempBool = other?.person == this.person
         else if(this.company)
             isTempBool = other?.company == this.company
         else if(this.opportunity)
             isTempBool = other?.opportunity == this.opportunity
         else if(this.kase)
             isTempBool = other?.kase == this.kase
         isTempBool && other.value == this.value
    }

    @Override
    public int hashCode() {
        def builder = new HashCodeBuilder()
        if(this.company) builder.append(company)
        else if(this.person) builder.append(person)
        builder.append(id)
        builder.toHashCode()
    }

    @Override
    String toString(){
        return "customfield VALUE$value"
    }

}

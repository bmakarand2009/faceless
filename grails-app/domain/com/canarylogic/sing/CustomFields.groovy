package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

class CustomFields extends  AbstractCanaryDomain implements Serializable{


    static XML_ELEMENT_MAP = [customlabel:"clabel",customType:"cType", customValue:"cValue",
                 customDesc:"cDesc",sequence:"cSequence"]

    static belongsTo = [person:Person,company:Company,opportunity:Opportunity]

    String cLabel
    String cType //text,boolean,datetime,multichoice,generatedLink
    String cValue
    String cDesc
    Integer cSequence

    String createdBy
    String updatedBy


    static constraints = {
        cLabel(blank: false,)
        cDesc(nullable: true)
        person(nullable: true)
        company(nullable: true)
        opportunity(nullable:true)

    }





    @Override
    boolean equals(other){
         if(! (other instanceof CustomFields )){
             return false
         }
         boolean isTempBool =false
         if(this.person){
             isTempBool = other?.person == this.person
         }else if(this.company){
             isTempBool = other?.company == this.company
         }else if(this.opportunity)
             isTempBool = other?.opportunity == this.opportunity
         isTempBool && other.id == this.id

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
        return "$cLabel - $cValue"
    }


}

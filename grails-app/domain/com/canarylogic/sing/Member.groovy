package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

//Person / company can be member to many Oppurtunities

class Member extends AbstractCanaryDomain implements  Serializable{


    static constraints = {
        opportunity(nullable: true)
        person(nullable: true)
        company(nullable: true)
        kase(nullable:true)
        memberCategory(nullable:false)
    }

    static minCriteria = [
         ['opportunity','person'] ,
          ['opportunity','company'] ,
          [ 'kase','person' ],
          ['kase','company']
    ]

    Opportunity opportunity
    Kase kase

    Person person
    Company company
    MemberCategory memberCategory


    static void remoteAllWithMemberCategory(MemberCategory memberCategory){
        executeUpdate("DELETE FROM Member WHERE memberCategory=:memberCategory", [memberCategory: memberCategory])
    }

     @Override
    def toXml(def builder,boolean isListView){
        builder."$SingUtils.MEMBER_ROOT"(){
           id(type:SingUtils.INTEGER_TYPE, id)
           entity_id(type:SingUtils.INTEGER_TYPE,findEntityId())
           entity_type(findEntityType())
           memberCategory.toXml(builder)
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
      if(! (other instanceof Member )){
         return false
      }
      boolean  isValid
      if(opportunity) isValid = other?.opportunity == this.opportunity
      else if(kase) isValid = other?.kase == this.kase

      isValid && other?.memberCategory == this.memberCategory
    }

    @Override
    public int hashCode() {
        def builder = new HashCodeBuilder()
        if(opportunity)builder.append(opportunity)
        else if(kase) builder.append(kase)
        builder.append(memberCategory)
        builder.toHashCode()
    }

    @Override
    String toString(){
        return "$memberCategory - $opportunity"
    }

}

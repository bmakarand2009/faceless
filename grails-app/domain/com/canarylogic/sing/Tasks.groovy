package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

class Tasks extends AbstractCanaryDomain implements Serializable{

    static belongsTo = [person:Person,company:Company,opportunity:Opportunity,cases:Cases]

    String subject
    Date dueDate
    TaskType taskType


    static mapping = {
        subject type: 'text'
    }
    static constraints = {
        subject(blank:false,validator: { val, obj ->
               obj?.person !=null || obj?.company!=null || obj?.opportunity !=null || obj?.cases !=null
            })
        person(nullable: true)
        company(nullable: true)
        opportunity(nullable:true)
        cases(nullable:true)

    }




    @Override
    boolean equals(other){
         if(! (other instanceof Tasks )){
             return false
         }
         boolean isTempBool =false
         if(this.person){
             isTempBool = other?.person == this.person
         }else if(this.company){
             isTempBool = other?.company == this.company
         }

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
        return "$subject - $taskType"
    }


}

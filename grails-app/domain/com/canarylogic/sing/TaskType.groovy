package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

/*

to delete all the tasksListfrom a client
def c = TaskType.createCriteria().list {
   eq("client", client)
}*.delete()

*/
class TaskType extends AbstractCanaryDomain implements Serializable{

    static belongsTo = [client:Client]

    String typeName
    String typeDesc


    static constraints = {
        typeName(blank:false,unique:'client')
    }


    @Override
    boolean equals(other){
         if(! (other instanceof TaskType )){
             return false
         }
         other.client==this.client && other?.typeName == this.typeName
    }

    @Override
    public int hashCode() {
        def builder = new HashCodeBuilder()
        builder.append(client).append(typeName)
        builder.toHashCode()
    }

    @Override
    String toString(){
        return "$typeName - $client"
    }





}

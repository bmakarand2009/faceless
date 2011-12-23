package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

/*

to delete all the tasksListfrom a client
def c = TaskType.createCriteria().list {
   eq("client", client)
}*.delete()

*/
class TaskType extends AbstractCanaryDomain implements Serializable{

    static XML_ELEMENT_MAP = [name:"name",description:"description"]

    static belongsTo = [client:Client]

    static constraints = {
        name(blank:false,unique:'client')
        description(blank:true,nullable: true)
    }

    String name
    String description

    def beforeDelete() {
        Tasks.withNewSession {
            Tasks.removeAllWithTaskType(this)
        }
    }

    @Override
    def toXml(def builder){
        builder."$SingUtils.TASK_TYPE_ROOT"(){
            id(type:SingUtils.INTEGER_TYPE, id)
            name(name)
            description(description)
        }
    }

    static void saveBean(String xmlRootName,def aMap,def pBean, def client, boolean isUpdateCall){
        if(!isUpdateCall && !pBean.client) pBean.client = client
        pBean.save(failOnError:true)
    }

    @Override
    boolean equals(other){
         if(! (other instanceof TaskType )){
             return false
         }
         other.client==this.client && other?.name == this.name
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

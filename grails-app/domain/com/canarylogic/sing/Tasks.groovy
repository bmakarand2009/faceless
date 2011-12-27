package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

class Tasks extends AbstractCanaryDomain implements Serializable{

    def static XML_ELEMENT_MAP = [body:"body",due_at:"dueDate",
            task_type_id:"taskTypeId",entity_id:"entityId",entity_type:"entityType"]

    static belongsTo = [person:Person,company:Company,opportunity:Opportunity,kase:Kase]

    String body
    Date dueDate   //TBD period variable which say the dueDate Period is today,sevendays, likethat idea is to add a transient variable
    TaskType taskType

    String createdBy
    String updatedBy

    static mapping = {
        body type: 'text'
    }

    static constraints = {
        body(blank:false)
        person(nullable: true)
        company(nullable: true)
        opportunity(nullable:true)
        kase(nullable:true)
        createdBy(editable:false)
        updatedBy(nullable:true)
    }

    static minCriteria = [
          [ 'person' ],
          [ 'company' ],
          ['opportunity'],
          [ 'kase' ]
    ]


    static void removeAllWithTaskType(TaskType taskType) {
        executeUpdate("DELETE FROM Tasks WHERE taskType=:taskType", [taskType: taskType])
    }


    @Override
    def toXml(def builder, boolean isListView){
       builder."$SingUtils.TASK_ROOT"(){
           id(id)
           body(body)
           due_at(dueDate)
           entity_id(findEntityId())
           entity_type(findEntityType())
           taskType.toXml(builder)

           date_created(type:SingUtils.DATETIME_TYPE,dateCreated)
           last_updated(type:SingUtils.DATETIME_TYPE,lastUpdated)
           created_by(createdBy)
           updated_by(updatedBy)
       }
    }

    static void saveBean(String xmlRootName,def aMap,def pBean, def client, boolean isUpdateCall){
        if(aMap.taskTypeId){
            def tType = TaskType.get(aMap.taskTypeId)
            pBean.taskType = tType
        }
        String entityId = aMap.entityId
        String entityType = aMap.entityType

        def entityObj = SingUtils.findEntityObj(entityId,entityType)
        def entityVar = SingUtils.findEntityVariable(entityType)
        if(entityObj && entityVar)
            pBean."$entityVar" = entityObj

        pBean.save(client:client).save(failOnError:true)
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
        return "$body - $taskType"
    }


}

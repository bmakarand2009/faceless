package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

class Kase extends AbstractCanaryDomain implements Serializable{
   static XML_ELEMENT_MAP = [subject:"subject", custom_fields_list:"customFieldsList"]

   static hasMany = [taskList:Tasks, customFieldList:CustomFields]

   static constraints = {
        subject(unique: 'client')
   }


    String subject
    Client client
    String createdBy
    String updatedBy

    def getMemberList(){
       Member.findAllByKase(this)
    }


    def getNoteList(){
       Notes.findAllByKase(this)
    }

    Set<Tag> getTagList(){
        KaseTag.findAllByKase(this).collect { it.tag } as Set
    }

    boolean hasTag(Tag tag) {
        KaseTag.countByKaseAndTag(this, tag) > 0
    }


    def beforeDelete() {
        Notes.withNewSession {
            noteList*.delete()
        }
        Member.withNewSession {
            memberList*.delete()
        }
        KaseTag.withNewSession {
            KaseTag.removeAllWithKase(this)
        }

    }

    @Override
    def toXml(def builder,boolean isListView=true){
      def mkp = builder.getMkp()
      builder."$SingUtils.KASE_ROOT"(){
          id(type:SingUtils.INTEGER_TYPE, id)
          subject(subject)
          custom_fields_list(){
              customFieldsList.each { it.toXml(builder,isListView)}
          }
          if(!isListView){
              tag_list(){
                  tagList.each{it.toXml(builder,isListView)}
              }
              note_list(){
                  noteList.each{it.toXml(builder,isListView)}
              }
              task_list(){
                  taskList.each{ it.toXml(builder,isListView)}
              }
          }
          date_created(type:SingUtils.DATETIME_TYPE,dateCreated)
          last_updated(type:SingUtils.DATETIME_TYPE,lastUpdated)
          created_by(createdBy)
          updated_by(updatedBy)
      }
    }

    static void saveBean(String xmlRootName,def aMap,def pBean, def client, boolean isUpdateCall){
         if(!pBean.client)
              pBean.client = client

         if(aMap.customFieldsList){
             pBean.customFieldsList.each{
                 if(!it.person) it.person = pBean
             }
         }
         pBean.save(failOnError:true,flush:true)
     }


            //////     Standard methods
    @Override
    String toString(){
        "Kase subject $subject for $client"
    }

    @Override
    boolean equals(other){
         if(! (other instanceof Kase )){
             return false
         }
        other?.client == this.client && other?.subject == this.subject
    }

    @Override
    int hashCode(){
        def builder = new HashCodeBuilder()
        builder.append(client).append(subject)
        builder.toHashCode()
    }


}

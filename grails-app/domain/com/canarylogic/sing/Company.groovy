package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

class Company extends AbstractCanaryDomain implements Serializable{
    static XML_ELEMENT_MAP = [name:"companyName",
                      address_list:"contactAddressList",contact_data_list:"contactDetailsList",
                      custom_fields_list:"customFieldsList"]

    static constraints = {
       companyName(nullable:false,unique:'client')
       createdBy(editable:false)
       updatedBy(nullable:true)
    }

    Client client
    String companyName

    String createdBy
    String updatedBy


    static hasMany = [contactAddressList:ContactAddress,contactDetailsList:ContactDetails,
                        taskList:Tasks, customFieldList:CustomFields]

    def getPersonList(){
       Person.findAllByCompany(this,[sort:'firstName'])
    }

    def getMemberList(){
        Member.findAllByCompany(this)
    }

    def getNoteList(){
        Notes.findAllByCompany(this)
    }

    Set<Tag> getTagList(){
        CompanyTag.findAllByCompany(this).collect { it.tag } as Set
    }

    boolean hasTag(Tag tag) {
        CompanyTag.countByCompanyAndTag(this, tag) > 0
    }


    def beforeDelete() {
        Member.withNewSession(){
            memberList*.delete()
        }
        Notes.withNewSession {
            noteList*.delete()
        }
        CompanyTag.withNewSession {
            CompanyTag.removeAllWithCompany(this)
        }

    }

     @Override
    def toXml(def builder,boolean isListView=true){
      def mkp = builder.getMkp()
      builder.company(){
          id(type:SingUtils.INTEGER_TYPE, id)
          mkp.comment("required")
          name(companyName)
          address_list(){
              contactAddressList.each {it.toXml(builder,isListView)}
          }
          contact_data_list(){
              contactDetailsList.each{it.toXml(builder,isListView)}
          }
          if(!isListView){
              tag_list(){
                  tagList.each{it.toXml(builder,isListView)}
              }
              note_list(){
                  noteList.each{it.toXml(builder,isListView)}
              }
              task_list(){
                  taskList.each{
                      it.toXml(builder,isListView)
                  }
              }
              member_list(){
                  memberList.each{ it.toXml(builder,isListView)}
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
        if(aMap.contactAddressList){
            pBean.contactAddressList.each{
                if(!it.person) it.person = pBean
            }
        }
        if(aMap.contactDetailsList){
            pBean.contactDetailsList.each{
                if(!it.person) it.person = pBean
            }
        }
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
        "company:$companyName,client:$client"
    }

    @Override
    boolean equals(other){
         if(! (other instanceof Company )){
             return false
         }
         other?.companyName == this.companyName &&
                    other?.client == this.client
    }
    @Override
    int hashCode(){
        def builder = new HashCodeBuilder()
        builder.append(companyName).append(client)
        builder.toHashCode()
    }

}

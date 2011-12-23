package com.canarylogic.sing


import org.apache.commons.lang.builder.HashCodeBuilder

//TBD : Can be optimized by using osome one to Many Techniques for https://github.com/bjornerik/sandbox/
//https://mrpaulwoods.wordpress.com/2011/02/07/implementing-burt-beckwiths-gorm-performance-no-collections/

//hashcode and equals method need to be implemented to take the advantage of Hibernate Second level cache
class Person extends AbstractCanaryDomain implements Serializable{

    //XML_ELEMENT_MAP : lists of xmlfield mappings based on which objct will get created
    def static XML_ELEMENT_MAP = [firstName:"firstName",lastName:"lastName",
                  count:"count", address_list:"contactAddressList",contact_data_list:"contactDetailsList",
                  custom_fields_list:"customFieldsList"]

	static hasMany = [contactAddressList:ContactAddress,contactDetailsList:ContactDetails,
            taskList:Tasks,customFieldsList:CustomFields]

    static constraints = {
        firstName(blank:false, unique:['lastName','client'])
        lastName(blank:false)
        company(nullable:true)
        createdBy(editable:false)
        updatedBy(nullable:true)
        customFieldsList(nullable: true)
        contactAddressList(nullable: true)
        contactDetailsList(nullable: true)

    }


	Client client
    Company company
	
	String firstName
	String lastName

    String createdBy
    String updatedBy



    def getOppMemberList(){
       Member.findAllByPerson(this)
    }

    def getNotesList(){
        Notes.findAllByPerson(this)
    }

    Set<Tag> getTagList(){
        PersonTag.findAllByPerson(this).collect { it.tag } as Set
    }

    boolean hasTag(Tag tag) {
        PersonTag.countByPersonAndTag(this, tag) > 0
    }

    def beforeDelete() {
        Member.withNewSession(){
            oppMemberList*.delete()
        }
        Notes.withNewSession {
            notesList*.delete()
        }
        PersonTag.withNewSession {
            PersonTag.removeAllWithPerson(this)
        }

    }

    @Override
    def toXml(def builder,boolean isListView=true){
      def mkp = builder.getMkp()
      builder.person(){
          id(type:SingUtils.INTEGER_TYPE, id)
          mkp.comment("required")
          firstName(firstName)
          mkp.comment("required")
          lastName(lastName)
          address_list(){
              contactAddressList.each {it.toXml(builder,isListView)}
          }
          contact_data_list(){
              contactDetailsList.each { it.toXml(builder,isListView)}
          }
          custom_fields_list(){
              customFieldsList.each { it.toXml(builder,isListView)}

          }
          if(!isListView){
              tag_list(){
                  tagList.each{it.toXxml(builder,isListView)}
              }
              notes_list(){
                  notesList.each{it.toXml(builder,isListView)}
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
        pBean.errors.each{
            println it
        }

    }


    @Override
    boolean equals(other){
         if(! (other instanceof Person )){
             return false
         }
         other?.firstName == this.firstName &&
                 other?.lastName == this.lastName  &&
                    other?.client == this.client
    }

    @Override
    int hashCode(){
        def builder = new HashCodeBuilder()
        builder.append(firstName).append(lastName).append(client)
        builder.toHashCode()
    }

    @Override
    String toString(){
        "[firstName:$firstName,lastName:$lastName,client:$client"
    }






    
}

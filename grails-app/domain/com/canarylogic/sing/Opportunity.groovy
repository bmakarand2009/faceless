package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

class Opportunity extends AbstractCanaryDomain implements Serializable{

    static XML_ELEMENT_MAP = [name:"name", description:"description",probability:"probability",status:"status",
                    currency:"currency",value:"value",
                    duration_type:"durationType",duration:"duration",  entity_id:"entityId",entity_type:"entityType",
                    expected_close_date:"expectedCloseDate",opp_category_id:"oppCategoryId"]

    static hasMany = [taskList:Tasks, customFieldList:CustomFields]

    static constraints = {
        name(blank:false)
        description(blank:false)
        status(inList: ['won','lost','pending','abadoned'])
        probability(blank: true,nullable: true)
        expectedCloseDate(nullable: true)
        actualCloseDate(nullable: true)
        currency(blank:false,nullable: false)
        value(blank:true,nullable: true)
        durationType(inList:['hourly','monthly','yearly','fixed'])
        duration(blank:true,nullable: true)


        client(nullable:false)
        createdBy(editable:false)
        updatedBy(nullable:true)
        person(nullable: true)
        company(nullable: true)

    }
    static mapping = {
        description type: 'text'
    }
    static minCriteria = [
          [ 'person' ],
          [ 'company' ]
    ]


    Client client
    String createdBy
    String updatedBy

    String name
    String description
    String status
    String probability
    Date expectedCloseDate
    Date actualCloseDate

    String currency
    Integer value //totalValue = value*frequence if frequency is not fixed
    String  durationType  //hourly,monthly,yearly
    Integer duration  //12


    Person person //targetPerson
    Company company//or targetCompany

    OppCategory oppCategory //Consulting,Training,Developemnt

    def getNoteList(){
        Notes.findAllByOpportunity(this)
    }

    def getMemberList(){
        Member.findAllByOpportunity(this)
    }


    Set<Tag> getTagList(){
        OpportunityTag.findAllByOpportunity(this).collect { it.tag } as Set
    }

    boolean hasTag(Tag tag) {
        OpportunityTag.countByOpportunityAndTag(this, tag) > 0
    }


    def beforeDelete() {
        Notes.withNewSession {
            noteList*.delete()
        }
        Member.withNewSession {
            memberList*.delete()
        }
        OpportunityTag.withNewSession {
            OpportunityTag.removeAllWithOpportunity(this)
        }

    }

    static void remoteAllWithOppCategory(OppCategory oppCategory){
        executeUpdate("DELETE FROM OppCategory WHERE oppCategory=:oppCategory", [oppCategory: oppCategory])
    }

    static void remoteAllWithOppStatus(OppStatus oppStatus){
        executeUpdate("DELETE FROM OppStatus WHERE oppStatus=:oppStatus", [oppStatus: oppStatus])
    }


    @Override
    def toXml(def builder,boolean isListView=true){
       builder."$SingUtils.OPPORTUNITY_ROOT"(){
           id(id)
           name(name)
           description(description)
           probability(probability)
           status(status)
           currency(currency)
           value(value)
           duration_type(durationType)
           duration(duration)
           oppCategory.toXml(builder)
           entity_id(findEntityId())
           entity_type(findEntityType())

           custom_fields_list(){
               customFieldList.each{ it.toXml(builder,false) }
           }
           if(!isListView){
              tag_list(){
                  tagList.each{ it.toXml(builder,isListView)}
              }
              note_list(){
                  noteList.each{it.toXml(builder,isListView)}
              }
              task_list(){
                  taskList.each{ it.toXml(builder,isListView)}
              }
              mem ber_list(){
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
        pBean.errors.each{
            println it
        }

    }




    //////     Standard methods
    @Override
    String toString(){
        "$name for client $client"
    }

    @Override
    boolean equals(other){
         if(! (other instanceof Opportunity )){
             return false
         }
        boolean isTempBool =false
        if(person) isTempBool = other?.person == person
        else if(company)  isTempBool = other?.company == company

        isTempBool && other?.id == this.id
    }

    @Override
    int hashCode(){
        def builder = new HashCodeBuilder()
        builder.append(client).append(id)
        if(person) builder.append(person)
        else if(company) builder.append(company)
        builder.toHashCode()
    }

}

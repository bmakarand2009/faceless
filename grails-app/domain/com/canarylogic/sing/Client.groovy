package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

import com.canarylogic.focalpoint.Role
import com.canarylogic.focalpoint.Groups;

class Client  extends  AbstractCanaryDomain implements Serializable {
	
	String orgId
	String orgName
    String createdBy
    String updatedBy

	
	static hasMany=[roles:Role,groups:Groups,taskTypeList:TaskType,oppCategoryList:OppCategory,
    memberCategoryList:MemberCategory,oppStatus:OppStatus]
	
    static constraints = {
		orgId(blank:false,unique:true)
		orgName(blank:false,length:5)
        createdBy(editable:false)
        updatedBy(nullable:true)
        memberCategoryList(nullable: true)
        oppStatus(nullable: true)
    }

    def getPersonList(){
        Person.findAllByClient(this,[sort:'firstName'])
    }

    def getCompanyList(){
        Company.findAllByClient(this,[sort:'companyName'])
    }

    def getOpportunityList(){
        Opportunity.findAllByClient(this,[sort:'dateCreated'])
    }

    def getKaseList(){
        Kase.findAllByClient(this)
    }

    def getTagList(){
        Tag.findAllByClient(this,[sort:'dateCreated'])
    }

    def getCustomFieldsDefinitionList(){
        CustomFieldsDefinition.findAllByClient(this)
    }

    def beforeDelete() {
        Person.withNewSession(){
            personList*.delete()
        }
        Company.withNewSession(){
            companyList*.delete()
        }

        Opportunity.withNewSession {
            opportunityList*.delete()
        }
        Kase.withNewSession {
            kaseList*.delete()
        }
        Tag.withNewSession {
            tagList*.delete()
        }
        CustomFieldsDefinition.withNewSession {
            customFieldsDefinitionList*.delete()
        }
    }

    @Override
    String toString() {
        return "[OrgName:$orgName,OrgId: $orgId]"
    }


    @Override
    boolean equals(other){
         if(! (other instanceof Client )){
             return false
         }
         other?.orgId == this.orgId
    }

    @Override
    int hashCode(){
        def builder = new HashCodeBuilder()
        builder.append(orgId).append(id)
        builder.toHashCode()
    }

	
}

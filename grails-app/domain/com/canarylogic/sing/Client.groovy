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
    oppMemberCategoryList:OppMemberCategory]
	
    static constraints = {
		orgId(blank:false,unique:true)
		orgName(blank:false,length:5)
        createdBy(editable:false)
        updatedBy(nullable:true)
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

    def getTagList(){
        Tags.findAllByClient(this,[sort:'dateCreated'])
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
    }

    @Override
    String toString() {
        return "OrgName:$orgName and OrgId: $orgId"
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

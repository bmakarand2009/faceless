package com.canarylogic.focalpoint

import com.canarylogic.base.RestException;
import com.canarylogic.base.ExMessages;
/*
 * This servcie is used by FocalPoint to Setup Orgnanziation, Add Users, Groups Adn Roles
 */
class FocalAdminService {

    static transactional = false

    def serviceMethod() {
		return "passed"
    }
	
//	//paramsMap[orgName:"canryorg", orgId:"foc-12345"]
//	def setupOrganization(def paramsMap) {
//		OrganizationMo orgMo = new OrganizationMo()
//		orgMo.properties = paramsMap
//		orgMo.addToGroups(new GroupMo(grpName:"mygrp1", grpDesc:"grp Desc"))
//		orgMo.save()
//		OrganizationMo org1Mo = OrganizationMo.findByOrgName(paramsMap.orgName)
//		return org1Mo
//	}
//	//paramsMap[grpName:"Admin",groupDesc:"AdminGroup Desc"]
//	def createGroup(String orgId,def paramsMap) {
//		OrganizationMo orgMo = OrganizationMo.findByOrgId(orgId)
//		if(!orgMo )
//		    throw new RestException(ExMessages.INVALID_MO,"No Organization Found for $orgId")
//			
//		GroupMo grpMo  = new GroupMo()
//		grpMo.properties = paramsMap		
//		orgMo.addToGroups(grpMo)
//		orgMo.save()
//
//	}
//	
//
//	GroupMo getGroup(String orgId, String grpName) {
//		log.debug "getGroup called for $grpName"
//		GroupMo grpMo = GroupMo.findByGrpName(grpName)
//		return grpMo
//	}
}

package com.canarylogic.sing


class PartnerService {

    def listRecords(Client parent, Class domainClz, def params){
//		def tableClass = domainName.class
		String orderType=params.order?params.String(order):'asc'
		def max = params.max?params.max.toInteger():10
		def offset = params.offset?params.offset:0
		String orderField = params.orderField?params.String(orderField):'id'
		
		def resultList = domainClz.withCriteria {
			maxResults(max)
			firstResult(offset)
			order(orderField,orderType)
			and{
				eq('client', parent)
			}
//			or {
//				params.each{k,v->
//					like(k,"%$v%")
//				}
//			 }
        }
        return resultList
		
	}

    def createOrUpdate(boolean isCreate,Client client,def user,def xmlBody){
        try{
            def proot = new XmlParser().parseText(xmlBody)
            Integer curId=null
            if(!isCreate){
                curId = proot.id.text().toInteger()
                if(!curId)
                    throw new Exception("No Object found for $curId, Update Operation Failed")
            }

            def domainObj = pxmlClosure(proot,client,user,curId)
            return domainObj
        }catch(Exception ex){
            throw new Exception(ex.message)
        }
    }


    def domainObjectsMap =
      ["$SingUtils.PERSON_ROOT":Person, "$SingUtils.COMPANY_ROOT":Company,
              "$SingUtils.OPPORTUNITY_ROOT":Opportunity,"$SingUtils.KASE_ROOT":Kase,
              "$SingUtils.CUSTOM_FIELD_DEFINITION_ROOT":CustomFieldsDefinition,
              "$SingUtils.CUSTOM_FIELD_ROOT":CustomFields,
              "$SingUtils.TAG_ROOT":Tag, "$SingUtils.MEMBER_ROOT":Member,
              "$SingUtils.MEMBER_CATEGORY_ROOT":MemberCategory,
              "$SingUtils.TASK_TYPE_ROOT":TaskType,
              "$SingUtils.PERSON_TAG_ROOT":PersonTag,
              "$SingUtils.COMPANY_TAG_ROOT":CompanyTag,
              "$SingUtils.OPPORTUNITY_TAG_ROOT":OpportunityTag,
              "$SingUtils.KASE_TAG_ROOT":KaseTag,
                address:ContactAddress,email: ContactDetails,
                note:Notes,task:Tasks]





    def pxmlClosure = { pRoot,parentObj,user,curId ->
        def xmlMap=[:]
      //  def clzLoader = this.class.classLoader
        def rootName = pRoot.name()
       // def domainClzName = domainObjectsMap."${rootName}"
       // def domainClz = clzLoader.loadClass(domainClzName)
        def domainClz = domainObjectsMap."${rootName}"
        log.debug "domainc lass loaded as ${domainClz}"

        def curObj=null
        if(curId) {
            curObj = domainClz.get(curId)
        }


        def xmlElementsMap = domainClz.XML_ELEMENT_MAP
        xmlElementsMap.each{k,v->
            def childElem = pRoot."$k"[0]
            if(childElem){
                String childElemName = childElem.name()
                log.debug "going throught childelements of list $childElemName"
                def childElemVal = childElem.value()[0]
                String aType = childElem.attribute("type")
                if(!aType){
                   if(childElemName.endsWith("_list")){
                       def xmlListObj=[]
                       childElem.children().each{ it ->
                          Integer listObjId= null
                          String  listObjIdStr = it.id.text()
                          if(listObjIdStr) listObjId = listObjIdStr.toInteger()
                          xmlListObj<<pxmlClosure(it,curObj,user,listObjId)
                       }
                      xmlMap."$v" = xmlListObj
                   }else
                     xmlMap."$v" = childElemVal
                }
                else if(aType==SingUtils.INTEGER_TYPE) xmlMap."$v" = childElemVal.toInteger()
                else if(aType==SingUtils.DATETIME_TYPE)xmlMap."$v" = new Date().parse("yyyy-M-d H:m:s",childElemVal.toString())

            }
        }
        def aObj=null
        if(xmlMap)
           aObj = domainClz.createOrUpdate(rootName,domainClz,xmlMap,curObj,parentObj,user)
        else
           log.debug "No xmlMap properties found,Make sure XML_ELEMENT_MAP of $domainClz has the correct Mappings "
        return aObj
    }

    //status = "403"
    private def sendValidationFailedResponse(customer, status) {
        response.status = status
        render contentType: "application/xml", {
            errors {
                customer?.errors?.fieldErrors?.each {err ->
                    field(err.field)
                    message(g.message(error: err))
                }
            }
        }
    }

	
}

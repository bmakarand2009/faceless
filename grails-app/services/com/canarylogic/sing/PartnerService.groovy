package com.canarylogic.sing

import org.springframework.transaction.annotation.Transactional


class PartnerService {

    def VALID_CHILD_DOMAINS = ["person","company",
              "opportunity","kase"]




    def createOrUpdate(boolean isCreate,Client client,def user,def xmlBody){
          try{
              def proot = new XmlParser().parseText(xmlBody)
              Integer curId=null
              if(!isCreate){
                  curId = proot.id.text().toInteger()
                  if(!curId)
                      throw new Exception("No Object found for $curId, Update Operation Failed")
              }

              def domainObj = parseXmlClosure(proot,client,user,curId)
              return domainObj
          }catch(Exception ex){
              throw new Exception(ex.message)
          }
    }

    def getRecord(Client parent,def params){
        def domainClz = SingUtils.DOMAIN_OBJECTS_MAP.get(params.domain)
        def resultObj = domainClz.get(params.entityId)
        if(!resultObj)
            throw new Exception("No Object found for domain:$params.domain and entityId:$params.entityId")
        return resultObj
    }



 // /tag/person/id  : list all the tags for the person with person id
    // /tag/person : list all the person tags
    @Transactional(readOnly = true)
    def listRecords(Client parent, def params){
        def resultList
        if(params.entityOrAction || params.entityId)
              resultList = listChildrenRecords(parent,params)
        else
            resultList = listDomainRecords(parent,params)

        return resultList
	}

    private def listDomainRecords(def client,def params){
        def domainClz = SingUtils.DOMAIN_OBJECTS_MAP."$params.domain"
        String orderType=params.order?params.String(params.order):'asc'
        def max = params.max?params.max.toInteger():10
        def offset = params.offset?params.offset:0
        String orderField = params.orderField?params.orderField:'id'

        def resultList = dom ainClz.withCriteria {
             maxResults(max)
             firstResult(offset)
             order(orderField,orderType)
             and{
                 eq('client', client)
             }
 //			or {
 //				params.each{k,v->
 //					like(k,"%$v%")
 //				}
 //			 }
        }
        return resultList
    }

    //list all the tag/note/tasK for a particular entity e.g person id representing john
    private def listChildrenRecords(def client,def params){
        String parentDomainName = params.domain
        if (!VALID_CHILD_DOMAINS.contains(params.entityOrAction) )
            throw new Exception("Invalid Child domain ${params.entityOrAction} for parent $parentDomainName")
        def childDomainClz =  SingUtils.DOMAIN_OBJECTS_MAP."${params.entityOrAction}"
        if(!childDomainClz) throw new Exception("Invalid entityName ${params.entityOrAction}  passed for domain${params.domain} ")

        def resultList
        if(params.entityId)
            resultList = childDomainClz.get(params.entityId)?."${parentDomainName}List"
        else{
            println "this cases where entityId is not there needs to be identitfied"
            if(parentDomainName==SingUtils.TAG_ROOT) getTagListForChildDomain(params.entityOrAction,client)
            else if(parentDomainName == SingUtils.CUSTOM_FIELD_DEFINITION_ROOT)
                resultList = CustomFieldsDefinition.findAllByClientAndEntityType(client,params.entityOrAction)
            else{
                def domainClz = SingUtils.DOMAIN_OBJECTS_MAP."$params.domainName"
                resultList = domainClz.createCriteria().list{
                "$params.entityOrAction"{
                    eq('client', client)
                 }
                }
            }
        }

        return  resultList
    }

    //url /tag/person or /tag/company..
    private def getTagListForChildDomain(def childDomain, def client){
          def resultList
          if(childDomain == SingUtils.PERSON_ROOT)
              resultList = PersonTag.list().findAll{client in it.person.client}
            else if(childDomain == SingUtils.COMPANY_ROOT)
              resultList = CompanyTag.list().findAll{client in it.company.client}
            else if(childDomain == SingUtils.KASE_ROOT)
              resultList = KaseTag.list().findAll{client in it.kase.client}
            else if(childDomain == SingUtils.OPPORTUNITY_ROOT)
              resultList = OpportunityTag.list().findAll{client in it.opportunity.client}
          return resultList
    }






    private def parseXmlClosure = { pRoot,parentObj,user,curId ->
        def xmlMap=[:]
        def rootName = pRoot.name()
        def domainClz = SingUtils.DOMAIN_OBJECTS_MAP."${rootName}"
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
                          xmlListObj<<parseXmlClosure(it,curObj,user,listObjId)
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

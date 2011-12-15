package com.canarylogic.sing
import com.canarylogic.focalpoint.Client
class PartnerService {

    def listRecords(Client parent, Object domainName, def params){
		def tableClass = domainName.class
		String orderType=params.order?params.String(order):'asc'
		def max = params.max?params.max:10
		def offset = params.offset?params.offset:0
		String orderField = params.orderField?params.String(orderField):'id'
		
		def resultList = tableClass.withCriteria {
			maxResults(max)
			firstResult(offset)
			order(orderField,orderType)
			and{
				eq('parent', parent)
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





    def domainObjectsMap=[person:"com.canarylogic.sing.Person",
            address:"com.canarylogic.sing.ContactAddress",
            email: "com.canarylogic.sing.ContactDetails"]

    def pxmlClosure = { pRoot,parentObj,user,curId ->
        def xmlMap=[:]
        def clzLoader = this.class.classLoader
        def rootName = pRoot.name()
        def domainClzName = domainObjectsMap."${rootName}"
        def domainClz = clzLoader.loadClass(domainClzName)
        log.debug "domainc lass loaded as ${domainClzName}"

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
                else if(aType==Constants.INTEGER_TYPE) xmlMap."$v" = childElemVal.toInteger()
                else if(aType==Constants.DATETIME_TYPE)xmlMap."$v" = new Date().parse("yyyy-M-d H:m:s",childElemVal.toString())

            }
        }

        def aObj = domainClz.createOrUpdate(rootName,domainClz,xmlMap,curObj,parentObj,user)
        return aObj
    }
	
}

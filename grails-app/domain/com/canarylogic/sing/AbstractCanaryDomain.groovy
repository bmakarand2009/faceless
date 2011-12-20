package com.canarylogic.sing

abstract class AbstractCanaryDomain {

  Date dateCreated  // grails will auto timestamp
  Date lastUpdated  // grails will auto timestamp


  static constraints = {
     dateCreated(editable:false)
     lastUpdated(editable:false)

  }

  def toXml(def builder,boolean isList=true){
      def mkp = builder.getMkp()
      builder.ErrorMessage(){
          message("Conversion of the object is not supported yet, pls contact admin")
          mkp.comment("Need to get this fixed")
      }
  }


  def static createOrUpdate(String xmlRootName,def clazz,def aMap,def curObj,def parent, def user){
     boolean isUpdateCall = curObj ? true : false
     def pBean = isUpdateCall ? curObj : clazz.newInstance()
     if(!pBean)
        throw new Exception("No Person found for")

     aMap.remove("id")//remove id since we dont want to use it
     aMap.updatedBy = user

     if(!curObj)
         aMap.createdBy = user

     //this might throw some error as updatedby is nolonger in the base class
     aMap.each{ colName,colVal->
           pBean."$colName" = colVal
     }
     clazz.saveBean(xmlRootName,aMap,pBean,parent,isUpdateCall)
     return pBean
  }


}

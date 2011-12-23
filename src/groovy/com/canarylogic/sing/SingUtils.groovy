package com.canarylogic.sing

/**
 * Created by IntelliJ IDEA.
 * User: bmakarand
 * Date: 12/13/11
 * Time: 10:14 PM
 * To change this template use File | Settings | File Templates.
 */
class SingUtils {
    static String INTEGER_TYPE="integer"
    static String DATETIME_TYPE="datetime"

    static String PERSON_TYPE = "Person"
    static String COMPANY_TYPE = "Company"
    static String OPPORTUNITY_TYPE="Opportunity"
    static String KASE_TYPE="Kase"



    //xml root tags
    static String PERSON_ROOT="person"
    static String COMPANY_ROOT="company"
    static String KASE_ROOT="kase"
    static String OPPORTUNITY_ROOT="opportunity"
    static String EMAIL_ROOT="email"
    static String CUSTOM_FIELD_ROOT="custom_field"
    static String CUSTOM_FIELD_DEFINITION_ROOT="custom_fields_definition"

    static String TAG_ROOT="tag"
    static String PERSON_TAG_ROOT="person_tag"
    static String COMPANY_TAG_ROOT="company_tag"
    static String KASE_TAG_ROOT="kase_tag"

    static String OPP_STATUS_ROOT="opp_status"
    static String OPP_CATEGORY_ROOT="opp_category"
    static String OPPORTUNITY_TAG_ROOT="opp_tag"




    static String MEMBER_ROOT="member_category"
    static String MEMBER_CATEGORY_ROOT="member_category"
    static String TASK_TYPE_ROOT="task_type"






    static def findEntityObj(entityId,entityType){
        def clzLoader = SingUtils.classLoader
        def domainClz = clzLoader.loadClass("com.canarylogic.sing.$entityType")
        domainClz.get(entityId)
    }

    static def findEntityVariable(entityType){
        if(entityType == SingUtils.COMPANY_TYPE) "company"
        else if(entityType == SingUtils.PERSON_TYPE) "person"
        else if(entityType == SingUtils.OPPORTUNITY_TYPE) "opportunity"
        else if(entityType == SingUtils.KASE_TYPE) "kase"
    }


}

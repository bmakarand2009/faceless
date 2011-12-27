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

    //xml root tags
    static String PERSON_ROOT="person"
    static String COMPANY_ROOT="company"
    static String KASE_ROOT="kase"
    static String OPPORTUNITY_ROOT="opportunity"
    static String EMAIL_ROOT="email"      //CANNOT BE changed just by changing here
    static String CUSTOM_FIELD_ROOT="custom_field"
    static String CUSTOM_FIELD_DEFINITION_ROOT="custom_fields_definition"

    static String TAG_ROOT="tag"
    static String PERSON_TAG_ROOT="person_tag"
    static String COMPANY_TAG_ROOT="company_tag"
    static String KASE_TAG_ROOT="kase_tag"

    static String OPP_STATUS_ROOT="opp_status"
    static String OPP_CATEGORY_ROOT="opp_category"
    static String OPPORTUNITY_TAG_ROOT="opp_tag"
    static String ADDRESS_TAG_ROOT="address"


    static String MEMBER_ROOT="member_category"
    static String MEMBER_CATEGORY_ROOT="member_category"

    static String TASK_TYPE_ROOT="task_type"
    static String TASK_ROOT="task"
    static String NOTE_ROOT="note"


    //these keys should come from keys above but due to some class loading issues
    //maps dont work the right way hence doing some manual keys again
//    def  DOMAIN_OBJECTS_MAP =
//      ["$SingUtils.PERSON_ROOT":Person, "$SingUtils.COMPANY_ROOT":Company,
//              "$SingUtils.OPPORTUNITY_ROOT":Opportunity,"$SingUtils.KASE_ROOT":Kase,
//              "$SingUtils.CUSTOM_FIELD_DEFINITION_ROOT":CustomFieldsDefinition,
//              "$SingUtils.CUSTOM_FIELD_ROOT":CustomFields,
//              "$SingUtils.TAG_ROOT":Tag, "$SingUtils.MEMBER_ROOT":Member,
//              "$SingUtils.MEMBER_CATEGORY_ROOT":MemberCategory,
//              "$SingUtils.TASK_TYPE_ROOT":TaskType,
//              "$SingUtils.PERSON_TAG_ROOT":PersonTag,
//              "$SingUtils.COMPANY_TAG_ROOT":CompanyTag,
//              "$SingUtils.OPPORTUNITY_TAG_ROOT":OpportunityTag,
//              "$SingUtils.KASE_TAG_ROOT":KaseTag,
//              "$SingUtils.ADDRESS_TAG_ROOT":ContactAddress,
//               "$SingUtils.EMAIL_ROOT": ContactDetails,
//               "$SingUtils.NOTE_ROOT":Notes,
//               "$SingUtils.TASK_ROOT":Tasks]

      def static DOMAIN_OBJECTS_MAP =
      [person:Person, company:Company,
              opportunity:Opportunity,kase:Kase,
              custom_fields_definition:CustomFieldsDefinition,
              custom_field:CustomFields,
              tag:Tag, member:Member,
              member_category:MemberCategory,
              task_type:TaskType,
              person_tag:PersonTag,
              company_tag:CompanyTag,
              opp_tag:OpportunityTag,
              kase_tag:KaseTag,
              address:ContactAddress,
               email: ContactDetails,
               note:Notes,
               task:Tasks]


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

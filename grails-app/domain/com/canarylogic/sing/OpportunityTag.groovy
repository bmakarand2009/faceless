package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

class OpportunityTag extends AbstractCanaryDomain implements Serializable{
    static XML_ELEMENT_MAP = [entity_id:"entityId",tag_id:"tagId"]

    static mapping = {
        table('opportunity_tag')
        version(false)
        id composite:['opportunity','tag']
    }

    static constraints = {
    }

    Opportunity opportunity
    Tag   tag


    static OpportunityTag create(Opportunity opportunity, Tag tag, boolean flush = false) {
        OpportunityTag opportunityTag = new OpportunityTag(opportunity:opportunity, tag:tag)
        opportunityTag.save(flush: true, insert: true)
        return opportunityTag
    }

    static boolean remove(Opportunity opportunity, Tag tag, boolean flush = false) {
       OpportunityTag opportunityTag = OpportunityTag.findByOpportunityAndTag(opportunity, tag)
       return opportunityTag ? opportunityTag.delete(flush: flush) : false
    }

    static void removeAllWithOpportunity(Opportunity opportunity) {
        executeUpdate("DELETE FROM OpportunityTag WHERE opportunity=:opportunity", [opportunity: opportunity])
    }

    static void removeAllWithTag(Tag tag){
        executeUpdate("DELETE FROM OpportunityTag WHERE tag=:tag", [tag: tag])
    }

    //!@toXml method is not available since we are not going to come here but go to Tags direcltly
    static void saveBean(String xmlRootName,def aMap,def pBean, def client, boolean isUpdateCall){
        pBean.person = Opportunity.get(aMap.entityId)
        pBean.tag    = Tag.get(aMap.tagId)
        pBean.save(failOnError:true)
    }



    @Override
    String toString(){
        return "$opportunity $tag"
    }

    @Override
    boolean equals(other){
        other?.opportunity == this.opportunity && other?.tag == this.tag
    }

    @Override
    int hashCode(){
        def builder = new HashCodeBuilder()
        builder.append(opportunity).append(tag)
        builder.toHashCode()

    }
}

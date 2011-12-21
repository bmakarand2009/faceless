package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

class OpportunityTag extends AbstractCanaryDomain implements Serializable{
    Opportunity opportunity
    Tag   tag

    static mapping = {
        table('opportunity_tag')
        version(false)
        id composite:['opportunity','tag']
    }

    static OpportunityTag create(Opportunity opportunity, Tag tag, boolean flush = false) {
        OpportunityTag opportunityTag = new OpportunityTag(opportunity:opportunity, tag:tag)
        opportunityTag.save(flush: flush, insert: true)
        return opportunityTag
    }

    static boolean remove(Opportunity opportunity, Tag tag, boolean flush = false) {
       OpportunityTag opportunityTag = OpportunityTag.findByOpportunityAndTag(opportunity, tag)
       return opportunityTag ? opportunityTag.delete(flush: flush) : false
    }

    static void removeAllWithOpportunity(Opportunity opportunity) {
        executeUpdate("DELETE FROM OpportunityTag WHERE opportunity=:opportunity", [opportunity: opportunity])
    }

    static void remoteAllWithTag(Tag tag){
        executeUpdate("DELETE FROM OpportunityTag WHERE tag=:tag", [tag: tag])
    }

    static constraints = {
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

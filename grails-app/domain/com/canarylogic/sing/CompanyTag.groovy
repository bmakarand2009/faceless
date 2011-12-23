package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

class CompanyTag extends AbstractCanaryDomain implements Serializable{
    static XML_ELEMENT_MAP = [entity_id:"entityId",tag_id:"tagId"]

    static mapping = {
        table('company_tag')
        version(false)
        id composite:['company','tag']
    }

    static constraints = {
    }

    Company company
    Tag   tag

    static CompanyTag create(Company company, Tag tag, boolean flush = false) {
        CompanyTag companyTag = new CompanyTag(company:company, tag:tag)
        companyTag.save(flush: flush, insert: true)
        return companyTag
    }

    static boolean remove(Company company, Tag tag, boolean flush = false) {
       CompanyTag companyTag = CompanyTag.findByCompanyAndTag(company, tag)
       return companyTag ? companyTag.delete(flush: flush) : false
    }

    static void removeAllWithCompany(Company company) {
        executeUpdate("DELETE FROM CompanyTag WHERE company=:company", [company: company])
    }

    static void removeAllWithTag(Tag tag){
        executeUpdate("DELETE FROM CompanyTag WHERE tag=:tag", [tag: tag])
    }

    //!@toXml method is not available since we are not going to come here but go to Tags direcltly
    static void saveBean(String xmlRootName,def aMap,def pBean, def client, boolean isUpdateCall){
        pBean.person = Kase.get(aMap.entityId)
        pBean.tag    = Tag.get(aMap.tagId)
        pBean.save(failOnError:true)
    }

    @Override
    String toString(){
        "[company:$company,tag:$tag]"
    }

    @Override
    boolean equals(other){
        other?.company == this.company && other?.tag == this.tag
    }

    @Override
    int hashCode(){
        def builder = new HashCodeBuilder()
        builder.append(company).append(tag)
        builder.toHashCode()
    }
}

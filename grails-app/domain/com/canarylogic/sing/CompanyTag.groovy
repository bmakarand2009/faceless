package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

class CompanyTag extends AbstractCanaryDomain implements Serializable{

    Company company
    Tag   tag

    static mapping = {
        table('company_tag')
        version(false)
        id composite:['company','tag']
    }



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

    static void remoteAllWithTag(Tag tag){
        executeUpdate("DELETE FROM CompanyTag WHERE tag=:tag", [tag: tag])
    }




    static constraints = {
    }

    @Override
    String toString(){
        return "$company $tag"
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

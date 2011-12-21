package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

/**
 * Created by IntelliJ IDEA.
 * User: bmakarand
 * Date: 12/20/11
 * Time: 10:42 PM
 * To change this template use File | Settings | File Templates.
 */
class CasesTag  extends AbstractCanaryDomain implements Serializable{
    Cases cases
    Tag   tag

    static mapping = {
        table('cases_tag')
        version(false)
        id composite:['cases','tag']
    }



    static CasesTag create(Cases cases, Tag tag, boolean flush = false) {
        CasesTag casesTag = new CasesTag(cases:cases, tag:tag)
        casesTag.save(flush: flush, insert: true)
        return casesTag
    }

    static boolean remove(Cases cases, Tag tag, boolean flush = false) {
       CasesTag casesTag = CasesTag.findByCasesAndTag(cases, tag)
       return casesTag ? casesTag.delete(flush: flush) : false
    }

    static void removeAllWithCases(Cases cases) {
        executeUpdate("DELETE FROM CasesTag WHERE cases=:cases", [cases: cases])
    }

    static void remoteAllWithTag(Tag tag){
        executeUpdate("DELETE FROM CasesTag WHERE tag=:tag", [tag: tag])
    }




    static constraints = {
    }

    @Override
    String toString(){
        return "$cases $tag"
    }

    @Override
    boolean equals(other){
        other?.cases == this.cases && other?.tag == this.tag
    }

    @Override
    int hashCode(){
        def builder = new HashCodeBuilder()
        builder.append(cases).append(tag)
        builder.toHashCode()

    }
    
}

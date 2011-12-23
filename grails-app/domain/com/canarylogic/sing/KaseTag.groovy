package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

/**
 * Created by IntelliJ IDEA.
 * User: bmakarand
 * Date: 12/20/11
 * Time: 10:42 PM
 * To change this template use File | Settings | File Templates.
 */
class KaseTag extends AbstractCanaryDomain implements Serializable{
    static XML_ELEMENT_MAP = [entity_id:"entityId",tag_id:"tagId"]

    static mapping = {
        table('kase_tag')
        version(false)
        id composite:['kase','tag']
    }

    static constraints = {
    }

    Tag   tag
    Kase kase

    static KaseTag create(Kase kase, Tag tag, boolean flush = false) {
        KaseTag kaseTag = new KaseTag(kase:kase, tag:tag)
        kaseTag.save(flush: flush, insert: true)
        return kaseTag
    }

    static boolean remove(Kase kase, Tag tag, boolean flush = false) {
       KaseTag kaseTag = KaseTag.findByKaseAndTag(kase, tag)
       return kaseTag ? kaseTag.delete(flush: flush) : false
    }

    static void removeAllWithKase(Kase kase) {
        executeUpdate("DELETE FROM KaseTag WHERE kase=:kase", [kase: kase])
    }

    static void removeAllWithTag(Tag tag){
        executeUpdate("DELETE FROM KaseTag WHERE tag=:tag", [tag: tag])
    }


    //!@toXml method is not available since we are not going to come here but go to Tags direcltly
    static void saveBean(String xmlRootName,def aMap,def pBean, def client, boolean isUpdateCall){
        pBean.person = Kase.get(aMap.entityId)
        pBean.tag    = Tag.get(aMap.tagId)
        pBean.save(failOnError:true)
    }



    @Override
    String toString(){
        return "[kase:$kase,tag:$tag]"
    }

    @Override
    boolean equals(other){
        other?.kase == this.kase && other?.tag == this.tag
    }

    @Override
    int hashCode(){
        def builder = new HashCodeBuilder()
        builder.append(kase).append(tag)
        builder.toHashCode()

    }
    
}

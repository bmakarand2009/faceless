package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

class PersonTag extends AbstractCanaryDomain implements Serializable{
    static XML_ELEMENT_MAP = [entity_id:"entityId",tag_id:"tagId"]

    static mapping = {
        table('person_tag')
        version(false)
        id composite:['person','tag']
    }

    static constraints = {
    }

    Person person
    Tag   tag



    static PersonTag create(Person person, Tag tag, boolean flush = false) {
        PersonTag personTag = new PersonTag(person:person, tag:tag)
        personTag.save(flush: flush, insert: true)
        return personTag
    }

    static boolean remove(Person person, Tag tag, boolean flush = false) {
       PersonTag personTag = PersonTag.findByPersonAndTag(person, tag)
       return personTag ? personTag.delete(flush: flush) : false
    }

    static void removeAllWithPerson(Person person) {
        executeUpdate("DELETE FROM PersonTag WHERE person=:person", [person: person])
    }

    static void removeAllWithTag(Tag tag){
        executeUpdate("DELETE FROM PersonTag WHERE tag=:tag", [tag: tag])
    }

    //!@toXml method is not available since we are not going to come here but go to Tags direcltly
    static void saveBean(String xmlRootName,def aMap,def pBean, def client, boolean isUpdateCall){
        pBean.person = Person.get(aMap.entityId)
        pBean.tag    = Tag.get(aMap.tagId)
        pBean.save(failOnError:true)
    }



    @Override
    String toString(){
        return "$person $tag"
    }

    @Override
    boolean equals(other){
        other?.person == this.person && other?.tag == this.tag
    }

    @Override
    int hashCode(){
        def builder = new HashCodeBuilder()
        builder.append(person).append(tag)
        builder.toHashCode()

    }
}

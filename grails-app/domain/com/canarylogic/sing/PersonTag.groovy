package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

class PersonTag extends AbstractCanaryDomain implements Serializable{
    Person person
    Tag   tag

    static mapping = {
        table('person_tag')
        version(false)
        id composite:['person','tag']
    }

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

    static void remoteAllWithTag(Tag tag){
        executeUpdate("DELETE FROM PersonTag WHERE tag=:tag", [tag: tag])
    }




    static constraints = {
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

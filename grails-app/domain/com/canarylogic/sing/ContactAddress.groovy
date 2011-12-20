package com.canarylogic.sing

import org.apache.commons.lang.builder.HashCodeBuilder

class ContactAddress extends AbstractCanaryDomain implements  Serializable{

//	static searchable = true
    def static XML_ELEMENT_MAP=[street:"street",city:"city",state:"state",zip:"zip",country:"country"]


	String street
	String city
	String state
	String zip
	String country
	
	static belongsTo = [person:Person,company:Company]
	
	
	
    static constraints = {
		street(nullable:true)
		city(nullable:true)
		state(nullable:true)
		zip(nullable:true)
		country(nullable:true)
        person(nullable:true)
        company(nullable:true)
    }

    def toXml(builder){
      //def mkp = builder.getMkp()
      builder.address(){
          id(id.toString())
          street(street)
          city(city)
          state(state)
          zip(zip)
          country(country)
      }

    }


    protected  void saveBean(def pBean, def parent, boolean isUpdateCall){
       if(isUpdateCall)
           pBean.save(failOnError:true)
    }



    static void saveBean(String xmlRootName,def aMap,def pBean, def parent, boolean isUpdateCall){
        if(isUpdateCall){
            pBean.save(failOnError:true)
        }
    }



    @Override
    boolean equals(other){
         if(! (other instanceof ContactAddress )){
             return false
         }
         boolean isTempBool =false
         if(this.person){
             isTempBool = other?.person == this.person
         }else if(this.company){
             isTempBool = other?.companry == this.company
         }

         isTempBool && other.id == this.id
    }

    @Override
    public int hashCode() {
        def builder = new HashCodeBuilder()
        if(this.company) builder.append(company)
        else if(this.person) builder.append(person)
        builder.append(id)
        builder.toHashCode()
    }

    @Override
    String toString(){
        return "$street - $city:$state for $person or $company"
    }






}

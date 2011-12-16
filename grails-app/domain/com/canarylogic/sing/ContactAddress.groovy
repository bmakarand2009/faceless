package com.canarylogic.sing

class ContactAddress extends AbstractCanaryDomain{

//	static searchable = true
    def static XML_ELEMENT_MAP=[street:"street",city:"city",state:"state",zip:"zip",country:"country"]


	String street
	String city
	String state
	String zip
	String country
	
	static belongsTo = [person:Person]
	
	
	
    static constraints = {
		street(nullable:true)
		city(nullable:true)
		state(nullable:true)
		zip(nullable:true)
		country(nullable:true)
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


}

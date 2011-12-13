package com.canarylogic.sing

class ContactAddress {

//	static searchable = true
    def static XML_ELEMENT_MAP=[city:"city"]


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
      def mkp = builder.getMkp()
      builder.address(){
          street(street)
          city(city)
          state(state)
          zip(zip)
          country(country)
      }

    }

    def createObj(def aMap){
         ContactAddress a = new ContactAddress()
         a.city = aMap.city
         return a
     }
}

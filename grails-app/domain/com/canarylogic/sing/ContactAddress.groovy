package com.canarylogic.sing

class ContactAddress {

//	static searchable = true
	
	String street
	String city
	String state
	String zip
	String country
	
	static belongsTo = [contact:Contact]
	
	
	
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
}

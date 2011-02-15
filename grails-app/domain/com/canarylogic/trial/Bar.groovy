package com.canarylogic.trial

class Bar {
	static belongsTo = [parent:Foo2]	
	String barName
    static constraints = {
		barName(unique:'parent')		
    }
}

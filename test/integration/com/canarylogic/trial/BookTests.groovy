package com.canarylogic.trial

import grails.test.*

class BookTests extends GrailsUnitTestCase {
	def transactional=true
	
	def sessionFactory
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testSomething() {
		def book = new Book(name: 'myBook')
		assertNotNull book.save()		
    }
	
	void testAutomaticTimestamping() {
		def foo = new Foo()
		foo.bar = 1
		assertNull foo.dateCreated
		assertNull foo.lastUpdated
		assertNotNull foo.save()
		assertNotNull foo.dateCreated
		assertNotNull foo.lastUpdated
		def oldDateCreated = foo.dateCreated
		def oldLastUpdated = foo.lastUpdated
		foo.bar = 2
		assertNotNull foo.save()
		assertEquals oldDateCreated, foo.dateCreated
		assertEquals oldLastUpdated, foo.lastUpdated
		assertNotNull foo.save(flush:true)
		assertTrue foo.lastUpdated.after(oldLastUpdated)
	  }
	
	//OneToMany Relationship
	void testFooSavingManyBars() {
		def foo = new Foo2()
		foo.orgName ="orgName1"
		assertNull foo.bars			
		foo.save()
		foo.bars = []
		foo.bars << new Bar(barName:"bar1")
		foo.bars << new Bar(barName:'bar2')
		foo.bars << new Bar(barName:'bar3')
		foo.bars.each { it.parent = foo }		
		assertNotNull foo.save(flush:true)
		assertEquals 3, foo.bars.size()
		
		sessionFactory.currentSession.clear()
		
		def boo = new Foo2()
		boo.orgName="orgName2"
		assertNull boo.bars
		boo.save()
		
		boo.bars = []
		boo.bars << new Bar(barName:"bar2")
		boo.bars.each { it.parent = boo }
		assertNotNull boo.save(flush:true)
		assertEquals 1, boo.bars.size()
		
		sessionFactory.currentSession.clear()
		Bar b = new Bar(barName:"bar2") //"bar2" will nt work		
		b.parent = foo
		assertNull b.save(flush:true)

		sessionFactory.currentSession.clear()		
		def myfoo = Foo2.findByOrgName('orgName1')
//		assertNotNull myfoo
		assertEquals 3,myfoo.bars.size()
		
		sessionFactory.currentSession.clear()
		def barList = Bar.findAllByBarName("bar2")
		assertEquals 2, barList.size()
		
		barList.each{
			assertNotNull it.parent.orgName
			assertTrue it.parent.orgName.startsWith("orgName")
		}
	  }
	  
}

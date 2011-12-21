
/**
 * Created by IntelliJ IDEA.
 * User: bmakarand
 * Date: 12/19/11
 * Time: 12:01 AM
 * To change this template use File | Settings | File Templates.
 */

class GrailsConsoleTest {

/*
import com.canarylogic.sing.*
import com.canarylogic.focalpoint.*
Client c1 = Client.findByOrgId("canary-test-123")
println c1
if(!c1)
    c1 = new Client(orgName:"sample test org",orgId:"canary-test-123",createdBy:"testuser").save(flush:true,failOnError:true)

c1.errors.each{
  println it
}

println "$c1 created"

def t1List = c1.taskTypeList
if(!t1List){
   c1.addToTaskTypeList(typeName:'phone',typeDesc:'phone followup')
   c1.addToTaskTypeList(typeName:'email',typeDesc:'email followup')
}

t1List = c1.taskTypeList
TaskType t1
t1List.each{
  if(!t1)
      t1 = it
}

assert t1!=null

def tagsList = c1.getTagList()
if(!tagsList){
     new Tag(client:c1,tagName:'testTag').save(flush:true,failOnError:true)
     new Tag(client:c1,tagName:'testTag2').save(flush:true,failOnError:true)
}

tagsList = c1.getTagList()
Tag tg1
tagsList.each{
    if(!tg1) tg1 = it
}
assert tg1 !=null

def pList = c1.getPersonList()
if(!pList){
    Person p1 = new Person(client:c1, firstName:"john", lastName:"travlota",createdBy:"testUser").save(flush:true,failOnError:true)
    p1.errors.each{
      println it
    }


    Person p2 = new Person(client:c1, firstName:"john", lastName:"decosta",createdBy:"testUser").save(flush:true,failOnError:true)
    p2.errors.each{
      println it
    }

}
def cList = c1.getCompanyList()
if(!cList){
    Company aComp = new Company(client:c1, companyName:"CanarySytems",createdBy:"testUser").save(flush:true,failOnError:true)
}




Person pp1 = c1.getPersonList()[0]
Company cc1 = c1.getCompanyList()[0]
pp1.addToContactDetailsList(new ContactDetails(contactType:'email',contactCategory:'home',contactValue:'mark@bac.com'))
pp1.addToContactDetailsList(new ContactDetails(contactType:'email',contactCategory:'home',contactValue:'mark@bac.com'))
cc1.addToContactDetailsList(new ContactDetails(contactType:'email',contactCategory:'home',contactValue:'mark@bac.com'))

cc1.addToContactAddressList(street:"desire street",city:"destination",state:"dream",country:"solace")
pp1.addToContactAddressList(street:"desire street",city:"destination",state:"dream",country:"solace")
cc1.addToContactAddressList(street:"desire street",city:"destination",state:"dream",country:"solace")

Date dDate = new Date()
pp1.addToTaskList(subject:'this is a task wcih needs ot be done as a par ot followup',taskType:t1,dueDate: dDate)
cc1.addToTaskList(subject:'this string can really get long and does not have any restrictions',taskType:t1,dueDate: dDate)


PersonTag pt = new PersonTag(person:pp1,tag:tg1).save(flush:true,failOnError:true)
*/
}

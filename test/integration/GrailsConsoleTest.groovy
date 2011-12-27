
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
if(!c1) c1 = new Client(orgName:"sample test org",orgId:"canary-test-123",createdBy:"consoleuser").save(flush:true,failOnError:true)
c1.errors.each{
  println it
}
println "$c1 created"

//create TaskType

def t1List = c1.taskTypeList
if(!t1List){
   c1.addToTaskTypeList(name:'phone',typeDesc:'phone followup')
   c1.addToTaskTypeList(name:'email',typeDesc:'email followup')
}
TaskType t1
t1List.each{
  if(!t1)
      t1 = it
}
assert t1!=null

def tagsList = c1.getTagList()
def tg1
if(!tagsList){
     tg1 = new Tag(client:c1,name:'testTag').save(flush:true,failOnError:true)
     new Tag(client:c1,name:'testTag2').save(flush:true,failOnError:true)
}else{
  tagsList.each{ tg1 = it }
}
assert tg1 !=null

def customFieldDefnList = c1.customFieldsDefinitionList
def cdf
if(!customFieldDefnList){
    cdf = new CustomFieldsDefinition(name:"rate",client:c1,optionType:"text",entityType:"Person",cSequence:1,createdBy:"consoleUser")
    cdf.save(flush:true,failOnErrror:true)
    if(cdf.hasErrors()){
       cdf.errors.each{ println it}
    }
}else{
   customFieldDefnList.each{ cdf = it }
}
assert cdf!=null
def pList = c1.getPersonList()
Person p1,p2
Company ac1,ac2
if(!pList){
    p1 = new Person(client:c1, firstName:"john", lastName:"travlota",createdBy:"testUser").save(flush:true,failOnError:true)
    p2 = new Person(client:c1, firstName:"john", lastName:"decosta",createdBy:"testUser").save(flush:true,failOnError:true)
}else{
  pList.each{ p1 = it }
}
def cList = c1.getCompanyList()
if(!cList){
    ac1 = new Company(client:c1, companyName:"CanarySytems",createdBy:"testUser").save(flush:true,failOnError:true)
    ac1 = new Company(client:c1, companyName:"CanarySytems2",createdBy:"testUser").save(flush:true,failOnError:true)
}else{
  cList.each{ ac1 = it }
}



p1.addToContactDetailsList(new ContactDetails(contactType:'email',contactCategory:'home',contactValue:'mark@bac1.com')).save(failOnError:true)
p1.addToContactDetailsList(new ContactDetails(contactType:'email',contactCategory:'home',contactValue:'mark@bac2.com')).save(failOnError:true)
ac1.addToContactDetailsList(new ContactDetails(contactType:'email',contactCategory:'home',contactValue:'mark@bac3.com')).save(failOnError:true)

ac1.addToContactAddressList(street:"desire street",city:"destination",state:"dream",country:"solace").save(failOnError:true)
p1.addToContactAddressList(street:"desire street",city:"destination",state:"dream",country:"solace").save(failOnError:true)
ac1.addToContactAddressList(street:"desire street",city:"destination",state:"dream",country:"solace").save(failOnError:true)

p1.addToCustomFieldsList(value:"565",customFieldsDefinition:cdf).save(failOnError:true)

Date dDate = new Date()
p1.addToTaskList(body:'this is a task wcih needs ot be done as a par ot followup',taskType:t1,dueDate: dDate,createdBy:"consoleTest")
ac1.addToTaskList(body:'this string can really get long and does not have any restrictions',taskType:t1,dueDate: dDate,createdBy:"consoleTest")


PersonTag pt = new PersonTag(person:p1,tag:tg1).save(flush:true,failOnError:true)
Notes nt = new Notes(person:p1,body:"this is a sample note which can fly",createdBy:"testurser").save(failOnError:true)

println "doNE"


*/
}

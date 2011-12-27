class UrlMappings {

	static mappings = {
//		"/$controller/$action?/$id?"{
//			constraints {
//				// apply constraints here
//			}
//		}

        // /app/person/$id?
        // /app/company/$id?
        // /app/tags/
        // /app/tags/?id
        // /app/tags/entityName?/id?
        //

        //"/person/$id?"(resource:"partner")
        ///person/
        ///person/search/
        //"/company/$id?"(resource:"partner")
        ////tag/
        //tag/entityName?/
        //tag/entityName?/entityId?
        ///tag/id?

        "/$domain/$entityOrAction?/$id?"(controller:"partner", parseRequest:true){
                action = [GET:"show", PUT:"update", DELETE:"delete", POST:"save"]
               constraints{
               }
        }
        //"/$controller/$option1?/$option2?/id?"(resource:"partner")



		"/"(view:"/index")
		"500"(view:'/error')
	}
}

class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

        "/person/$id?"(resource:"partner")
        "/company/$id?"(resource:"partner")


		"/"(view:"/index")
		"500"(view:'/error')
	}
}

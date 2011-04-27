dataSource {
    pooled = true
    driverClassName = "org.hsqldb.jdbcDriver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "update"//create-drop" // one of 'create', 'create-drop','update'
//            url = "jdbc:hsqldb:mem:devDB"
			url = "jdbc:mysql://mycanarydb.c8mvjbrpgxos.us-east-1.rds.amazonaws.com/devfaceless"
			driverClassName = "com.mysql.jdbc.Driver"
			username = "faceless_admin"
			password = "tiger"
        }
    }
    test {
        dataSource {
            dbCreate = "update"
//            url = "jdbc:hsqldb:mem:testDb"
			url = "jdbc:mysql://mycanarydb.c8mvjbrpgxos.us-east-1.rds.amazonaws.com/devfaceless"
			driverClassName = "com.mysql.jdbc.Driver"
			username = "faceless_admin"
			password = "tiger"
			
//			url = "jdbc:mysql://localhost/faceless2"
//			driverClassName = "com.mysql.jdbc.Driver"
//			username = "root"
//			password = "mysql"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:hsqldb:file:prodDb;shutdown=true"
        }
    }
}

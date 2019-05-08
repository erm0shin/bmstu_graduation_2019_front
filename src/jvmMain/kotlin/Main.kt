import database.DatabaseFactory
import database.Posts
import database.toPost
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.html.respondHtml
import io.ktor.http.content.files
import io.ktor.http.content.static
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import kotlinx.html.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.FieldSet
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.selectAll


fun Application.main() {
    install(ContentNegotiation) {
        jackson {}
    }

    DatabaseFactory.init()

//    database {
//        SchemaUtils.create(Posts)
//    }

    routing {
        get("/") {
            call.respondHtml {
                head {
                    meta {
                        charset = "utf-8"
                    }
                    title {
                        +"BMSTU Graduation Application"
                    }
                }
                body {
                    div {
                        id = "main_menu"
                        +"Loading..."
                    }
                    script(src = "/main.bundle.js") {
                    }
                }
            }
        }

        static("/") {
            files("build/bundle")
        }

        get("/hello") {
            val serializedResult = Json.stringify(Greeting.serializer(), Greeting(11L, "hello from ktor"))
            call.respond(serializedResult)
        }

        get("/db_hello") {
            call.respond(DatabaseFactory.dbQuery {
                Posts.selectAll().map { toPost(it) }
            })

//            lateinit var resultSet: Query
//            DatabaseFactory.dbQuery {
//                resultSet = Posts.selectAll()
//            }
//            call.respond(resultSet)
        }

    }
}

@Serializable
data class Greeting(
    val id: Long,
    val content: String
)
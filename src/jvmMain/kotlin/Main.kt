import database.DatabaseFactory
import database.Posts
import database.toPost
import dto.NewUser
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.html.respondHtml
import io.ktor.http.content.files
import io.ktor.http.content.static
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.delete
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.sessions.*
import kotlinx.html.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.selectAll
import repositories.UserRepository
import services.UserService

data class UserSession(val name: String, val value: Long)

fun Application.main() {
    install(ContentNegotiation) {
        jackson {}
    }
//    install(Sessions) {
//        cookie<UserSession>("USER_COOKIE")
//    }
    install(Sessions) {
        cookie<UserSession>(
            "USER_COOKIE",
            storage = SessionStorageMemory()
        ) {
            cookie.path = "/" // Specify cookie's path '/' so it can be used in the whole site
        }
    }

    DatabaseFactory.init()
    val userRepository = UserRepository()
    val userService = UserService(userRepository)

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

        post("/signup") {
            val newUser = call.receive<NewUser>()
            val newUserId = userService.signup(newUser)
            if (newUserId != null) {
                val session = call.sessions.get<UserSession>() ?: UserSession(name = newUser.login, value = 0)
                call.sessions.set(session.copy(value = newUserId))
            }
        }

        post("/signin") {
            val newUser = call.receive<NewUser>()
            val userId = userService.signin(newUser)
            if (userId != null) {
                val session = call.sessions.get<UserSession>() ?: UserSession(name = newUser.login, value = 0)
                call.sessions.set(session.copy(value = userId))
            }
        }

        delete("/signout") {
            call.sessions.clear<UserSession>()
        }

        get("/hello") {
            val serializedResult = Json.stringify(Greeting.serializer(), Greeting(11L, "hello from ktor"))
            call.respond(serializedResult)
        }

        get("/db_hello") {
            call.respond(DatabaseFactory.dbQuery {
                Posts.selectAll().map { toPost(it) }
            })
        }

    }
}

@Serializable
data class Greeting(
    val id: Long,
    val content: String
)
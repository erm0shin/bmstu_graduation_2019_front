import database.DatabaseFactory
import database.Posts
import database.toPost
import dto.AttendanceRequest
import dto.NewUser
import dto.PerformanceRequest
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.features.ContentNegotiation
import io.ktor.html.respondHtml
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.files
import io.ktor.http.content.static
import io.ktor.http.contentType
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
import utils.ANALYTIC_URL

data class UserSession(val name: String, val value: Long)

fun Application.main() {
    install(ContentNegotiation) {
        jackson {}
    }
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

    val client = HttpClient(Apache) {
        install(JsonFeature) {
            serializer = JacksonSerializer()
        }
    }

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
                call.response.status(HttpStatusCode.OK)
            } else {
                call.response.status(HttpStatusCode.BadRequest)
            }
        }

        post("/signin") {
            val newUser = call.receive<NewUser>()
            val userId = userService.signin(newUser)
            if (userId != null) {
                val session = call.sessions.get<UserSession>() ?: UserSession(name = newUser.login, value = 0)
                call.sessions.set(session.copy(value = userId))
                call.response.status(HttpStatusCode.OK)
            } else {
                call.response.status(HttpStatusCode.BadRequest)
            }
        }

        post("/forwardPerformanceRequest") {
            val session = call.sessions.get<UserSession>()
            if (session == null || !userService.userExists(session.value)) {
                call.response.status(HttpStatusCode.BadRequest)
            } else {
                val message = client.post<PerformanceRequest> {
                    url("$ANALYTIC_URL/performance")
                    contentType(ContentType.Application.Json)
                    body = call.receive<PerformanceRequest>()
                }
                call.respond(message)
            }
        }

        post("/forwardAttendanceRequest") {
            val session = call.sessions.get<UserSession>()
            if (session == null || !userService.userExists(session.value)) {
                call.response.status(HttpStatusCode.BadRequest)
            } else {
                val message = client.post<AttendanceRequest> {
                    url("$ANALYTIC_URL/attendance")
                    contentType(ContentType.Application.Json)
                    body = call.receive<AttendanceRequest>()
                }
                call.respond(message)
            }
        }

        delete("/signout") {
            call.sessions.clear<UserSession>()
            call.response.status(HttpStatusCode.OK)
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
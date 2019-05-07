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
import kotlinx.css.*
import kotlinx.css.properties.lh
import kotlinx.html.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

private val globalCss = CSSBuilder().apply {
    body {
        margin(0.px)
        padding(0.px)

        fontSize = 13.px
        fontFamily = "-system-ui, -apple-system, BlinkMacSystemFont, Segoe UI, Roboto, Oxygen, Ubuntu, Cantarell, Droid Sans, Helvetica Neue, BlinkMacSystemFont, Segoe UI, Roboto, Oxygen, Ubuntu, Cantarell, Droid Sans, Helvetica Neue, Arial, sans-serif"

        lineHeight = 20.px.lh
    }
}

private val blockCSS = CSSBuilder().apply {
    body {
        border = "2px solid #aaa719"
        backgroundColor = Color("#d4d37f")
        marginBottom = 20.px
        padding = 10.px.toString()
        width = 80.pct
    }
}

private val menuCSS = CSSBuilder().apply {
    body {
        display = Display.flex
        padding = 10.px.toString()
        width = LinearDimension("25%")
        marginLeft = LinearDimension("37.5%")
        color = Color("#fff")
        fontSize = 1.3.em
        flexDirection = FlexDirection.column
        alignItems = Align.center
    }
}

fun Application.main() {
    install(ContentNegotiation) {
        jackson {}
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
//                    style {
//                        unsafe {
//                            +globalCss.toString()
//                        }
//                    }
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

//        route("/api") {
//            rpc(PostService::class, Post.serializer())
//            rpc(PostWithCommentsService::class, PostWithComments.serializer())
//        }
    }
}

@Serializable
data class Greeting(
    val id: Long,
    val content: String
)
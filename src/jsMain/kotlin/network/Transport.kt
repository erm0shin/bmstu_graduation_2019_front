package network

import dto.NewUser
import dto.PerformanceRequest
import kotlinx.coroutines.await
import kotlinx.coroutines.withContext
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.list
import org.w3c.fetch.RequestInit
import kotlin.browser.window
import kotlin.coroutines.CoroutineContext
import kotlin.js.json

@JsName("encodeURIComponent")
external fun urlEncode(value: String): String

@JsName("decodeURIComponent")
external fun urlDecode(encoded: String): String

class Transport(private val coroutineContext: CoroutineContext) {
    internal suspend fun <T> get(
        url: String,
        deserializationStrategy: KSerializer<T>,
        vararg args: Pair<String, Any>
    ): T {
        return Json.parse(deserializationStrategy, fetch(url, *args))
    }

    internal suspend fun <T> getList(
        url: String,
        deserializationStrategy: KSerializer<T>,
        vararg args: Pair<String, Any>
    ): List<T> {
        return Json.parse(deserializationStrategy.list, fetch(url, *args))
    }

    suspend fun getHello(
        url: String,
        deserializationStrategy: KSerializer<CommentClient.Greeting>,
        vararg args: Pair<String, Any>
    ): CommentClient.Greeting {
        return Json.parse(deserializationStrategy, fetch(url, *args))
    }

    suspend fun sign(
        url: String,
        newUser: NewUser
    ): Boolean {
        return withContext(coroutineContext) {
            val response = window.fetch(
                "/$url", RequestInit(
                    "POST",
                    headers = json(
                        "Accept" to "application/json",
                        "Content-Type" to "application/json"
                    ),
                    credentials = "same-origin".asDynamic(),
                    body = Json.stringify(NewUser.serializer(), newUser)
                )
            ).await()

            response.status == 200.toShort()
        }
    }

    suspend fun signout(): Boolean {
        return withContext(coroutineContext) {
            val response = window.fetch(
                "/signout", RequestInit(
                    "DELETE",
                    credentials = "same-origin".asDynamic()
                )
            ).await()

            response.status == 200.toShort()
        }
    }

    private suspend fun fetch(method: String, vararg args: Pair<String, Any>): String {
//        var url = "/api/$method"
        var url = "/$method"
        if (args.isNotEmpty()) {
            url += "?"
            url += args.joinToString("&", transform = { "${it.first}=${urlEncode(it.second.toString())}" })
        }

        return withContext(coroutineContext) {
            val response = window.fetch(
                url, RequestInit(
                    "GET", headers = json(
                        "Accept" to "application/json",
                        "Content-Type" to "application/json"
                    ), credentials = "same-origin".asDynamic()
                )
            ).await()

            response.text().await()
        }
    }

    fun forwardPerformanceRequest(performanceRequest: PerformanceRequest, serializer: KSerializer<PerformanceRequest>): ByteArray {
        return ByteArray(12);
    }
}
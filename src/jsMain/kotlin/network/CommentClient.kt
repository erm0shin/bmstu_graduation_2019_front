package network

import kotlinx.coroutines.await
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.w3c.fetch.RequestCredentials
import org.w3c.fetch.RequestInit
import org.w3c.fetch.SAME_ORIGIN
import utils.FAKE_JSON_TOKEN
import utils.FAKE_JSON_URL
import utils.GREETING_URL
import utils.JSON_PLACEHOLDER_URL
import kotlin.browser.window
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext
import kotlin.js.json

@Serializable
internal class FakeJsonCommentRequest {
    companion object {
        fun create(postId: Int, count: Int): FakeJsonCommentRequest {
            return FakeJsonCommentRequest().apply {
                data = data.apply {
                    this.postId = postId
                    this._repeat = count
                }
            }
        }
    }

    val token = FAKE_JSON_TOKEN
    var data = FakeJsonCommentData()
}

@Serializable
internal class FakeJsonCommentData {
    var postId = 0
    val id = "numberInt"
    val name = "name"
    val email = "internetEmail"
    val body = "stringShort"
    var _repeat = 1
}

class CommentClient constructor(coroutineContext: CoroutineContext) {
    private var fallback = false

    private val headers = json(
        "Accept" to "application/json",
        "Content-Type" to "application/json"
    )

    suspend fun getComments(postId: String, count: Int): String {
        return withContext(coroutineContext) {
            val fakeJsonRequestBody = FakeJsonCommentRequest.create(postId.toInt(), count)
            val fakeJsonResponse = if (!fallback) {
                window.fetch(
                    FAKE_JSON_URL,
                    RequestInit(
                        "POST",
                        headers = headers,
                        credentials = RequestCredentials.SAME_ORIGIN,
                        body = Json.stringify(FakeJsonCommentRequest.serializer(), fakeJsonRequestBody)
                    )
                ).await()
            } else {
                null
            }

            if (fakeJsonResponse?.status == 200.toShort()) {
                if (count <= 1) {
                    "[${fakeJsonResponse.text().await()}]"
                } else {
                    fakeJsonResponse.text().await()
                }
            } else {
                fallback = true

                val url = "$JSON_PLACEHOLDER_URL/posts/$postId/comments"
                val response = window.fetch(
                    url, RequestInit(
                        "GET",
                        headers = headers,
                        credentials = RequestCredentials.SAME_ORIGIN
                    )
                ).await()

                response.text().await()
            }
        }
    }


    @Serializable
    data class Greeting(
        val id: Long,
        val content: String
    )

    suspend fun getGreeting(): String {
        return withContext(coroutineContext) {
            val url = GREETING_URL
            val response = window.fetch(
                url, RequestInit(
                    "GET",
                    headers = headers,
                    credentials = RequestCredentials.SAME_ORIGIN
                )
            ).await()

            if (response.status != 200.toShort()) {
                ""
            } else {
                Json.parse(
                    Greeting.serializer(),
                    response.text().await()
                ).content
            }
        }
    }


}
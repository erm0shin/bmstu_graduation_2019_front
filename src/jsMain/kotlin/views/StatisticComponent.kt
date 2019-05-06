package views

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.html.js.onClickFunction
import network.CommentClient
import react.*
import react.dom.button
import react.dom.div
import utils.ApplicationPage
import kotlin.browser.window

interface StatisticProps : RProps {
    var coroutineScope: CoroutineScope
    var updatePage: (ApplicationPage)->Unit
}

class StatisticState : RState {
    var greetingMessage = ""
    var gotResponse = false
}

class StatisticComponent : RComponent<StatisticProps, StatisticState>() {
    init {
        state = StatisticState()
    }

    private val coroutineContext
        get() = props.coroutineScope.coroutineContext

    override fun RBuilder.render() {
        div {
            +"hello from STATISTIC"
        }
        button {
            +"Back to main menu"
            attrs.onClickFunction = {
                props.updatePage(ApplicationPage.MAIN)
            }
        }
        button {
            +"Call Spring!"
            attrs.onClickFunction = {
                val commentClient = CommentClient(coroutineContext)
                props.coroutineScope.launch {
                    val greetingResponse = commentClient.getGreeting()
                    window.alert(greetingResponse)
                    if (greetingResponse != "") {
                        setState {
                            greetingMessage = greetingResponse
                            gotResponse = true
                        }
                    }
                }
            }
        }
        div {
            if (state.gotResponse) {
                +state.greetingMessage
            } else {
                +"Spring has not yet been called"
            }
        }
    }
}
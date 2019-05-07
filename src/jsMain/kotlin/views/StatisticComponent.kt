package views

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.html.DIV
import kotlinx.html.js.onClickFunction
import network.CommentClient
import react.*
import react.dom.button
import react.dom.div
import react.dom.input
import react.dom.p
import styled.StyledDOMBuilder
import styled.css
import styled.styledButton
import styled.styledDiv
import styles.CommonStyles
import utils.ApplicationPage
import kotlin.browser.window

interface StatisticProps : RProps {
    var coroutineScope: CoroutineScope
    var updatePage: (ApplicationPage) -> Unit
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
        styledDiv {
            css {
                +CommonStyles.common
            }
            styledDiv {
                +"Укажите локацию"
                css {
                    +CommonStyles.box
                }
                p {
                    +"Выберите курс"
                }
                input {
                    attrs.placeholder = "Курс"
                }
                p {
                    +"Выберите факультет"
                }
                input {
                    attrs.placeholder = "Факультет"
                }
                p {
                    +"Выберите группу"
                }
                input {
                    attrs.placeholder = "Группа"
                }
                p {
                    +"Выберите студента"
                }
                input {
                    attrs.placeholder = "Студент"
                }
            }
            styledDiv {
                +"Укажите предмет"
                css {
                    +CommonStyles.box
                }
                p {
                    +"Выберите предмет"
                }
                input {
                    attrs.placeholder = "Предмет"
                }
            }
//            buildFetch()
            styledDiv {
                +"Укажите временной период"
                css {
                    +CommonStyles.box
                }
                p {
                    +"Укажите начало"
                }
                input {
                    attrs.placeholder = "Начало"
                }
                p {
                    +"Укажите окончание"
                }
                input {
                    attrs.placeholder = "Окончание"
                }
            }
        }
    }


    private fun StyledDOMBuilder<DIV>.buildFetch() {
        styledDiv {
            +"hello from STATISTIC"
            css {
                +CommonStyles.box
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
}
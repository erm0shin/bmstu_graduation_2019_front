package views

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.html.DIV
import kotlinx.html.InputType
import kotlinx.html.js.onClickFunction
import network.CommentClient
import react.*
import react.dom.*
import styled.*
import styles.CommonStyles
import styles.TableStyles
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
                +TableStyles.center
            }
            styledP {
                css {
                    +TableStyles.bigHeader
                }
                +"Аналитика"
            }
            styledDiv {
                css {
                    +TableStyles.table
                }
                styledDiv {
                    css {
                        +TableStyles.tableRow
                    }
                }
                styledDiv {
                    css {
                        +TableStyles.tableRow
                    }
                    styledDiv {
                        css {
                            +TableStyles.tableCell
                        }
                        styledP {
                            css {
                                +TableStyles.header
                            }
                            +"Выберите группу"
                        }
                        input {
                            attrs.placeholder = "Группа"
                        }
                    }
                    styledDiv {
                        css {
                            +TableStyles.tableCell
                        }
                        styledP {
                            css {
                                +TableStyles.header
                            }
                            +"Выберите предмет"
                        }
                        input {
                            attrs.placeholder = "Предмет"
                        }
                    }
                    styledDiv {
                        css {
                            +TableStyles.tableCell
                        }
                        styledP {
                            css {
                                +TableStyles.header
                            }
                            +"Укажите показатель"
                        }
                        select {
                            option {
                                +"Успеваемость"
                            }
                            option {
                                +"Посещаемость"
                            }
                        }
                    }
                    styledDiv {
                        css {
                            +TableStyles.tableCell
                        }
                        styledDiv {
                            styledP {
                                css {
                                    +TableStyles.header
                                }
                                +"Укажите временной период"
                            }
                            p {
                                +"Укажите начало"
                            }
                            input {
                                attrs.type = InputType.date
                                attrs.placeholder = "Начало"
                            }
                            p {
                                +"Укажите окончание"
                            }
                            input {
                                attrs.type = InputType.date
                                attrs.placeholder = "Окончание"
                            }
                        }
                    }
                }
            }
//            buildFetch()
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
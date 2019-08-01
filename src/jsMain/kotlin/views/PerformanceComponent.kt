package views

import dto.PerformanceRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.html.DIV
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import network.CommentClient
import network.Transport
import react.*
import react.dom.*
import styled.*
import styles.CommonStyles
import styles.TableStyles
import utils.ApplicationPage
import kotlin.browser.document
import kotlin.browser.window

interface PerformanceProps : RProps {
    var coroutineScope: CoroutineScope
    var updatePage: (ApplicationPage) -> Unit
}

class PerformanceState : RState {
    var greetingMessage = ""
    var gotResponse = false
}

class PerformanceComponent : RComponent<PerformanceProps, PerformanceState>() {
    init {
        state = PerformanceState()
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
                +"Успеваемость"
            }
            styledDiv {
                css {
                    +TableStyles.table
                }
                styledDiv {
                    css {
                        +TableStyles.tableRow
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
                                +"Укажите учебное подразделение"
                            }
                            p {
                                +"Выберите подразделение"
                            }
                            select {
                                option {
                                    +"Группа"
                                }
                                option {
                                    +"Кафедра"
                                }
                                option {
                                    +"Факультет"
                                }
                            }
                            p {
                                +"Укажите значение"
                            }
                            input {
                                attrs.placeholder = "Подразделение"
                            }
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
            styledDiv {
                attrs.id = "image_area"
            }
            styledDiv {
                css {
                    +CommonStyles.loginBar
                }
                styledDiv {
                    styledButton {
                        +"Подтвердить"
                        attrs.onClickFunction = {
                            val performanceRequest = PerformanceRequest()
                            performanceRequest.course = getCourse()
                            performanceRequest.begin = getBegin()
                            performanceRequest.end = getEnd()
                            performanceRequest.unitType = getUnitType()
                            performanceRequest.unit = getUnit()
                            val transport = Transport(coroutineContext)
                            props.coroutineScope.launch {
                                val result = transport.forwardPerformanceRequest(performanceRequest,
                                    PerformanceRequest.serializer())
                                val imageArea = document.getElementById("image_area")
                                imageArea!!.innerHTML += "<img src=\"data:image/png;base64,$result\" />"
                            }
                        }
                        css {
                            +CommonStyles.lightBtn
                        }
                    }
                    styledButton {
                        +"Добавить измерение"
                        attrs.onClickFunction = {
                            addRow()
                        }
                        css {
                            +CommonStyles.lightBtn
                        }
                    }
                }
                styledDiv {
                    styledButton {
                        +"Сброс"
                        attrs.onClickFunction = {
                            val imageArea = document.getElementById("image_area")
                            imageArea!!.innerHTML = ""
                            removeAllRows()
                            addRow()
                        }
                        css {
                            +CommonStyles.redBtn
                        }
                    }
                }
            }
//            buildFetch()
        }
    }


    private fun getUnit(): String {
        return "ИУ6"
    }

    private fun getUnitType(): String {
        return "Кафедра"
    }

    private fun getBegin(): String {
        return "01.01.2019"
    }

    private fun getEnd(): String {
        return "01.01.2019"
    }

    private fun getCourse(): String {
        return "4"
    }

    private fun removeAllRows() {
    }

    private fun addRow() {
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
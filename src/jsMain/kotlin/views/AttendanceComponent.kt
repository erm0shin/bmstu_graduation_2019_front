package views

import kotlinx.coroutines.CoroutineScope
import kotlinx.html.InputType
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.input
import react.dom.option
import react.dom.p
import react.dom.select
import styled.css
import styled.styledButton
import styled.styledDiv
import styled.styledP
import styles.CommonStyles
import styles.TableStyles
import utils.ApplicationPage

interface AttendanceProps : RProps {
    var coroutineScope: CoroutineScope
    var updatePage: (ApplicationPage) -> Unit
}

class AttendanceState : RState

class AttendanceComponent : RComponent<AttendanceProps, AttendanceState>() {
    init {
        state = AttendanceState()
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
                +"Посещаемость"
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
                css {
                    +CommonStyles.loginBar
                }
                styledDiv {
                    styledButton {
                        +"Подтвердить"
                        attrs.onClickFunction = {
                        }
                        css {
                            +CommonStyles.lightBtn
                        }
                    }
                    styledButton {
                        +"Добавить измерение"
                        attrs.onClickFunction = {
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
                        }
                        css {
                            +CommonStyles.redBtn
                        }
                    }
                }
            }
        }
    }
}
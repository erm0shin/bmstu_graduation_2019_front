package views

import kotlinx.coroutines.CoroutineScope
import kotlinx.html.InputType
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.button
import react.dom.div
import react.dom.input
import react.dom.p
import styled.css
import styled.styledButton
import styled.styledDiv
import styles.CommonStyles
import utils.ApplicationPage

interface AnalyticProps : RProps {
    var coroutineScope: CoroutineScope
    var updatePage: (ApplicationPage) -> Unit
}

class AnalyticState : RState

class AnalyticComponent : RComponent<AnalyticProps, AnalyticState>() {
    init {
        state = AnalyticState()
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
            styledDiv {
                css {
                    +CommonStyles.box
                }
                p {
                    +"Укажите желаемый показатель"
                }
//                input {
//                    attrs.type = InputType.radio
//                    attrs.value = "Успеваемость"
//                    attrs.name = "CheckAnal"
//                }
//                input {
//                    attrs.type = InputType.radio
//                    attrs.value = "Посещаемость"
//                    attrs.name = "CheckAnal"
//                }
                input {
                    attrs.placeholder = "Показатель"
                }
            }
        }
    }
}
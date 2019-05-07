package views

import kotlinx.coroutines.CoroutineScope
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
                css {
                    +CommonStyles.box
                }
                p {
                    +"Укажите первый параметр"
                }
                input {
                    attrs.placeholder = "Первый параметр"
                }
            }
            styledDiv {
                css {
                    +CommonStyles.box
                }
                p {
                    +"Укажите второй параметр"
                }
                input {
                    attrs.placeholder = "Второй параметр"
                }
            }
        }
    }
}
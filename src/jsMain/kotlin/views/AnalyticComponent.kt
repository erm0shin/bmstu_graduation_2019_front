package views

import kotlinx.coroutines.CoroutineScope
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.button
import react.dom.div
import styled.css
import styled.styledDiv
import styles.CommonStyles
import utils.ApplicationPage

interface AnalyticProps : RProps {
    var coroutineScope: CoroutineScope
    var updatePage: (ApplicationPage)->Unit
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
                +"hello from ANALYTIC"
                css {
                    +CommonStyles.box
                }
                div {
                    +"Text3"
                }
                div {
                    +"Text4"
                }
            }
            styledDiv {
                +"hello from ANALYTIC"
                css {
                    +CommonStyles.box
                }
                div {
                    +"Text3"
                }
                div {
                    +"Text4"
                }
            }
            button {
                +"Back to main menu"
                attrs.onClickFunction = {
                    props.updatePage(ApplicationPage.MAIN)
                }
            }
        }
    }
}
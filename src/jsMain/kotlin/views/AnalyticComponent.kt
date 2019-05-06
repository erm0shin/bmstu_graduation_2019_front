package views

import kotlinx.coroutines.CoroutineScope
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.button
import react.dom.div
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
        div {
            +"hello from ANALYTIC"
        }
        button {
            +"Back to main menu"
            attrs.onClickFunction = {
                props.updatePage(ApplicationPage.MAIN)
            }
        }
    }
}
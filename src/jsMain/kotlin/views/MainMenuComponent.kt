package views

import kotlinx.coroutines.CoroutineScope
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.button
import react.dom.div
import utils.ApplicationPage

interface MainMenuProps : RProps {
    var coroutineScope: CoroutineScope
    var updatePage: (ApplicationPage)->Unit
}

class MainMenuState : RState

class MainMenuComponent : RComponent<MainMenuProps, MainMenuState>() {
    init {
        state = MainMenuState()
    }

    private val coroutineContext
        get() = props.coroutineScope.coroutineContext

    override fun RBuilder.render() {
        div {
            +"hello from MENU"
        }
        button {
            +"Statistic"
            attrs.onClickFunction = {
                props.updatePage(ApplicationPage.STATISTIC)
            }
        }
        button {
            +"Analytic"
            attrs.onClickFunction = {
                props.updatePage(ApplicationPage.ANALYTIC)
            }
        }
    }
}
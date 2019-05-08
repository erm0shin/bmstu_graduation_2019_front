package views

import kotlinx.coroutines.CoroutineScope
import kotlinx.css.JustifyContent
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import styled.css
import styled.styledButton
import styled.styledDiv
import styles.CommonStyles
import utils.ApplicationPage


interface MainMenuProps : RProps {
    var coroutineScope: CoroutineScope
    var updatePage: (ApplicationPage) -> Unit
}

class MainMenuState : RState

class MainMenuComponent : RComponent<MainMenuProps, MainMenuState>() {
    init {
        state = MainMenuState()
    }

    private val coroutineContext
        get() = props.coroutineScope.coroutineContext

    override fun RBuilder.render() {
        styledDiv {
            css {
                +CommonStyles.common
            }

            styledDiv {
                +"hello from MENU"
                css {
                    +CommonStyles.box
                }
                styledButton {
                    +"Statistic"
                    attrs.onClickFunction = {
                        props.updatePage(ApplicationPage.STATISTIC)
                    }
                    css {
                        +CommonStyles.lightBtn
                    }
                }
                styledButton {
                    +"Analytic"
                    attrs.onClickFunction = {
                        props.updatePage(ApplicationPage.ANALYTIC)
                    }
                    css {
                        +CommonStyles.redBtn
                    }
                }
            }
        }
    }
}
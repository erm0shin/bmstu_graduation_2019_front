package views

import kotlinx.coroutines.CoroutineScope
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.button
import react.dom.div

//private object ApplicationStyles : StyleSheet("ApplicationStyles", isStatic = true) {
//    val wrapper by css {
//        padding(32.px, 16.px)
//    }
//
//    val post by css {
//        marginBottom = 32.px
//    }
//}

enum class ApplicationPage {
    MAIN,
    STATISTIC,
    ANALYTIC
}

interface ApplicationProps : RProps {
    var coroutineScope: CoroutineScope
}

class ApplicationState : RState {
    var applicationPage: ApplicationPage = ApplicationPage.MAIN
}

class ApplicationComponent : RComponent<ApplicationProps, ApplicationState>() {
    init {
        state = ApplicationState()
    }

    private val coroutineContext
        get() = props.coroutineScope.coroutineContext

    override fun RBuilder.render() {
        when(state.applicationPage) {
            ApplicationPage.MAIN -> {
                div {
                    +"hello from MENU"
                }
                button {
                    +"Statistic"
                    attrs.onClickFunction = {
                        setState {
                            applicationPage = ApplicationPage.STATISTIC
                        }
                    }
                }
                button {
                    +"Analytic"
                    attrs.onClickFunction = {
                        setState {
                            applicationPage = ApplicationPage.ANALYTIC
                        }
                    }
                }
            }
            ApplicationPage.STATISTIC -> {
                div {
                    +"hello from STATISTIC"
                }
                button {
                    +"Back to main menu"
                    attrs.onClickFunction = {
                        setState {
                            applicationPage = ApplicationPage.MAIN
                        }
                    }
                }
            }
            ApplicationPage.ANALYTIC -> {
                div {
                    +"hello from ANALYTIC"
                }
                button {
                    +"Back to main menu"
                    attrs.onClickFunction = {
                        setState {
                            applicationPage = ApplicationPage.MAIN
                        }
                    }
                }
            }
        }

    }

}

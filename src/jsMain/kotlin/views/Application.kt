package views

import kotlinx.coroutines.CoroutineScope
import react.*
import utils.ApplicationPage

//private object ApplicationStyles : StyleSheet("ApplicationStyles", isStatic = true) {
//    val wrapper by css {
//        padding(32.px, 16.px)
//    }
//
//    val post by css {
//        marginBottom = 32.px
//    }
//}

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
                child(MainMenuComponent::class) {
                    attrs.coroutineScope = props.coroutineScope
                    attrs.updatePage = this@ApplicationComponent::updatePage
                }
            }
            ApplicationPage.STATISTIC -> {
                child(StatisticComponent::class) {
                    attrs.coroutineScope = props.coroutineScope
                    attrs.updatePage = this@ApplicationComponent::updatePage
                }
            }
            ApplicationPage.ANALYTIC -> {
                child(AnalyticComponent::class) {
                    attrs.coroutineScope = props.coroutineScope
                    attrs.updatePage = this@ApplicationComponent::updatePage
                }
            }
        }

    }

    private fun updatePage(page: ApplicationPage) {
        setState {
            applicationPage = page
        }
    }

}

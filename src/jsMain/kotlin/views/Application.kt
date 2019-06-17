package views

import dto.ClientUser
import dto.NewUser
import kotlinx.coroutines.CoroutineScope
import react.*
import utils.ApplicationPage
import kotlin.browser.window


interface ApplicationProps : RProps {
    var coroutineScope: CoroutineScope
}

class ApplicationState : RState {
    var applicationPage: ApplicationPage = ApplicationPage.MAIN
    var currentUser: ClientUser? = null
}

class ApplicationComponent : RComponent<ApplicationProps, ApplicationState>() {
    init {
        state = ApplicationState()
    }

    private val coroutineContext
        get() = props.coroutineScope.coroutineContext

    override fun RBuilder.render() {
//        checkAuthorization()

        child(ButtonBarComponent::class) {
            attrs.coroutineScope = props.coroutineScope
            attrs.updatePage = this@ApplicationComponent::updatePage
            attrs.applicationPage = state.applicationPage
            attrs.updateCurrentUser = this@ApplicationComponent::updateCurrentUser
        }
        when (state.applicationPage) {
            ApplicationPage.MAIN -> {
                child(MainMenuComponent::class) {
                    attrs.coroutineScope = props.coroutineScope
                    attrs.updatePage = this@ApplicationComponent::updatePage
                }
            }
            ApplicationPage.PERFORMANCE -> {
                child(PerformanceComponent::class) {
                    attrs.coroutineScope = props.coroutineScope
                    attrs.updatePage = this@ApplicationComponent::updatePage
                }
            }
            ApplicationPage.ATTENDANCE -> {
                child(AttendanceComponent::class) {
                    attrs.coroutineScope = props.coroutineScope
                    attrs.updatePage = this@ApplicationComponent::updatePage
                }
            }
            ApplicationPage.SIGN_IN -> {
                child(SignInComponent::class) {
                    attrs.coroutineScope = props.coroutineScope
                    attrs.updatePage = this@ApplicationComponent::updatePage
                    attrs.updateCurrentUser = this@ApplicationComponent::updateCurrentUser
                }
            }
            ApplicationPage.SIGN_UP -> {
                child(SignUpComponent::class) {
                    attrs.coroutineScope = props.coroutineScope
                    attrs.updatePage = this@ApplicationComponent::updatePage
                    attrs.updateCurrentUser = this@ApplicationComponent::updateCurrentUser
                }
            }
            ApplicationPage.SIGN_OUT -> {
            }
        }

    }

    private fun checkAuthorization() {
        if (state.currentUser == null &&
            !(state.applicationPage == ApplicationPage.MAIN ||
                    state.applicationPage == ApplicationPage.SIGN_UP ||
                    state.applicationPage == ApplicationPage.SIGN_IN)
        ) {
            window.alert("Зарегайся!")
            setState {
                applicationPage = ApplicationPage.MAIN
            }
        }
    }

    private fun updatePage(page: ApplicationPage) {
        setState {
            applicationPage = page
        }
    }

    private fun updateCurrentUser(newUser: NewUser?) {
        setState {
            if (newUser == null) {
                currentUser = null
            } else {
                currentUser = ClientUser(
                    login = newUser.login,
                    email = newUser.email
                )
            }
        }
    }

}

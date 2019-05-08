package views

import dto.NewUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.input
import rpc.Transport
import styled.css
import styled.styledButton
import styled.styledDiv
import styles.CommonStyles
import utils.ApplicationPage
import kotlin.browser.window

interface SignInProps : RProps {
    var coroutineScope: CoroutineScope
    var updatePage: (ApplicationPage)->Unit
    var updateCurrentUser: (NewUser?) -> Unit
}

class SignInState : RState {
    var loginOrEmail: String = ""
    var password: String = ""
}

class SignInComponent : RComponent<SignInProps, SignInState>() {
    init {
        state = SignInState()
    }

    private val coroutineContext
        get() = props.coroutineScope.coroutineContext

    override fun RBuilder.render() {
        styledDiv {
            css {
                +CommonStyles.common
            }

            styledDiv {
                +"hello from SignIn"
                css {
                    +CommonStyles.box
                }
                // TODO: добавить стили
                input {
                    attrs.placeholder = "Login or Email"
                    attrs.onChangeFunction = {
                        val target = it.target as HTMLInputElement
                        setState {
                            loginOrEmail = target.value
                        }
                    }
                }
                // TODO: замазывать пароль
                input {
                    attrs.placeholder = "Password"
                    attrs.onChangeFunction = {
                        val target = it.target as HTMLInputElement
                        setState {
                            password = target.value
                        }
                    }
                }
                styledButton {
                    +"Submit"
                    attrs.onClickFunction = {
                        val transport = Transport(coroutineContext)
                        props.coroutineScope.launch {
                            val newUser = NewUser(
                                id = null,
                                login = state.loginOrEmail,
                                email = state.loginOrEmail,
                                password = state.password
                            )
                            if (transport.sign("signin", newUser)) {
                                window.alert("Ура")
                                props.updateCurrentUser(newUser)
                                props.updatePage(ApplicationPage.MAIN)
                            }
                        }
                    }
                    css {
                        +CommonStyles.lightBtn
                    }
                }
            }

        }
    }
}
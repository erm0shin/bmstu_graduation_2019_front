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

interface SignUpProps : RProps {
    var coroutineScope: CoroutineScope
    var updatePage: (ApplicationPage) -> Unit
    var updateCurrentUser: (NewUser?) -> Unit
}

class SignUpState : RState {
    var login: String = ""
    var email: String = ""
    var password1: String = ""
    var password2: String = ""
}

class SignUpComponent : RComponent<SignUpProps, SignUpState>() {
    init {
        state = SignUpState()
    }

    private val coroutineContext
        get() = props.coroutineScope.coroutineContext

    override fun RBuilder.render() {
        styledDiv {
            css {
                +CommonStyles.common
            }

            styledDiv {
                +"hello from SignUp"
                css {
                    +CommonStyles.box
                }
                // TODO: добавить стили
                input {
                    attrs.placeholder = "Login"
                    attrs.onChangeFunction = {
                        val target = it.target as HTMLInputElement
                        setState {
                            login = target.value
                        }
                    }
                }
                input {
                    attrs.placeholder = "Email"
                    attrs.onChangeFunction = {
                        val target = it.target as HTMLInputElement
                        setState {
                            email = target.value
                        }
                    }
                }
                // TODO: замазывать пароль
                input {
                    attrs.placeholder = "Password"
                    attrs.onChangeFunction = {
                        val target = it.target as HTMLInputElement
                        setState {
                            password1 = target.value
                        }
                    }
                }
                input {
                    attrs.placeholder = "Repeat password"
                    attrs.onChangeFunction = {
                        val target = it.target as HTMLInputElement
                        setState {
                            password2 = target.value
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
                                login = state.login,
                                email = state.email,
                                // TODO: сделать проверку паролей
                                password = state.password1
                            )
                            if (transport.sign("signup", newUser)) {
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
package views

import dto.NewUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import network.Transport
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.input
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
                +"Регистрация"
                css {
                    +CommonStyles.box
                }
                input {
                    attrs.placeholder = "Логин"
                    attrs.onChangeFunction = {
                        val target = it.target as HTMLInputElement
                        setState {
                            login = target.value
                        }
                    }
                }
                input {
                    attrs.placeholder = "Почта"
                    attrs.onChangeFunction = {
                        val target = it.target as HTMLInputElement
                        setState {
                            email = target.value
                        }
                    }
                }
                input {
                    attrs.placeholder = "Пароль"
                    attrs.type = InputType.password
                    attrs.onChangeFunction = {
                        val target = it.target as HTMLInputElement
                        setState {
                            password1 = target.value
                        }
                    }
                }
                input {
                    attrs.placeholder = "Повторите пароль"
                    attrs.type = InputType.password
                    attrs.onChangeFunction = {
                        val target = it.target as HTMLInputElement
                        setState {
                            password2 = target.value
                        }
                    }
                }
                styledButton {
                    +"Подтвердить"
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
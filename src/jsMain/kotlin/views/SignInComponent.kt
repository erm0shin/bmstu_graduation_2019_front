package views

import dto.NewUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.input
import network.Transport
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
                +"Авторизация"
                css {
                    +CommonStyles.box
                }
                input {
                    attrs.placeholder = "Логин или почта"
                    attrs.onChangeFunction = {
                        val target = it.target as HTMLInputElement
                        setState {
                            loginOrEmail = target.value
                        }
                    }
                }
                input {
                    attrs.type = InputType.password
                    attrs.placeholder = "Пароль"
                    attrs.onChangeFunction = {
                        val target = it.target as HTMLInputElement
                        setState {
                            password = target.value
                        }
                    }
                }
                styledDiv {
                    css {
                        +CommonStyles.loginBar
                    }
                    styledButton {
                        +"Подтвердить"
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
                    styledButton {
                        +"Зарегистрироваться"
                        attrs.onClickFunction = {
                            props.updatePage(ApplicationPage.SIGN_UP)
                        }
                        css {
                            +CommonStyles.redBtn
                        }
                    }
                }
            }

        }
    }
}
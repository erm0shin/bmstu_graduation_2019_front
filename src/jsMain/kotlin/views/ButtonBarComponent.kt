package views

import dto.NewUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.css.JustifyContent
import kotlinx.html.DIV
import kotlinx.html.js.onClickFunction
import network.CommentClient
import network.Transport
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.button
import styled.StyledDOMBuilder
import styled.css
import styled.styledButton
import styled.styledDiv
import styles.CommonStyles
import utils.ApplicationPage
import kotlin.browser.window

interface ButtonBarProps : RProps {
    var coroutineScope: CoroutineScope
    var updatePage: (ApplicationPage)->Unit
    var applicationPage: ApplicationPage
    var updateCurrentUser: (NewUser?) -> Unit
}

class ButtonBarState : RState

class ButtonBarComponent : RComponent<ButtonBarProps, ButtonBarState>() {
    init {
        state = ButtonBarState()
    }

    private val coroutineContext
        get() = props.coroutineScope.coroutineContext

    override fun RBuilder.render() {
        styledDiv {
            css {
                +CommonStyles.loginBar
                if (props.applicationPage == ApplicationPage.MAIN) {
                    justifyContent = JustifyContent.flexEnd
                }
            }
            if (props.applicationPage != ApplicationPage.MAIN) {
                styledDiv {
                    styledButton {
                        +"Назад в главное меню"
                        attrs.onClickFunction = {
                            props.updatePage(ApplicationPage.MAIN)
                        }
                        css {
                            +CommonStyles.lightBtn
                        }
                    }
                }
            }
            if (false) {
                styledDiv {
                    styledButton {
                        +"SignIn"
                        attrs.onClickFunction = {
                            props.updatePage(ApplicationPage.SIGN_IN)
                        }
                        css {
                            +CommonStyles.lightBtn
                        }
                    }
                    styledButton {
                        +"SignUp"
                        attrs.onClickFunction = {
                            props.updatePage(ApplicationPage.SIGN_UP)
                        }
                        css {
                            +CommonStyles.redBtn
                        }
                    }
                    styledButton {
                        +"SignOut"
                        attrs.onClickFunction = {
                            val transport = Transport(coroutineContext)
                            props.coroutineScope.launch {
                                if (transport.signout()) {
                                    window.alert("Ура")
                                    props.updateCurrentUser(null)
                                    props.updatePage(ApplicationPage.MAIN)
                                }
                            }
                        }
                        css {
                            +CommonStyles.redBtn
                        }
                    }
//                callKtor()
                }
            }
        }
    }


    private fun StyledDOMBuilder<DIV>.callKtor() {
        button {
            +"Call Ktor!"
            attrs.onClickFunction = {
                val transport = Transport(coroutineContext)
                props.coroutineScope.launch {
                    val hello = transport.getHello("hello", CommentClient.Greeting.serializer())
                    window.alert(hello.content)
                }
            }
        }
    }
}
package views

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.css.JustifyContent
import kotlinx.html.DIV
import kotlinx.html.js.onClickFunction
import network.CommentClient
import react.*
import react.dom.button
import rpc.Transport
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
                        +"Back to main menu"
                        attrs.onClickFunction = {
                            props.updatePage(ApplicationPage.MAIN)
                        }
                        css {
                            +CommonStyles.lightBtn
                        }
                    }
                }
            }
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
//                callKtor()
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
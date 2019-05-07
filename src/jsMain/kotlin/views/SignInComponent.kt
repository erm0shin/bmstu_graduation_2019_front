package views

import kotlinx.coroutines.CoroutineScope
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.input
import styled.css
import styled.styledButton
import styled.styledDiv
import styles.CommonStyles
import utils.ApplicationPage

interface SignInProps : RProps {
    var coroutineScope: CoroutineScope
    var updatePage: (ApplicationPage)->Unit
}

class SignInState : RState

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
                    attrs.placeholder = "Login"
                }
                // TODO: замазывать пароль
                input {
                    attrs.placeholder = "Password"
                }
                styledButton {
                    +"Submit"
                    attrs.onClickFunction = {
                        props.updatePage(ApplicationPage.MAIN)
                    }
                    css {
                        +CommonStyles.lightBtn
                    }
                }
            }

        }
    }
}
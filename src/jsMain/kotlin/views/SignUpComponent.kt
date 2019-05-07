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

interface SignUpProps : RProps {
    var coroutineScope: CoroutineScope
    var updatePage: (ApplicationPage)->Unit
}

class SignUpState : RState

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
                }
                input {
                    attrs.placeholder = "Email"
                }
                // TODO: замазывать пароль
                input {
                    attrs.placeholder = "Password"
                }
                input {
                    attrs.placeholder = "Repeat password"
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
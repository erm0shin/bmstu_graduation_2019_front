import kotlinext.js.invoke
import kotlinx.css.*
import styled.StyledComponents

object GlobalStyles {
    fun inject() {
        val styles = CSSBuilder(allowClasses = false).apply {
            body {
                backgroundColor = Color("#BEEFF4")
            }
        }

        StyledComponents.injectGlobal(styles.toString())
    }
}
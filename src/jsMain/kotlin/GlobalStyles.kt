import kotlinext.js.invoke
import kotlinx.css.*
import styled.StyledComponents

object GlobalStyles {
    fun inject() {
        val styles = CSSBuilder(allowClasses = false).apply {
            body {
                backgroundColor = Color("#e1e388")
            }
        }

        StyledComponents.injectGlobal(styles.toString())
    }
}
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import react.buildElement
import react.dom.div
import react.dom.render
//import view.ApplicationComponent
import kotlin.browser.document
import kotlin.coroutines.CoroutineContext

private class Application : CoroutineScope {
    override val coroutineContext: CoroutineContext = Job()

    fun start() {
        document.getElementById("react-app")?.let {
            render(buildElement {
//                child(ApplicationComponent::class) {
//                    attrs.coroutineScope = this@Application
//                }
                div {
                    +"Text from frontend part"
                }
            }, it)
        }
    }
}

fun main() {
    GlobalStyles.inject()

    Application().start()
}
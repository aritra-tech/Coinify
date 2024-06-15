import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import utils.ApplicationComponent

fun main() = application {

    ApplicationComponent.init()

    Window(
        onCloseRequest = ::exitApplication,
        title = "Coinify",
    ) {
        App()
    }
}
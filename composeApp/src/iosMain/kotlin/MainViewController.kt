import androidx.compose.ui.window.ComposeUIViewController
import utils.ApplicationComponent

fun MainViewController() = ComposeUIViewController { App() }

fun initializer() {
    ApplicationComponent.init()
}
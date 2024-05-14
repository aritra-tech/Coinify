import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import navigation.Navigation
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MainContent()
}

@Composable
fun MainContent() {
    Box(modifier = Modifier.fillMaxSize()) {
        Navigation()
    }
}

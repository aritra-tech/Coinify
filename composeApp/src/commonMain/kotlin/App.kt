import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import di.appModule
import navigation.Navigation
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import ui.CoinifyTheme
import utils.ThemeViewModel

@Composable
@Preview
fun App() {

    KoinApplication(application = {
        modules(appModule)
    }) {
        val themeViewModel: ThemeViewModel = koinInject()
        val isDarkModeEnabled by themeViewModel.currentTheme.collectAsState(isSystemInDarkTheme())

        CoinifyTheme(
            darkTheme = isDarkModeEnabled
        ) {
            MainContent()
        }
    }
}

@Composable
fun MainContent() {
    Box(modifier = Modifier.fillMaxSize()) {
        Navigation()
    }
}

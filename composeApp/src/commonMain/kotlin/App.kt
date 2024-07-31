import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import data.database.CoinifyDatabase
import di.appModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import navigation.Navigation
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import ui.CoinifyTheme
import utils.ThemeViewModel

@Composable
@Preview
fun App(
    databaseBuilder: RoomDatabase.Builder<CoinifyDatabase>
) {
    KoinApplication(application = {
        modules(appModule)
    }) {
        val database = remember { getRoomDatabase(databaseBuilder) }
        val themeViewModel: ThemeViewModel = koinInject()
        val isDarkModeEnabled by themeViewModel.currentTheme.collectAsState(isSystemInDarkTheme())

        CoinifyTheme(
            darkTheme = isDarkModeEnabled
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Navigation(database)
            }
        }
    }
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<CoinifyDatabase>
): CoinifyDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}


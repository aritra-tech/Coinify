package presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.PeopleAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coinify.composeapp.generated.resources.Res
import coinify.composeapp.generated.resources.invite_others
import coinify.composeapp.generated.resources.theme
import component.SettingItem
import component.Theme
import component.ThemeSelectionDialog
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import utils.Constants.APP_REPO

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = koinInject()
) {

    val uriHandler = LocalUriHandler.current
    var showThemeDialog by remember { mutableStateOf(false) }
    val isDarkModeEnabled by settingsViewModel.isDarkModeEnabled.collectAsState()

    when {

        showThemeDialog -> {
            ThemeSelectionDialog(
                onThemeChange = { theme ->
                    when (theme) {
                        Theme.Light -> settingsViewModel.changeDarkMode(false)
                        Theme.Dark -> settingsViewModel.changeDarkMode(true)
                    }
                    showThemeDialog = false
                },
                onDismissRequest = { showThemeDialog = false },
                currentTheme = if (isDarkModeEnabled) Theme.Dark else Theme.Light
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
            )
        }
    ) {  paddingValues ->

        Box(
            modifier = modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {

            LazyColumn {
                item {
                    Box(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "General",
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                item {
                    SettingItem(
                        onClick = {
                            showThemeDialog = true
                        },
                        imageVector = if (isSystemInDarkTheme()) Icons.Outlined.DarkMode else Icons.Outlined.LightMode,
                        itemName = stringResource(Res.string.theme)
                    )
                }
                item {
                    SettingItem(
                        onClick = {
                            uriHandler.openUri(APP_REPO)
                        },
                        imageVector = Icons.Outlined.PeopleAlt,
                        itemName = stringResource(Res.string.invite_others)
                    )
                }
            }
        }
    }
}
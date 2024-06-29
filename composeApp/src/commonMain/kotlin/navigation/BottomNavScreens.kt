package navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.StackedLineChart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavScreens(val route: String, val icon: ImageVector, val title: String) {
    data object Home: BottomNavScreens(Screens.Home.route, Icons.Default.Home,"Home")
    data object Statistics: BottomNavScreens(Screens.Statistics.route, Icons.Outlined.StackedLineChart, "Statistics")
    data object News: BottomNavScreens(Screens.Settings.route, Icons.Default.Settings, "Settings")
}
package navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavScreens(val route: String, val icon: ImageVector, val title: String) {
    object Home: BottomNavScreens(Screens.Home.route, Icons.Default.Home,"Home")
    object News: BottomNavScreens(Screens.News.route, Icons.Default.Newspaper, "News")
}
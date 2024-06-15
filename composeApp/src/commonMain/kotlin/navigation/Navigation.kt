package navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coinify.composeapp.generated.resources.Res
import coinify.composeapp.generated.resources.poppins_regular
import domain.model.Data
import kotlinx.serialization.json.Json
import presentation.DetailsScreen
import presentation.HomeScreen
import presentation.NewsScreen
import presentation.SettingsScreen
import ui.backgroundLight
import ui.onPrimaryContainerLight
import ui.onSurfaceVariantLight

val LocalNavHost = staticCompositionLocalOf<NavHostController> {
    error("No Parameter is available")
}

@Composable
fun Navigation() {

    val navController: NavHostController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val screensWithoutNavigationBar = mutableListOf(
        "${Screens.Details.route}/{data}"
    )

    CompositionLocalProvider(LocalNavHost provides navController) {
        Scaffold(
            contentWindowInsets = WindowInsets(0.dp),
            bottomBar = {
                BottomNavigationBar(
                    navController,
                    backStackEntry,
                    screensWithoutNavigationBar
                )
            }
        ) {
            NavHost(
                modifier = Modifier.fillMaxWidth(),
                navController = navController,
                startDestination = Screens.Home.route,
            ) {

                composable(route = Screens.Home.route) {
                    HomeScreen()
                }

                composable(route = "${Screens.Details.route}/{data}") { backStackEntry ->
                    val jsonData = backStackEntry.arguments?.getString("data")
                    val data = jsonData?.let { Json.decodeFromString<Data>(it) }
                    data?.let {
                        DetailsScreen(it)
                    }
                }

                composable(route = Screens.Settings.route) {
                    SettingsScreen()
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    backStackEntry: State<NavBackStackEntry?>,
    screensWithoutNavigationBar: MutableList<String>
) {
    val items = listOf(
        BottomNavScreens.Home,
        BottomNavScreens.News
    )

    if (backStackEntry.value?.destination?.route !in screensWithoutNavigationBar) {
        BottomAppBar(
            containerColor = backgroundLight
        ) {
            val currentBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = currentBackStackEntry?.destination

            items.forEach { screen ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = screen.icon,
                            contentDescription = screen.title,
                            tint = if (backStackEntry.value?.destination?.route == screen.route) onPrimaryContainerLight else onSurfaceVariantLight
                        )
                    },
                    label = {
                        Text(
                            text = screen.title,
                            color = if (backStackEntry.value?.destination?.route == screen.route) onPrimaryContainerLight else onSurfaceVariantLight,
                            fontFamily = FontFamily(org.jetbrains.compose.resources.Font(Res.font.poppins_regular)),
                            fontWeight = if (backStackEntry.value?.destination?.route == screen.route)
                                FontWeight.SemiBold
                            else
                                FontWeight.Normal,
                        )
                    },
                    selected = currentDestination?.route == screen.route,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationRoute.toString()) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

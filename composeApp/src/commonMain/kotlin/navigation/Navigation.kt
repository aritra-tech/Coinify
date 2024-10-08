package navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coinify.composeapp.generated.resources.Res
import coinify.composeapp.generated.resources.poppins_regular
import domain.model.crypto.Data
import kotlinx.serialization.json.Json
import presentation.details.DetailsScreen
import presentation.home.HomeScreen
import presentation.news.NewsDetailsScreen
import presentation.news.NewsScreen
import presentation.settings.SettingsScreen
import presentation.stats.StatisticsScreen
import ui.onPrimaryContainerLight
import ui.onSurfaceVariantLight
import ui.primaryDark
import utils.FadeIn
import utils.FadeOut

val LocalNavHost = staticCompositionLocalOf<NavHostController> {
    error("No Parameter is available")
}

@Composable
fun Navigation() {

    val navController: NavHostController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val screensWithoutNavigationBar = mutableListOf(
        "${Screens.Details.route}/{data}",
        "${Screens.NewsDetails.route}/{news}"
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
                enterTransition = { FadeIn },
                exitTransition = { FadeOut }
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

                composable(route = Screens.Statistics.route) {
                    StatisticsScreen()
                }

                composable(route = Screens.News.route) {
                    NewsScreen()
                }

                composable(
                    route = "${Screens.NewsDetails.route}/{news}",
                    arguments = listOf(navArgument("news") { type = NavType.StringType })
                ) { backStackEntry ->
                    val jsonData = backStackEntry.arguments?.getString("news")
                    val newsData = jsonData?.let { Json.decodeFromString<domain.model.news.Data>(it) }
                    newsData?.let {
                        NewsDetailsScreen(it)
                    }
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

    if (backStackEntry.value?.destination?.route !in screensWithoutNavigationBar) {
        NavigationBar {
            val items = listOf(
                BottomNavScreens.Home,
                BottomNavScreens.Statistics,
                BottomNavScreens.News,
                BottomNavScreens.Settings
            )
            val currentDestination = navController.currentBackStackEntry?.destination?.route

            items.forEach { screen ->
                NavigationBarItem(
                    alwaysShowLabel = true,
                    icon = {
                        Icon(
                            imageVector = screen.icon,
                            contentDescription = screen.title,
                            tint = if (backStackEntry.value?.destination?.route == screen.route)
                                onPrimaryContainerLight
                            else
                                onSurfaceVariantLight
                        )
                    },
                    label = {
                        Text(
                            text = screen.title,
                            color = if (backStackEntry.value?.destination?.route == screen.route)
                                onPrimaryContainerLight
                            else
                                onSurfaceVariantLight,
                            fontFamily = FontFamily(org.jetbrains.compose.resources.Font(Res.font.poppins_regular)),
                            fontWeight = if (backStackEntry.value?.destination?.route == screen.route)
                                FontWeight.Medium
                            else
                                FontWeight.Normal,
                        )
                    },
                    selected = backStackEntry.value?.destination?.route == screen.route,
                    onClick = {
                        if (screen.route != currentDestination) {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationRoute.toString()) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = primaryDark
                    )
                )
            }
        }
    }
}

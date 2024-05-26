package navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import presentation.DetailsScreen
import presentation.home.HomeScreen

val LocalNavHost = staticCompositionLocalOf<NavHostController> {
    error("No Parameter is available")
}
@Composable
fun Navigation() {

    val navController: NavHostController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()

    CompositionLocalProvider(LocalNavHost provides navController) {
        Scaffold(
            contentWindowInsets = WindowInsets(0.dp)
        ) {
            NavHost(
                modifier = Modifier.fillMaxWidth(),
                navController = navController,
                startDestination = Screens.Home.route,
            ) {

                composable(route = Screens.Home.route) {
                    HomeScreen()
                }

                composable(route = Screens.Details.route) {
                    DetailsScreen()
                }
            }
        }
    }
}
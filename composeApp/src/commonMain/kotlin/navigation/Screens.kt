package navigation

sealed class Screens(val route: String) {
    data object Home : Screens("home_screen")
    data object Details : Screens("details_screen")
    data object News: Screens("news_screens")
    data object Settings : Screens("settings_screen")
    data object Statistics : Screens("stats_screen")
}
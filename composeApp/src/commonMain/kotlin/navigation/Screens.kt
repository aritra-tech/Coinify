package navigation

sealed class Screens(val route: String) {
    data object Home : Screens("home_screen")
    data object Details : Screens("details_screen")
}
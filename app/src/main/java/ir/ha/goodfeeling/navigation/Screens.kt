package ir.ha.goodfeeling.navigation

sealed class Screens(val route: String) {
    object Intro : Screens("intro")
    object NameRegister : Screens("name_register")
    object Scheduling : Screens("scheduling")
    object Host : Screens("host")
    object Home : Screens("home")
    object Setting : Screens("setting")
    object AboutUs : Screens("about_us")

}
package ir.hasanazimi.avand.presentation.navigation

sealed class Screens(val routeId: String) {
    object Splash : Screens("splash")
    object Intro : Screens("intro")
    object NameRegister : Screens("nameRegister")
    object Scheduling : Screens("scheduling")
    object Host : Screens("host")
    object Home : Screens("home")
    object CurrencyPrices : Screens("currencyPrices")
    object Setting : Screens("setting")
    object AboutUs : Screens("aboutUs")
    object News : Screens("news")
    object WebView : Screens("WebView")

}
package ir.hasanazimi.avand.presentation.navigation

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ir.hasanazimi.avand.presentation.theme.AvandTheme
import ir.hasanazimi.avand.presentation.theme.CustomTypography


data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val screenRoute: Screens
)


val navigationItems = listOf(
    NavigationItem(
        title = "خانه",
        icon = Icons.Default.Home,
        screenRoute = Screens.Home
    ),
    NavigationItem(
        title = "بازار مالی",
        icon = Icons.Default.Info,
        screenRoute = Screens.CurrencyPrices
    ),
    NavigationItem(
        title = "پروفایل",
        icon = Icons.Default.AccountCircle,
        screenRoute = Screens.Setting
    )
)


@Composable
fun BottomNavigationBar(navController: NavController) {

    var currentRoute by remember { mutableStateOf<String?>(Screens.Home.routeId) }

    NavigationBar(
        modifier = Modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(24.dp))
            .border(
                BorderStroke(2.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)),
                RoundedCornerShape(24.dp)
            ),
    ) {
        navigationItems.forEach { item ->
            NavigationBarItem(
                selected = (currentRoute == item.screenRoute.routeId).also {
                    Log.i("BottomBar", "BottomNavigationBar: ${currentRoute == item.screenRoute.routeId} ")
                },
                onClick = {
                    navController.navigate(item.screenRoute.routeId) {
                        popUpTo(Screens.Home.routeId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    currentRoute = navController.currentBackStackEntry?.destination?.route
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = if (item.screenRoute.routeId == currentRoute) MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f) else MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f)
                    )
                },
                label = {
                    Text(
                        item.title,
                        color = if (item.screenRoute.routeId == currentRoute) MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f) else MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f),
                        style = CustomTypography.labelSmall
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f),
                    indicatorColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)
                )

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    AvandTheme {
        val navController = rememberNavController()
        BottomNavigationBar(navController = navController)
    }
}
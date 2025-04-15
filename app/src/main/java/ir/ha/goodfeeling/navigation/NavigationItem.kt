package ir.ha.goodfeeling.navigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ir.ha.goodfeeling.ui.theme.CustomTypography
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme


data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)


val navigationItems = listOf(
    NavigationItem(
        title = "خانه",
        icon = Icons.Default.Home,
        route = Screens.Home.route
    ),
    NavigationItem(
        title = "بازار مالی",
        icon = Icons.Default.Info,
        route = Screens.CurrencyPrices.route
    ),
    NavigationItem(
        title = "پروفایل",
        icon = Icons.Default.AccountCircle,
        route = Screens.Setting.route
    )
)


@Composable
fun BottomNavigationBar(navController: NavController) {
    val selectedNavigationIndex = rememberSaveable { mutableIntStateOf(0) }
    NavigationBar(
        modifier = Modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(24.dp)).border(BorderStroke(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)), RoundedCornerShape(24.dp)),
    ) {
        navigationItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedNavigationIndex.intValue == index,
                onClick = {
                    if (selectedNavigationIndex.intValue != index) {
                        selectedNavigationIndex.intValue = index
                        navController.navigate(item.route)
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = if (index == selectedNavigationIndex.intValue) MaterialTheme.colorScheme.primary.copy(alpha = 0.8f) else MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                    )
                },
                label = {
                    Text(
                        item.title,
                        color = if (index == selectedNavigationIndex.intValue) MaterialTheme.colorScheme.primary.copy(alpha = 0.8f) else MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                        style = CustomTypography.labelSmall
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                )

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    GoodFeelingTheme {
        val navController = rememberNavController()
        BottomNavigationBar(navController = navController)
    }
}
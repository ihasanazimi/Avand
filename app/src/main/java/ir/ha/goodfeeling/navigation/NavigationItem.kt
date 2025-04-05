package ir.ha.goodfeeling.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme
import ir.ha.goodfeeling.ui.theme.TransparentlyGray


data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)


val navigationItems = listOf(
    NavigationItem(
        title = "Home",
        icon = Icons.Default.Home,
        route = Screens.Home.route
    ),
    NavigationItem(
        title = "Setting",
        icon = Icons.Default.Settings,
        route = Screens.Setting.route
    )
)


@Composable
fun BottomNavigationBar(navController: NavController) {
    val selectedNavigationIndex = rememberSaveable { mutableIntStateOf(0) }
    NavigationBar(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp)),
        containerColor = TransparentlyGray
    ) {
        navigationItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedNavigationIndex.intValue == index,
                onClick = {
                    selectedNavigationIndex.intValue = index
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.title)
                },
                label = {
                    Text(
                        item.title,
                        color = if (index == selectedNavigationIndex.intValue) Color.Black else Color.Gray
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.surface,
                    indicatorColor = MaterialTheme.colorScheme.primary
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
package ir.ha.goodfeeling.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ir.ha.goodfeeling.ui.theme.GoodFeelingTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("صفحه اصلی") },
                navigationIcon = {
                    IconButton(onClick = { /* انجام کاری */ }) {
                        Icon(Icons.Default.Menu, contentDescription = "منو")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                IconButton(onClick = { /* انجام کاری */ }) {
                    Icon(Icons.Default.Home, contentDescription = "خانه")
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { /* انجام کاری */ }) {
                    Icon(Icons.Default.Person, contentDescription = "پروفایل")
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* انجام کاری */ }) {
                Icon(Icons.Default.Add, contentDescription = "افزودن")
            }
        },
        modifier = modifier
    ) { innerPadding ->
        Surface {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("محتوای اصلی صفحه")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(modifier: Modifier = Modifier) {
    GoodFeelingTheme {
        HomeScreen(Modifier)
    }
}
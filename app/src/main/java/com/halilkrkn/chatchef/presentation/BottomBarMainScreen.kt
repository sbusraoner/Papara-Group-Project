package com.halilkrkn.chatchef.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.halilkrkn.chatchef.navigation.graphs.SetupBottomBarNavGraph
import com.halilkrkn.chatchef.navigation.util.BottomBarNavigationScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {

        SetupBottomBarNavGraph(navController = navController, modifier = Modifier.padding(it))

    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarNavigationScreen.ChatGptBottomBarNavigation,
        BottomBarNavigationScreen.FavoriteBottomBarNavigation,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    val isBottomBarVisible = currentDestination in screens.map { bottomBarScreen ->
        bottomBarScreen.route
    }

    if (isBottomBarVisible) {
        NavigationBar(
            containerColor = Color.White,
            contentColor = Color.Black,
        ) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarNavigationScreen,
    currentDestination: String?,
    navController: NavHostController,
) {
    NavigationBarItem(
        label = {
            Text(
                text = screen.title
            )
        },
        icon = {
            Icon(
                painter = painterResource(id = screen.icon),
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination == screen.route,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
//        alwaysShowLabel = false
    )
}
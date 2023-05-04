package com.example.sure_market.screen.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.sure_market.R
import com.example.sure_market.screen.main.ChatScreen
import com.example.sure_market.viewmodel.MainViewModel

const val MAIN = "mainScreen"
const val CHAT = "ChatScreen"
const val SETTING = "settingScreen"

sealed class BottomNavItem(
    val screenRoute: String,
    val bottomIcon: Int,
    val bottomTitle: Int,
    val topIcon: Int?,
    val topTitle: Int,
) {
    object PostListScreen : BottomNavItem(
        screenRoute = MAIN,
        bottomIcon = R.drawable.baseline_home_24,
        bottomTitle = R.string.bottom_navigation_main,
        topIcon = R.drawable.baseline_search_24,
        topTitle = R.string.top_main_title,
    )

    object ChatScreen : BottomNavItem(
        screenRoute = CHAT,
        bottomIcon = R.drawable.baseline_person_24,
        bottomTitle = R.string.bottom_navigation_chat,
        topIcon = null,
        topTitle = R.string.top_chat_title,
    )

    object SettingScreen : BottomNavItem(
        screenRoute = SETTING,
        bottomIcon = R.drawable.baseline_chat_24,
        bottomTitle = R.string.bottom_navigation_setting,
        topIcon = R.drawable.baseline_settings_24,
        topTitle = R.string.top_setting_title,
    )
}

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    navController: NavHostController,
    onMovePost: () -> Unit,
    onMoveDetail: (Long) -> Unit,
    clearUser: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = viewModel.topIconState.value.topTitle)) },
                contentColor = Color.White,
                actions = {
                    viewModel.topIconState.value.topIcon?.let {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(id = it),
                                contentDescription = "search"
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = "글쓰기") },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_add_24),
                        contentDescription = "add post",
                    )
                },
                onClick = { onMovePost() },
                contentColor = Color.White,
            )
        },
        bottomBar = {
            BottomNavigation(navController = navController)
        }

    ) {
        Box(modifier = Modifier.padding(it)) {
            NavigationGraph(
                navController = navController,
                viewModel = viewModel,
                onMoveDetail = onMoveDetail,
                clearUser = clearUser
            )
        }
    }
}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    viewModel: MainViewModel,
    onMoveDetail: (Long) -> Unit,
    clearUser: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.PostListScreen.screenRoute
    ) {
        composable(BottomNavItem.PostListScreen.screenRoute) {
            viewModel.setTopIconState(BottomNavItem.PostListScreen)
            PostListScreen(viewModel = viewModel, onMoveDetail = onMoveDetail)
        }
        composable(BottomNavItem.ChatScreen.screenRoute) {
            viewModel.setTopIconState(BottomNavItem.ChatScreen)
            ChatScreen()
        }
        composable(BottomNavItem.SettingScreen.screenRoute) {
            viewModel.setTopIconState(BottomNavItem.SettingScreen)
            SettingScreen(clearUser = clearUser)
        }
    }
}

@Composable
fun BottomNavigation(navController: NavHostController) {
    val items = listOf<BottomNavItem>(
        BottomNavItem.PostListScreen,
        BottomNavItem.ChatScreen,
        BottomNavItem.SettingScreen,
    )

    androidx.compose.material.BottomNavigation(
        backgroundColor = colorResource(id = R.color.main_color),
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.bottomIcon),
                        contentDescription = stringResource(id = item.bottomTitle),
                        modifier = Modifier.size(26.dp)
                    )

                },
                label = { Text(text = stringResource(id = item.bottomTitle), fontSize = 9.sp) },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = Color.Gray,
                selected = item.screenRoute == currentRoute,
                alwaysShowLabel = false,
                onClick = {
                    navController.navigate(item.screenRoute) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
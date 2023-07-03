package com.example.sure_market.screen.main

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.sure_market.R
import com.example.sure_market.viewmodel.MainViewModel

const val MAIN = "mainScreen"
const val CHAT = "ChatScreen"
const val SETTING = "settingScreen"

sealed class BottomNavItem(
    val screenRoute: String,
    var bottomIcon: ImageVector,
    val bottomTitle: Int,
    val topIcon: Int?,
    val topTitle: Int,
) {
    object PostListScreen : BottomNavItem(
        screenRoute = MAIN,
//        bottomIcon = R.drawable.baseline_home_24,
        bottomIcon = Icons.Default.Home,
        bottomTitle = R.string.bottom_navigation_main,
        topIcon = R.drawable.baseline_search_24,
        topTitle = R.string.top_main_title,
    )

    object ChatScreen : BottomNavItem(
        screenRoute = CHAT,
        bottomIcon = Icons.Outlined.Email,
        bottomTitle = R.string.bottom_navigation_chat,
        topIcon = null,
        topTitle = R.string.top_chat_title,
    )

    object SettingScreen : BottomNavItem(
        screenRoute = SETTING,
        bottomIcon = Icons.Outlined.Person,
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
    onMoveDetail: (Int) -> Unit,
    clearUser: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = viewModel.topIconState.value.topTitle)) },
//                contentColor = Color.White,
                actions = {
                    viewModel.topIconState.value.topIcon?.let {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(id = it),
                                contentDescription = "search",
//                                tint = contentColorFor(backgroundColor = MaterialTheme.colors.primary)
                            )
                        }
                    }
                },
                backgroundColor = MaterialTheme.colors.secondary,
                contentColor = contentColorFor(MaterialTheme.colors.secondary)
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
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = contentColorFor(MaterialTheme.colors.primary)
            )
        },
        bottomBar = {
            BottomNavigation(navController = navController, viewModel = viewModel)
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
    onMoveDetail: (Int) -> Unit,
    clearUser: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.PostListScreen.screenRoute
    ) {
        composable(BottomNavItem.PostListScreen.screenRoute) {
            viewModel.setTopIconState(BottomNavItem.PostListScreen)
            PostListScreen(viewModel = viewModel, onMoveDetail = onMoveDetail, context = LocalContext.current)
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
fun BottomNavigation(navController: NavHostController, viewModel: MainViewModel) {
    val items = listOf<BottomNavItem>(
        BottomNavItem.PostListScreen,
        BottomNavItem.ChatScreen,
        BottomNavItem.SettingScreen,
    )

    androidx.compose.material.BottomNavigation(
        backgroundColor = MaterialTheme.colors.secondary,
        contentColor = contentColorFor(MaterialTheme.colors.secondary)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = item.bottomIcon,
                        contentDescription = stringResource(id = item.bottomTitle),
                        modifier = Modifier.size(26.dp)
                    )
                },
                label = { Text(text = stringResource(id = item.bottomTitle), fontSize = 9.sp) },
//                selectedContentColor = MaterialTheme.colors.secondary,
//                unselectedContentColor = MaterialTheme.colors.secondary,
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

//                    bottomSheetIconStateList.forEach {
//                        Log.d("daeYoung", "bottomSheetIconStateList: ${it}")
//                    }
                    when (item.screenRoute) {
                        MAIN -> {
                            viewModel.bottomIconChange(p1 = true)

                            if (viewModel.bottomIconState.value[0]) {
                                items[1].bottomIcon = Icons.Outlined.Email
                                items[2].bottomIcon = Icons.Outlined.Person
                                item.bottomIcon = Icons.Default.Home
                            }
                        }
                        CHAT -> {
                            viewModel.bottomIconChange(p2 = true)

                             if (viewModel.bottomIconState.value[1]) {
                                items[0].bottomIcon = Icons.Outlined.Home
                                items[2].bottomIcon = Icons.Outlined.Person
                                item.bottomIcon = Icons.Default.Email
                            }
                        }
                        SETTING -> {
                            viewModel.bottomIconChange(p3 = true)
                            if (viewModel.bottomIconState.value[2]) {
                                items[0].bottomIcon = Icons.Outlined.Home
                                items[1].bottomIcon = Icons.Outlined.Email
                                item.bottomIcon = Icons.Default.Person
                            }
                        }
                    }

                }
            )
        }
    }
}
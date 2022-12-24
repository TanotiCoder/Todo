package com.example.todu.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.todu.addTask.AddTask
import com.example.todu.completed_task.CompletedTaskScreen
import com.example.todu.important_task.ImportantTaskScreen
import com.example.todu.task_detail.TaskDetail
import com.example.todu.tasks.TasksScreen
import com.example.todu.todu_drawer.DrawerTodu
import com.example.todu.utils.Common.Companion.drawerButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    navController: NavController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) {

    val scope: CoroutineScope = rememberCoroutineScope()
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: "Home/{DrawerRoot}"


    NavHost(
        navController = navController as NavHostController,
        startDestination = "Home/{DrawerRoot}"
    ) {
        composable("Home/{DrawerRoot}") {
            DrawerTodu(
                drawerState = drawerState,
                scope = scope,
                navigationOnClick = {
                    navController.navigate("$it/$it") {
                        launchSingleTop = true
                        //popUpTo("Home/{DrawerRoot}") { inclusive = true }
                    }
                },
                drawerButtonData = drawerButton,
                currentRoot = currentRoute,
                content =
                {
                    TasksScreen(
                        onClickAddTask = {
                            navController.navigate("addTask") {
                                launchSingleTop = true
                            }
                        },
                        onClickTaskCard = {
                            navController.navigate("detail/$it") {
                                launchSingleTop = true
                            }
                        },
                        openDrawer = { coroutineScope.launch { drawerState.open() } })
                }
            )
        }

        composable("addTask") { AddTask(goBack = { navController.popBackStack() }) }
        composable("detail/{id}") { TaskDetail(goBackOnClick = { navController.popBackStack() }) }

        composable("Done/{DrawerRoot}") {
            DrawerTodu(
                drawerState = drawerState,
                scope = scope,
                navigationOnClick = {
                    navController.navigate("$it/$it") {
                        launchSingleTop = true
                        //popUpTo("Home/{DrawerRoot}") { inclusive = true }
                    }
                },
                drawerButtonData = drawerButton,
                currentRoot = currentRoute,
                content =
                {
                    CompletedTaskScreen(
                        //onClickAddTask = { navController.navigate("addTask") },
                        onClickTaskCard = {
                            navController.navigate("detail/$it") {
                                launchSingleTop = true
                            }
                        },
                        openDrawer = { coroutineScope.launch { drawerState.open() } })
                }
            )
        }

        composable("Bookmark/{DrawerRoot}") {
            DrawerTodu(
                drawerState = drawerState,
                scope = scope,
                navigationOnClick = {
                    navController.navigate("$it/$it") {
                        launchSingleTop = true
                    }
                },
                drawerButtonData = drawerButton,
                currentRoot = currentRoute,
                content =
                {
                    ImportantTaskScreen(
                        onClickTaskCard = {
                            navController.navigate("detail/$it") {
                                launchSingleTop = true
                            }
                        },
                        openDrawer = { coroutineScope.launch { drawerState.open() } })
                }
            )
        }
    }
}
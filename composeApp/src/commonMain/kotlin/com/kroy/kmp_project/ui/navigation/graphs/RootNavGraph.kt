package com.kroy.kmp_project.ui.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kroy.kmp_project.ui.MainScreen
import com.kroy.kmp_project.ui.navigation.Graph
import com.kroy.kmp_project.ui.navigation.SettingRouteScreen
import com.kroy.kmp_project.ui.setting.SettingScreen

@Composable
fun RootNavGraph(){
    val rootnavController = rememberNavController()
    NavHost(
        navController = rootnavController,
        route = Graph.RootScreenGraph,
        startDestination = Graph.MainScreenGraph
    ){
        composable(route = Graph.MainScreenGraph){
            MainScreen(rootnavController)
        }

        composable(route = SettingRouteScreen.Setting.route){
            SettingScreen(rootnavController)
        }
    }
}

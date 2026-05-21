package com.kroy.kmp_project.ui.navigation

 object Graph {
     const val RootScreenGraph  = "root_screen_graph"
     const val MainScreenGraph = "main_screen_graph"
     const val SearchScreenGraph = "search_screen_graph"

 }
sealed class MainRouteScreen(var route: String) {
    object Headlines : MainRouteScreen("headlines")
    object Search : MainRouteScreen("search")
    object Bookmark : MainRouteScreen("bookmark")
}

sealed class SettingRouteScreen(var route: String) {

    object Setting : SettingRouteScreen("setting")

}
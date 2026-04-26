package com.kroy.kmp_project.ui.navigation.graphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kroy.kmp_project.ui.bookmark.BookmarkScreen
import com.kroy.kmp_project.ui.headline.HeadLineScreen
import com.kroy.kmp_project.ui.navigation.Graph
import com.kroy.kmp_project.ui.navigation.MainRouteScreen
import com.kroy.kmp_project.ui.search.SearchScreen


@Composable
fun MainNavGraph(
    rootNavController: NavHostController,
    homeNavController: NavHostController,
    paddingValues: PaddingValues
){
    NavHost(
        modifier = Modifier.fillMaxSize().padding(paddingValues),
        navController = homeNavController,
        route = Graph.MainScreenGraph,
        startDestination = MainRouteScreen.Headlines.route
    ){
        composable (route = MainRouteScreen.Headlines.route){
            HeadLineScreen()
        }
        composable (route = MainRouteScreen.Search.route){
            SearchScreen()
        }
        composable (route = MainRouteScreen.Bookmark.route){
            BookmarkScreen()
        }

    }

}
package com.kroy.kmp_project.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text

import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kroy.kmp_project.ui.navigation.NewsBottomnaviBar
import com.kroy.kmp_project.ui.navigation.SettingRouteScreen
import com.kroy.kmp_project.ui.navigation.graphs.MainNavGraph
import com.kroy.kmp_project.utils.bottomeNavigationitemList
import kmp_project.composeapp.generated.resources.Res
import kmp_project.composeapp.generated.resources.ic_settings
import kmp_project.composeapp.generated.resources.setting
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(rootNavController: NavHostController) {
    val homeNavController = rememberNavController()
    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
    val currentRoute  by rememberSaveable(navBackStackEntry) {mutableStateOf(navBackStackEntry?.destination?.route)}
    var previousRoute  by rememberSaveable(navBackStackEntry) {mutableStateOf(navBackStackEntry?.destination?.route)}
    val topBarTitle by remember(currentRoute){
        derivedStateOf {
            if(currentRoute!=null){
                bottomeNavigationitemList[bottomeNavigationitemList.indexOfFirst { it.route == currentRoute }].title
            }else{
                bottomeNavigationitemList[0].title
            }
        }
    }
    DisposableEffect(Unit){
        previousRoute = currentRoute
        println("previous route =  $previousRoute")
        onDispose {

        }
    }
    LaunchedEffect(Unit){

    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(topBarTitle),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground

                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                           rootNavController.navigate(SettingRouteScreen.Setting.route)
                        }
                    ){
                        Icon(
                            painter = painterResource(resource = Res.drawable.ic_settings),
                            contentDescription = stringResource(Res.string.setting)
                        )
                    }
                }

            )
        },
        bottomBar = {
            NewsBottomnaviBar(
                bottomNavigationitemList = bottomeNavigationitemList,
                currentRoute = currentRoute,
                onItemClick = { currentBottomNavigationItem ->
                    homeNavController.navigate(currentBottomNavigationItem.route) {
                        homeNavController.graph.startDestinationRoute?.let {
                            popUpTo(it) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) {
        MainNavGraph(rootNavController,homeNavController,it)
    }

}
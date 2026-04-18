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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import com.kroy.kmp_project.ui.navigation.NewsBottomnaviBar
import com.kroy.kmp_project.utils.bottomeNavigationitemList
import kmp_project.composeapp.generated.resources.Res
import kmp_project.composeapp.generated.resources.ic_settings
import kmp_project.composeapp.generated.resources.setting
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var currentRoute  by remember {mutableStateOf(bottomeNavigationitemList[0].route)}
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Headline",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground

                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            //TODO():
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
                onItemClick = {
                    currentBottomNavigationItem->{
                currentRoute = currentBottomNavigationItem.route
                }
                }
            )
        }
    ) {

    }

}
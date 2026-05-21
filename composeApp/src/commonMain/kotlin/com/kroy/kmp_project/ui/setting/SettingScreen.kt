package com.kroy.kmp_project.ui.setting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@Composable
fun SettingScreen(rootNavController: NavHostController){
    Box(Modifier.fillMaxSize()){
        Button(onClick = {
            println("SettingScreen: Back button clicked")
            rootNavController.navigateUp()
        },
            modifier = Modifier.align(Alignment.TopCenter),

        ){
            Text("Back")
        }
        Text(text = "Setting Screen", fontSize = 32.sp, modifier =
            Modifier.align(Alignment.Center) , fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground)

    }
}

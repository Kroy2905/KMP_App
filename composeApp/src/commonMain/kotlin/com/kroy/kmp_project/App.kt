package com.kroy.kmp_project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kroy.kmp_project.theme.NewsAppTheme
import com.kroy.kmp_project.utils.getRandomId
import com.kroy.kmp_project.utils.getType
import org.jetbrains.compose.resources.painterResource

import kmp_project.composeapp.generated.resources.Res
import kmp_project.composeapp.generated.resources.app_name
import kmp_project.composeapp.generated.resources.compose_multiplatform
import kmp_project.composeapp.generated.resources.ic_android_black_24dp
import kmp_project.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.stringResource

@Composable
@Preview
fun App() {
    NewsAppTheme(darkTheme = true) {
//        var showContent by remember { mutableStateOf(false) }
//        Column(
//            modifier = Modifier
//                .background(MaterialTheme.colorScheme.primaryContainer)
//                .safeContentPadding()
//                .fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//        ) {
//            Button(onClick = { showContent = !showContent }) {
//                Text("Click me!")
//            }
//            AnimatedVisibility(showContent) {
                val greeting = remember { getType() }
                val randomId = remember { getRandomId() }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(painterResource(Res.drawable.logo), null)
                    Text(stringResource(Res.string.app_name))
                    Text("Compose: $greeting")
                    Text("RandomID: $randomId")
                }
            }
        }
   // }
//}
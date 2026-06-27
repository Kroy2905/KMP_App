package com.kroy.kmp_project.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun EmptyContent(message: String){
    Box(modifier = Modifier.fillMaxSize() ){
        Text(modifier = Modifier.align(Alignment.Center), text = message , fontSize = 32.sp)
    }
}

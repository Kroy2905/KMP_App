package com.kroy.kmp_project.ui.headline

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun HeadLineScreen(){
    Box(){
        Text(text = "Headlines Screen", fontSize = 32.sp, modifier =
            Modifier.align(Alignment.Center) , fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground)

    }
}

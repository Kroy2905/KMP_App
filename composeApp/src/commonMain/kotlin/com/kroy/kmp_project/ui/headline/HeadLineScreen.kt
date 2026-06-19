package com.kroy.kmp_project.ui.headline

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kroy.kmp_project.ui.common.ArticleListScreen
import com.kroy.kmp_project.utils.articles

@Composable
fun HeadLineScreen(){
    ArticleListScreen(articles)
}

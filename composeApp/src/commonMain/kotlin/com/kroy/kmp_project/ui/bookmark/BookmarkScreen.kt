package com.kroy.kmp_project.ui.bookmark

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kroy.kmp_project.ui.common.ArticleListScreen
import com.kroy.kmp_project.ui.common.EmptyContent
import com.kroy.kmp_project.ui.common.ShimmerEffect
import com.kroy.kmp_project.ui.headline.HeadlineViewModel

@Composable
fun BookmarkScreen(){
    val bookmarkViewModel = viewModel { BookmarkViewModel() }
    val uiState by bookmarkViewModel.newsStateFlow.collectAsStateWithLifecycle()
    uiState.DisplayResult(
        onIdle = {},
        onLoading = {
            ShimmerEffect()
        },
        onError = {
            EmptyContent(it)
        },
        onSuccess = {list->
            if(list.isEmpty())
                EmptyContent("List is empty")
            else
                ArticleListScreen(list)
        }
    )
}


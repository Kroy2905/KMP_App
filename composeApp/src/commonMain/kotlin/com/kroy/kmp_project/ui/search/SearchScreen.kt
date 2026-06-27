package com.kroy.kmp_project.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.mediaQuery
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kroy.kmp_project.theme.mediumPadding
import com.kroy.kmp_project.ui.common.ArticleListScreen
import com.kroy.kmp_project.ui.common.EmptyContent
import com.kroy.kmp_project.ui.common.ShimmerEffect
import com.kroy.kmp_project.ui.headline.HeadlineViewModel
import com.kroy.kmp_project.ui.search.components.SearchBarScreen
import com.kroy.kmp_project.utils.articles

@Composable
fun SearchScreen(){
    var searchQuery by rememberSaveable(){
        mutableStateOf("")
    }
    val searchViewModel = viewModel { SearchViewModel() }
    val uiState by searchViewModel.newsStateFlow.collectAsStateWithLifecycle()
    Column(
       verticalArrangement = Arrangement.spacedBy(mediumPadding)
   ){
       SearchBarScreen(
           text = searchQuery,
           onValueChange = {
               searchQuery = it
           },
           onSearch = {query->
               if(query.trim().isNotEmpty()){
                  searchViewModel.searchQueryNews(query)
                   println(query)
               }

           }
       )
        uiState.DisplayResult(
            onIdle = {
                EmptyContent("Type to Search")
            },
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

}

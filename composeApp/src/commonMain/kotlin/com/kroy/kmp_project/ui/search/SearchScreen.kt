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
import com.kroy.kmp_project.theme.mediumPadding
import com.kroy.kmp_project.ui.common.ArticleListScreen
import com.kroy.kmp_project.ui.search.components.SearchBarScreen
import com.kroy.kmp_project.utils.articles

@Composable
fun SearchScreen(){
    var searchQuery by rememberSaveable(){
        mutableStateOf("")
    }
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
                   println(query)
               }

           }
       )
      ArticleListScreen(articles)
   }

}

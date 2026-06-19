package com.kroy.kmp_project.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.kroy.kmp_project.data.model.Article
import com.kroy.kmp_project.theme.xLargePadding
import com.kroy.kmp_project.utils.Type
import com.kroy.kmp_project.utils.articles
import com.kroy.kmp_project.utils.getType

@Composable
fun ArticleListScreen(articleList:List<Article>){
    val isDesktop: Boolean = remember{
        getType() == Type.Desktop
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(if (isDesktop) 3 else 1 ),
        verticalArrangement = Arrangement.spacedBy(xLargePadding),
        horizontalArrangement = Arrangement.spacedBy(xLargePadding),
        contentPadding = PaddingValues(xLargePadding)
    ){
        items(articleList, key = { it.source.id + it.publishedAt }){
            ArticleItem(article = it, onClick = {})
        }
    }
}
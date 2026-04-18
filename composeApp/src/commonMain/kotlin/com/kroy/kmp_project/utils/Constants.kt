package com.kroy.kmp_project.utils

import com.kroy.kmp_project.ui.navigation.BottomNavigationitem
import kmp_project.composeapp.generated.resources.Res
import kmp_project.composeapp.generated.resources.bookmark
import kmp_project.composeapp.generated.resources.headlines
import kmp_project.composeapp.generated.resources.ic_bookmark_filled
import kmp_project.composeapp.generated.resources.ic_headline
import kmp_project.composeapp.generated.resources.ic_search
import kmp_project.composeapp.generated.resources.search
import org.jetbrains.compose.resources.stringResource

enum class Type(){
    Mobile,
    Desktop,
    Web
}

val bottomeNavigationitemList = listOf(
    BottomNavigationitem(
        icon = Res.drawable.ic_headline ,
        title = Res.string.headlines,
        route = "headline"
    ),
    BottomNavigationitem(
        icon = Res.drawable.ic_search ,
        title = Res.string.search,
        route = "search"
    ),
    BottomNavigationitem(
        icon = Res.drawable.ic_bookmark_filled ,
        title = Res.string.bookmark,
        route = "bookmark"
    ),
    )

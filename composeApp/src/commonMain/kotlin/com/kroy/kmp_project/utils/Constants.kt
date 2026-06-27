package com.kroy.kmp_project.utils

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import com.kroy.kmp_project.data.model.Article
import com.kroy.kmp_project.data.model.Source
import com.kroy.kmp_project.ui.MainScreen
import com.kroy.kmp_project.ui.navigation.BottomNavigationitem
import com.kroy.kmp_project.ui.navigation.MainRouteScreen
import kmp_project.composeapp.generated.resources.Res
import kmp_project.composeapp.generated.resources.bookmark
import kmp_project.composeapp.generated.resources.headlines
import kmp_project.composeapp.generated.resources.ic_bookmark_filled
import kmp_project.composeapp.generated.resources.ic_headline
import kmp_project.composeapp.generated.resources.ic_search
import kmp_project.composeapp.generated.resources.search
import org.jetbrains.compose.resources.stringResource
import kotlin.random.Random

enum class Type(){
    Mobile,
    Desktop,
    Web
}

val bottomeNavigationitemList = listOf(
    BottomNavigationitem(
        icon = Res.drawable.ic_headline ,
        title = Res.string.headlines,
        route = MainRouteScreen.Headlines.route
    ),
    BottomNavigationitem(
        icon = Res.drawable.ic_search ,
        title = Res.string.search,
        route = MainRouteScreen.Search.route
    ),
    BottomNavigationitem(
        icon = Res.drawable.ic_bookmark_filled ,
        title = Res.string.bookmark,
        route = MainRouteScreen.Bookmark.route
    ),
    )


val articles: List<Article> = listOf(
    Article(
        source = Source("dwa", "My news"),
        author = "The author",
        title = "This is the main news title headline. This is the main news title headline.",
        description = "This is the main news description. This is the main news description. This is the main news description",
        url = "",
        urlToImage = "https://www.marketscreener.com/images/reuters/2024-03-05T144855Z_1_LYNXNPEK240IP_RTROPTP_3_GERMANY-TESLA-FIRE.JPG",
        publishedAt = Random(100).nextInt().toString(),
        content = "What is the content?"
    ),
    Article(
        source = Source("dawdwa", "My news"),
        author = "The author",
        title = "This is the main news title headline. This is the main news title headline.",
        description = "This is the main news description. This is the main news description. This is the main news description",
        url = "",
        urlToImage = "https://www.marketscreener.com/images/reuters/2024-03-05T144855Z_1_LYNXNPEK240IP_RTROPTP_3_GERMANY-TESLA-FIRE.JPG",
        publishedAt = Random(100).nextInt().toString(),
        content = "What is the content?"
    ),
    Article(
        source = Source("dwakjyk", "My news"),
        author = "The author",
        title = "This is the main news title headline. This is the main news title headline.",
        description = "This is the main news description. This is the main news description. This is the main news description",
        url = "",
        urlToImage = "https://www.marketscreener.com/images/reuters/2024-03-05T144855Z_1_LYNXNPEK240IP_RTROPTP_3_GERMANY-TESLA-FIRE.JPG",
        publishedAt = Random(100).nextInt().toString(),
        content = "What is the content?"
    ),
    Article(
        source = Source("dwserfewa", "My news"),
        author = "The author",
        title = "This is the main news title headline. This is the main news title headline.",
        description = "This is the main news description. This is the main news description. This is the main news description",
        url = "",
        urlToImage = "https://www.marketscreener.com/images/reuters/2024-03-05T144855Z_1_LYNXNPEK240IP_RTROPTP_3_GERMANY-TESLA-FIRE.JPG",
        publishedAt = Random(100).nextInt().toString(),
        content = "What is the content?"
    )
)

val FadeIn = fadeIn(animationSpec = tween(220, delayMillis = 90)) +
        scaleIn(
            initialScale = 0.92f,
            animationSpec = tween(220, delayMillis = 90)
        )

val FadeOut = fadeOut(animationSpec = tween(90))

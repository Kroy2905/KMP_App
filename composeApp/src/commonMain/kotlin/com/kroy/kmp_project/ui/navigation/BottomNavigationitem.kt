package com.kroy.kmp_project.ui.navigation

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

data class BottomNavigationitem(
    val icon: DrawableResource,
    val title : StringResource,
    val route : String
)

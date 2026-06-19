package com.kroy.kmp_project

import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application



fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "KMP_Project",
        state = WindowState(
            position = WindowPosition(Alignment.Center)
        )
    ) {
        window.minimumSize = java.awt.Dimension(1280,768)
        App()
    }
}
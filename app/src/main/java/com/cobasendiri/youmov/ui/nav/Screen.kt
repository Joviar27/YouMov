package com.cobasendiri.youmov.ui.nav

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {
    @Serializable
    data object Home : Screen

    @Serializable
    data class Detail(
        val movieId: Int
    ): Screen

    @Serializable
    data object Favorite : Screen
}
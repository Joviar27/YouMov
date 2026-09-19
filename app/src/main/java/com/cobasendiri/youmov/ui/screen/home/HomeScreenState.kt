package com.cobasendiri.youmov.ui.screen.home

data class HomeScreenState(
    val popularLoading: Boolean = false,
    val topRatedLoading: Boolean = false,
    val nowPlayingLoading: Boolean = false,
    val errorMessage: String = ""
)
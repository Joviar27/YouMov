package com.cobasendiri.youmov.ui.screen.home

import com.cobasendiri.youmov.model.Movie

data class HomeScreenState(
    val popularLoading: Boolean = false,
    val topRatedLoading: Boolean = false,
    val nowPlayingLoading: Boolean = false,
    val errorMessage: String = ""
)
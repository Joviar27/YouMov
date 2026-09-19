package com.cobasendiri.youmov.ui.screen.home

interface HomeScreenEvent {

    data class OnMovieClicked(val movieId: String): HomeScreenEvent

    data object OnFavoriteMenuClicked: HomeScreenEvent

    data object OnRefreshClicked: HomeScreenEvent
}
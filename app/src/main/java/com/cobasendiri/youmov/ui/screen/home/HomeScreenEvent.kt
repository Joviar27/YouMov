package com.cobasendiri.youmov.ui.screen.home

interface HomeScreenEvent {

    data class OnMovieClicked(val movieId: Int): HomeScreenEvent

    data object OnFavoriteMenuClicked: HomeScreenEvent

    data object OnPopularRefreshClicked: HomeScreenEvent

    data object OnTopRatedRefreshClicked: HomeScreenEvent

    data object OnNowPlayingRefreshClicked: HomeScreenEvent

    data object OnPopularRetryClicked: HomeScreenEvent

    data object OnTopRatedRetryClicked: HomeScreenEvent

    data object OnNowPlayingRetryClicked: HomeScreenEvent

}
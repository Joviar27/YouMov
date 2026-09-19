package com.cobasendiri.youmov.ui.screen.favorite

interface FavoriteScreenEvent {

    data object OnNavigateBack: FavoriteScreenEvent

    data class OnMovieClicked(val id: String): FavoriteScreenEvent
}
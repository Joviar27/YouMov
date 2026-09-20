package com.cobasendiri.youmov.ui.screen.detail

interface DetailScreenEvent {

    data object OnNavigateBack: DetailScreenEvent

    data object OnRefresh: DetailScreenEvent

    data class OnFavoriteClick(val id: String): DetailScreenEvent

    data object OnShareClick: DetailScreenEvent

    data class OnReviewSectionClick(val number: Int): DetailScreenEvent
}
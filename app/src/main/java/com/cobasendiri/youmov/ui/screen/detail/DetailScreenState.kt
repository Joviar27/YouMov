package com.cobasendiri.youmov.ui.screen.detail

import com.cobasendiri.youmov.domain.model.ReviewItem

data class DetailScreenState(
    val id: Int = 0,
    val imagePath: String = "",
    val title: String = "",
    val isFavorite: Boolean = false,
    val releaseInfo: String = "",
    val description: String = "",
    val reviewItems: List<ReviewItem> = listOf(),
    val reviewSection: Int = 1,
    val reviewSectionCount: Int = 1,
    val loading: Boolean = false,
    val error: Boolean = false,
    val toastMessage: String = ""
)

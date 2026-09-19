package com.cobasendiri.youmov.ui.screen.detail

import com.cobasendiri.youmov.domain.model.Review

data class DetailScreenState(
    val imageUri: String,
    val title: String,
    val isFavorite: Boolean,
    val releaseInfo: String,
    val description: String,
    val reviews: List<Review>,
    val reviewSection: Int,
    val reviewSectionCount: Int,
    val loading: Boolean,
    val errorMessage: String
)

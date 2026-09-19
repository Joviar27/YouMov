package com.cobasendiri.youmov.domain.model

data class FavoriteMovie(
    val id: String,
    val imageUri: String,
    val title: String,
    val releaseInfo: String,
    val overview: String
)

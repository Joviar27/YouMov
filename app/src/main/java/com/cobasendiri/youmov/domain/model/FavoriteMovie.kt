package com.cobasendiri.youmov.domain.model

data class FavoriteMovie(
    val id: Int,
    val imagePath: String,
    val title: String,
    val releaseInfo: String,
    val overview: String
)

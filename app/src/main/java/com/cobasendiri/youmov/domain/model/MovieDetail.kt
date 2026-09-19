package com.cobasendiri.youmov.domain.model

data class MovieDetail(
    val id: Int,
    val imageUri: String,
    val title: String,
    val releaseInfo: String,
    val description: String
)
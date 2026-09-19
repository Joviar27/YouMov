package com.cobasendiri.youmov.domain.model

data class Review(
    val author: String,
    val avatarUri: String,
    val rating: String,
    val date: String,
    val content: String
)

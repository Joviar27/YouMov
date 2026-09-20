package com.cobasendiri.youmov.domain.model

data class MovieReview(
    val page: Int,
    val totalPages: Int,
    val reviews: List<ReviewItem>
)

data class ReviewItem(
    val author: String,
    val avatarUri: String,
    val rating: String,
    val date: String,
    val content: String
)

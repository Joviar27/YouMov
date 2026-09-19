package com.cobasendiri.youmov.ui.screen.favorite

import com.cobasendiri.youmov.domain.model.FavoriteMovie

data class FavoriteScreenState(
    val favoriteMovies: List<FavoriteMovie>,
    val errorMessage: String
)

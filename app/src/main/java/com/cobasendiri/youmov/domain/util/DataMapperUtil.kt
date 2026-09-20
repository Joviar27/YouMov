package com.cobasendiri.youmov.domain.util

import com.cobasendiri.youmov.data.local.entity.FavoriteMovieEntity
import com.cobasendiri.youmov.data.remote.response.DetailMovieResponse
import com.cobasendiri.youmov.data.remote.response.MovieItemResponse
import com.cobasendiri.youmov.data.remote.response.ReviewItemResponse
import com.cobasendiri.youmov.domain.model.FavoriteMovie
import com.cobasendiri.youmov.domain.model.Movie
import com.cobasendiri.youmov.domain.model.MovieDetail
import com.cobasendiri.youmov.domain.model.Review

fun MovieItemResponse.mapToLandscapeMovie(): Movie{
    return Movie(
        id = this.id ?: 0,
        imagePath = this.backdropPath ?: "",
        title = this.title ?: "",
        releaseInfo = this.releaseDate ?: ""
    )
}

fun MovieItemResponse.mapToSquareMovie(): Movie{
    return Movie(
        id = this.id ?: 0,
        imagePath = this.posterPath ?: "",
        title = this.title ?: "",
        releaseInfo = this.releaseDate ?: ""
    )
}

fun DetailMovieResponse.mapToMovieDetail(): MovieDetail{
    return MovieDetail(
        id = this.id ?: 0,
        imageUri = this.backdropPath ?: "",
        title = this.title ?: "",
        releaseInfo = this.releaseDate ?: "",
        description = this.overview ?: ""
    )
}

fun ReviewItemResponse.mapToReview(): Review{
    return Review(
        author = this.author ?: "",
        avatarUri = this.authorDetails?.avatarPath ?: "",
        rating = this.authorDetails?.rating?.toString() ?: "",
        date = this.createdAt ?: "",
        content = this.content ?: ""
    )
}

fun List<FavoriteMovieEntity>.mapToFavoriteMovieList(): List<FavoriteMovie>{
    return this.map {
        it.mapToFavoriteMovie()
    }
}

fun FavoriteMovieEntity.mapToFavoriteMovie(): FavoriteMovie {
    return FavoriteMovie(
        id = this.id,
        imagePath = this.imagePath,
        title = this.title,
        releaseInfo = this.releaseInfo,
        overview = this.overview
    )
}

fun FavoriteMovie.mapToFavoriteMovieEntity(): FavoriteMovieEntity {
    return FavoriteMovieEntity(
        id = this.id,
        imagePath = this.imagePath,
        title = this.title,
        releaseInfo = this.releaseInfo,
        overview = this.overview
    )
}

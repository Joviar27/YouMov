package com.cobasendiri.youmov.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies")
data class FavoriteMovieEntity(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "image_path")
    val imagePath: String,

    val title: String,

    @ColumnInfo(name = "release_info")
    val releaseInfo: String,

    val overview: String
)
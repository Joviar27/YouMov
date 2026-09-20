package com.cobasendiri.youmov.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cobasendiri.youmov.data.local.entity.FavoriteMovieEntity

@Database(
    entities = [FavoriteMovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FavoriteDatabase: RoomDatabase(){
    abstract fun favoriteMovieDao(): FavoriteMovieDao
}
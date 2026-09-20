package com.cobasendiri.youmov.di

import android.content.Context
import androidx.room.Room
import com.cobasendiri.youmov.data.local.room.FavoriteDatabase
import com.cobasendiri.youmov.data.local.room.FavoriteMovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideFavoriteDatabase(
        @ApplicationContext context: Context
    ): FavoriteDatabase{
        return  Room.databaseBuilder(
            context.applicationContext,
            FavoriteDatabase::class.java,
            "Favorite.db"
        ).build()
    }

    @Provides
    fun favoriteMovieDao(favoriteDatabase: FavoriteDatabase): FavoriteMovieDao = favoriteDatabase.favoriteMovieDao()

}
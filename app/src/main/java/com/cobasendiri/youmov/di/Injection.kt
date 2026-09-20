package com.cobasendiri.youmov.di

import android.content.Context
import com.cobasendiri.youmov.data.MovieRepository
import com.cobasendiri.youmov.data.local.room.FavoriteDatabase
import com.cobasendiri.youmov.data.local.room.FavoriteMovieDao
import com.cobasendiri.youmov.data.remote.network.ApiClient
import com.cobasendiri.youmov.data.remote.network.ApiService

object Injection {

    private fun provideApiService(): ApiService{
        return ApiClient.apiService
    }

    private fun provideFavoriteMovieDao(context: Context): FavoriteMovieDao{
        return FavoriteDatabase.getDatabase(context).favoriteMovieDao()
    }

    fun provideRepository(context: Context): MovieRepository{
        return MovieRepository.getInstance(
            provideApiService(),
            provideFavoriteMovieDao(context)
        )
    }
}
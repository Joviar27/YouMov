package com.cobasendiri.youmov.di

import com.cobasendiri.youmov.data.MovieRepository
import com.cobasendiri.youmov.data.network.ApiClient
import com.cobasendiri.youmov.data.network.ApiService

object Injection {

    private fun provideApiService(): ApiService{
        return ApiClient.apiService
    }

    fun provideRepository(): MovieRepository{
        return MovieRepository.getInstance(
            provideApiService()
        )
    }
}
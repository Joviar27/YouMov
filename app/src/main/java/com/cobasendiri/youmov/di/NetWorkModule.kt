package com.cobasendiri.youmov.di

import com.cobasendiri.youmov.BuildConfig
import com.cobasendiri.youmov.data.remote.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetWorkModule {

    companion object Type{
        const val HEADER_INTERCEPTOR = "header-interceptor"
        const val LOGGING_INTERCEPTOR = "logging-interceptor"
    }

    @Singleton
    @Provides
    @Named(LOGGING_INTERCEPTOR)
    fun provideLoggingInterceptor(): Interceptor{
       return HttpLoggingInterceptor().apply {
           level = if (BuildConfig.DEBUG) {
               HttpLoggingInterceptor.Level.BODY
           } else {
               HttpLoggingInterceptor.Level.NONE
           }
       }
    }

    @Singleton
    @Provides
    @Named(HEADER_INTERCEPTOR)
    fun provideHeaderInterceptor(): Interceptor{
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val requestWithHeaders = originalRequest.newBuilder()
                .header("Authorization", "Bearer ${BuildConfig.API_KEY}")
                .header("Accept", "application/json")
                .build()
            chain.proceed(requestWithHeaders)
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @Named(HEADER_INTERCEPTOR) headerInterceptor: Interceptor,
        @Named(LOGGING_INTERCEPTOR) loggingInterceptor: Interceptor
    ): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headerInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService{
        return retrofit.create(ApiService::class.java)
    }

}
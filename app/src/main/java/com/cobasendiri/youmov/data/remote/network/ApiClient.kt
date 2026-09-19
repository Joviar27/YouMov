package com.cobasendiri.youmov.data.remote.network

import com.cobasendiri.youmov.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = if(BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        }else{
            HttpLoggingInterceptor.Level.NONE
        }
    }

    private val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val urlWithQueryParams = originalRequest.url
            .newBuilder()
            .addQueryParameter("key", BuildConfig.API_KEY)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(urlWithQueryParams)
            .build()

        chain.proceed(newRequest)
    }

    private val headerInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val requestWithHeaders = originalRequest.newBuilder()
            .header("Accept", "application/json")
            .build()
        chain.proceed(requestWithHeaders)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .addInterceptor(headerInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }
}
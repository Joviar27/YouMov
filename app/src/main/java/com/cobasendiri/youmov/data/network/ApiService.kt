package com.cobasendiri.youmov.data.network

import com.cobasendiri.youmov.data.response.DetailMovieResponse
import com.cobasendiri.youmov.data.response.MovieListResponse
import com.cobasendiri.youmov.data.response.MovieReviewListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
    ): MovieListResponse

    @GET("/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
    ): MovieListResponse

    @GET("/movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int,
    ): MovieListResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int
    ): DetailMovieResponse

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int
    ): MovieReviewListResponse
}
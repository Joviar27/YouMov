package com.cobasendiri.youmov.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.cobasendiri.youmov.data.remote.MoviePagingSource
import com.cobasendiri.youmov.data.remote.network.ApiService
import com.cobasendiri.youmov.domain.Result
import com.cobasendiri.youmov.domain.model.Movie
import com.cobasendiri.youmov.domain.model.MovieDetail
import com.cobasendiri.youmov.domain.model.Review
import com.cobasendiri.youmov.domain.util.mapToLandscapeMovie
import com.cobasendiri.youmov.domain.util.mapToMovieDetail
import com.cobasendiri.youmov.domain.util.mapToReview
import com.cobasendiri.youmov.domain.util.mapToSquareMovie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class MovieRepository(
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    companion object {
        private const val PAGE_SIZE = 20

        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(apiService: ApiService): MovieRepository {
            return instance ?: synchronized(this) {
                instance ?: MovieRepository(apiService)
                    .also { instance = it }
            }
        }
    }

    private val pagingConfig = PagingConfig(
        pageSize = PAGE_SIZE,
        initialLoadSize = 10,
        prefetchDistance = 2
    )

    fun getPopularMovieList(): Flow<PagingData<Movie>> {
        return Pager(
            config = pagingConfig
        ) {
            MoviePagingSource { page ->
                apiService.getPopularMovies(page)
            }
        }.flow.map { pagingData ->
            pagingData.map {
                it.mapToLandscapeMovie()
            }
        }
    }

    fun getTopRatedMovieList(): Flow<PagingData<Movie>> {
        return Pager(
            config = pagingConfig
        ) {
            MoviePagingSource { page ->
                apiService.getTopRatedMovies(page)
            }
        }.flow.map { pagingData ->
            pagingData.map {
                it.mapToSquareMovie()
            }
        }
    }

    fun getNowPlayingMovieList(): Flow<PagingData<Movie>> {
        return Pager(
            config = pagingConfig
        ) {
            MoviePagingSource { page ->
                apiService.getNowPlayingMovies(page)
            }
        }.flow.map { pagingData ->
            pagingData.map {
                it.mapToSquareMovie()
            }
        }
    }

    suspend fun getMovieDetail(
        movieId: Int
    ): Result<MovieDetail> = withContext(dispatcher){
        try {
            val result = apiService.getMovieDetail(movieId).mapToMovieDetail()
            Result.Success(result)
        }catch (e: Exception){
            Result.Error(e)
        }
    }

    suspend fun getReviewList(
        movieId: Int,
        page: Int
    ): Result<List<Review>> = withContext(dispatcher){
        try {
            val result = apiService.getMovieReviews(movieId, page).results?.map {
                it.mapToReview()
            } ?: emptyList()
            Result.Success(result)
        }catch (e: Exception){
            Result.Error(e)
        }
    }

}
package com.cobasendiri.youmov.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.cobasendiri.youmov.data.local.room.FavoriteMovieDao
import com.cobasendiri.youmov.data.remote.MoviePagingSource
import com.cobasendiri.youmov.data.remote.network.ApiService
import com.cobasendiri.youmov.domain.Result
import com.cobasendiri.youmov.domain.model.FavoriteMovie
import com.cobasendiri.youmov.domain.model.Movie
import com.cobasendiri.youmov.domain.model.MovieDetail
import com.cobasendiri.youmov.domain.model.MovieReview
import com.cobasendiri.youmov.domain.model.ReviewItem
import com.cobasendiri.youmov.domain.util.mapToFavoriteMovieEntity
import com.cobasendiri.youmov.domain.util.mapToFavoriteMovieList
import com.cobasendiri.youmov.domain.util.mapToLandscapeMovie
import com.cobasendiri.youmov.domain.util.mapToMovieDetail
import com.cobasendiri.youmov.domain.util.mapToReview
import com.cobasendiri.youmov.domain.util.mapToSquareMovie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class MovieRepository(
    private val apiService: ApiService,
    private val favoriteMovieDao: FavoriteMovieDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    companion object {
        private const val PAGE_SIZE = 20

        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(apiService: ApiService, favoriteMovieDao: FavoriteMovieDao): MovieRepository {
            return instance ?: synchronized(this) {
                instance ?: MovieRepository(apiService, favoriteMovieDao)
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

    suspend fun getMovieReview(
        movieId: Int,
        page: Int
    ): Result<MovieReview> = withContext(dispatcher){
        try {
            val result = apiService.getMovieReviews(movieId, page).let {
                MovieReview(
                    page = it.page ?: 1,
                    totalPages = it.totalPages ?: 1,
                    reviews = it.results?.map { reviewResponse ->
                        reviewResponse.mapToReview()
                    } ?: listOf()
                )
            }
            Result.Success(result)
        }catch (e: Exception){
            Result.Error(e)
        }
    }

    fun getFavoriteMovies(): Flow<Result<List<FavoriteMovie>>>{
        return favoriteMovieDao.getFavoriteMovies().map {
            Result.Success(it.mapToFavoriteMovieList())
        }.catch {
            Result.Error(it)
        }.flowOn(dispatcher)
    }

    fun isMovieFavorite(movieId: Int): Flow<Result<Boolean>>{
        return favoriteMovieDao.isMovieFavorite(movieId).map {
            Result.Success(it)
        }.catch {
            Result.Error(it)
        }.flowOn(dispatcher)
    }

    suspend fun addFavoriteMovie(
        favoriteMovie: FavoriteMovie
    ): Result<Unit> = withContext(dispatcher){
        try {
            val result = favoriteMovieDao.addFavorite(favoriteMovie.mapToFavoriteMovieEntity())
            Result.Success(result)
        }catch (e: Exception){
            Result.Error(e)
        }
    }

    suspend fun removeFavoriteMovie(
        movieId: Int
    ): Result<Unit> = withContext(dispatcher){
        try {
            val result = favoriteMovieDao.removeFavorite(movieId)
            Result.Success(result)
        }catch (e: Exception){
            Result.Error(e)
        }
    }

}
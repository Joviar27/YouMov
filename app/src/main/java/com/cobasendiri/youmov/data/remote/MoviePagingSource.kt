package com.cobasendiri.youmov.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cobasendiri.youmov.data.remote.response.MovieItemResponse
import com.cobasendiri.youmov.data.remote.response.MovieListResponse

class MoviePagingSource(
    private val getMovies: suspend (page: Int) -> MovieListResponse
) : PagingSource<Int, MovieItemResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItemResponse> {
        return try {
            val currentPage = params.key ?: 1
            val response = getMovies.invoke(currentPage)

            val movieItems = response.results ?: emptyList()

            val nextKey = if (movieItems.isEmpty()) {
                null
            } else if(response.totalPages != null){
                if (currentPage < response.totalPages) currentPage + 1 else null
            } else{
                currentPage + 1
            }

            LoadResult.Page(
                data = movieItems,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieItemResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
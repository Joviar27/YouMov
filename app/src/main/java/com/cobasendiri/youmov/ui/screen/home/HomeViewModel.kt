package com.cobasendiri.youmov.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.cobasendiri.youmov.data.MovieRepository

class HomeViewModel(
    private val repository: MovieRepository
): ViewModel() {

    val popularMovies =  repository.getPopularMovieList().cachedIn(viewModelScope)
    val topRatedMovies = repository.getTopRatedMovieList().cachedIn(viewModelScope)
    val nowPlayingMovies = repository.getNowPlayingMovieList().cachedIn(viewModelScope)
}
package com.cobasendiri.youmov.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.cobasendiri.youmov.data.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
): ViewModel() {

    val popularMovies =  repository.getPopularMovieList().cachedIn(viewModelScope)
    val topRatedMovies = repository.getTopRatedMovieList().cachedIn(viewModelScope)
    val nowPlayingMovies = repository.getNowPlayingMovieList().cachedIn(viewModelScope)
}
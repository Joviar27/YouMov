package com.cobasendiri.youmov.ui.screen.favorite

import androidx.lifecycle.viewModelScope
import com.cobasendiri.youmov.data.MovieRepository
import com.cobasendiri.youmov.ui.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val movieRepository: MovieRepository
) : BaseViewModel(){

    private val _state = MutableStateFlow(FavoriteScreenState())
    val state: StateFlow<FavoriteScreenState> get() = _state

    init {
        getFavoriteMovies()
    }

    fun getFavoriteMovies(){
        viewModelScope.launch {
            movieRepository.getFavoriteMovies().collect { result ->
                result.handleResult { favMovies ->
                    _state.update {
                        it.copy(favoriteMovies = favMovies)
                    }
                }
            }
        }
    }

    override fun showToast(message: String) {
        _state.update {
            it.copy(toastMessage = message)
        }
    }

    fun consumeToast(){
        _state.update {
            it.copy(toastMessage = "")
        }
    }
}
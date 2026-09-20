package com.cobasendiri.youmov.ui.screen.detail

import androidx.lifecycle.viewModelScope
import com.cobasendiri.youmov.data.MovieRepository
import com.cobasendiri.youmov.domain.model.FavoriteMovie
import com.cobasendiri.youmov.ui.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    private val movieRepository: MovieRepository
): BaseViewModel() {

    private val _state = MutableStateFlow(DetailScreenState())
    val state: StateFlow<DetailScreenState> get() = _state

    fun refreshDetailPageInfo(movieId: Int){
        getMovieDetail(movieId)
        isMovieFavorite(movieId)
        getMovieReviews(movieId, 1)
    }

    fun getMovieDetail(movieId: Int){
        viewModelScope.launch {
            showLoading(true)
            movieRepository.getMovieDetail(movieId).handleResult(
                onError = {
                    _state.update { it.copy(error = true ) }
                }
            ) { movieDetail ->
                _state.update {
                    it.copy(
                        id = movieDetail.id,
                        imagePath = movieDetail.imagePath,
                        title = movieDetail.title,
                        releaseInfo = movieDetail.releaseInfo,
                        description = movieDetail.description,
                        error = false
                    )
                }
                showLoading(false)
            }
        }
    }

    fun isMovieFavorite(movieId: Int){
        viewModelScope.launch {
            movieRepository.isMovieFavorite(movieId).collect { result ->
                result.handleResult { isFavorite ->
                    _state.update {
                        it.copy(isFavorite = isFavorite)
                    }
                }
            }
        }
    }

    fun getMovieReviews(movieId: Int, page: Int){
        viewModelScope.launch {
            movieRepository.getMovieReview(movieId, page).handleResult { movieReview ->
                _state.update {
                    it.copy(
                        reviewItems = movieReview.reviews,
                        reviewSection = movieReview.page,
                        reviewSectionCount = movieReview.totalPages
                    )
                }
            }
        }
    }

    fun updateFavorite(){
        viewModelScope.launch {
            if(_state.value.isFavorite){
                movieRepository.removeFavoriteMovie(_state.value.id).handleResult {
                    showToast("Movie removed from favorite")
                }
            }else{
                movieRepository.addFavoriteMovie(
                    _state.value.let {
                        FavoriteMovie(
                            id = it.id,
                            imagePath = it.imagePath,
                            title = it.title,
                            releaseInfo = it.releaseInfo,
                            overview = it.description,
                        )
                    }
                ).handleResult {
                    showToast("Movie added to favorite")
                }
            }
        }
    }

    override fun showToast(message: String) {
        _state.update {
            it.copy(toastMessage = message)
        }
    }

    fun showLoading(isLoading: Boolean) {
        _state.update {
            it.copy(loading = isLoading)
        }
    }
}
package com.tatiana.rodionova.androidacademyproject.ui.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tatiana.rodionova.androidacademyproject.data.repository.MovieRepository
import com.tatiana.rodionova.androidacademyproject.model.Movie
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class MovieDetailsState {
    object Loading : MovieDetailsState()
    object Error : MovieDetailsState()
    class Success(val movie: Movie?) : MovieDetailsState()
}

class MovieDetailsViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val state: MutableLiveData<MovieDetailsState> by lazy {
        MutableLiveData<MovieDetailsState>()
    }

    init {
        state.postValue(MovieDetailsState.Loading)
    }

    fun getMovieById(id: Long): LiveData<MovieDetailsState> {
        viewModelScope.launch {
            try {
                withContext(coroutineContext) { movieRepository.loadMovie(id) }.collect { detailedMovie ->
                    state.postValue(MovieDetailsState.Success(movie = detailedMovie))
                }
            } catch (e: Exception) {
                state.postValue(MovieDetailsState.Error)
            }
        }
        return state
    }
}

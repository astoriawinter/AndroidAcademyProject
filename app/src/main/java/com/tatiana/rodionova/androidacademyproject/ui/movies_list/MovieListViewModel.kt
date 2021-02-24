package com.tatiana.rodionova.androidacademyproject.ui.movies_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tatiana.rodionova.androidacademyproject.data.repository.MovieRepository
import com.tatiana.rodionova.androidacademyproject.model.Movie
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class MovieListState {
    object Loading : MovieListState()
    object Error : MovieListState()
    class Success(val movie: List<Movie>) : MovieListState()
}

class MovieListViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val state: MutableLiveData<MovieListState> by lazy {
        MutableLiveData<MovieListState>().also {
            loadMoviesList()
        }
    }

    val movies: LiveData<MovieListState> = state

    init {
        state.postValue(MovieListState.Loading)
    }

    private fun loadMoviesList() {
        viewModelScope.launch {
            try {
                withContext(coroutineContext) { movieRepository.loadPopularMovies() }.collect { movieList ->
                    state.postValue(MovieListState.Success(movie = movieList))
                }
            } catch (e: Exception) {
                state.postValue(MovieListState.Error)
            }
        }
    }
}

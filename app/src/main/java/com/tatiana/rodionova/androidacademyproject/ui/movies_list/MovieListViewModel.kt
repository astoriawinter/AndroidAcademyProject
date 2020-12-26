package com.tatiana.rodionova.androidacademyproject.ui.movies_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tatiana.rodionova.androidacademyproject.model.Movie
import com.tatiana.rodionova.androidacademyproject.model.loadMovies
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class MovieState {
    object Loading : MovieState()
    object Error : MovieState()
    class Success(val moves: List<Movie>) : MovieState()
}

class MovieListViewModel(application: Application) : AndroidViewModel(application) {

    private val state: MutableLiveData<MovieState> by lazy {
        MutableLiveData<MovieState>().also {
            loadMoviesList()
        }
    }

    init {
        state.postValue(MovieState.Loading)
    }

    fun getMovies(): LiveData<MovieState> = state

    private fun loadMoviesList() {
        viewModelScope.launch {
            try {
                /* repository is not added yet, as I was lazy to add DI (⇀‸↼‶) */
                val list = withContext(coroutineContext) { loadMovies(getApplication()) }
                state.postValue(MovieState.Success(moves = list))
            } catch (e: Exception) {
                state.postValue(MovieState.Error)
            }
        }
    }
}

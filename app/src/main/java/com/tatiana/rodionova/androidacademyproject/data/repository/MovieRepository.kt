package com.tatiana.rodionova.androidacademyproject.data.repository

import android.content.Context
import com.tatiana.rodionova.androidacademyproject.data.network.MovieApi
import com.tatiana.rodionova.androidacademyproject.model.Movie

class MovieRepository(val context: Context, private val api: MovieApi) {

    suspend fun loadPopularMovies(): List<Movie> = api.getMovies()

    suspend fun loadMovie(id: Long): Movie? = api.getMovieById(id)
}
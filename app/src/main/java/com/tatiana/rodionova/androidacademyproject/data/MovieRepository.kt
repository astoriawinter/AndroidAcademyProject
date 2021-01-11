package com.tatiana.rodionova.androidacademyproject.data

import android.content.Context
import com.tatiana.rodionova.androidacademyproject.model.Movie
import com.tatiana.rodionova.androidacademyproject.model.loadMovies

class MovieRepository(val context: Context) {

    suspend fun getMovies(): List<Movie> = loadMovies(context)

    suspend fun getMovieById(id: Long): Movie? = loadMovies(context).find { it.id == id }
}
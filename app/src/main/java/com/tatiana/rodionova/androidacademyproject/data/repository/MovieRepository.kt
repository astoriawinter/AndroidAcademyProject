package com.tatiana.rodionova.androidacademyproject.data.repository

import com.tatiana.rodionova.androidacademyproject.data.db.dao.ActorDao
import com.tatiana.rodionova.androidacademyproject.data.db.dao.GenreDao
import com.tatiana.rodionova.androidacademyproject.data.db.dao.MovieDao
import com.tatiana.rodionova.androidacademyproject.data.db.dao.MovieDetailedDao
import com.tatiana.rodionova.androidacademyproject.data.db.mapper.*
import com.tatiana.rodionova.androidacademyproject.data.network.MovieApi
import com.tatiana.rodionova.androidacademyproject.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepository(
    private val api: MovieApi,
    private val movieDao: MovieDao,
    private val genreDao: GenreDao,
    private val actorDao: ActorDao,
    private val movieDetailedDao: MovieDetailedDao
) {

    suspend fun loadPopularMovies(): Flow<List<Movie>> = flow {
        if (isDatabaseEmpty()) {
            val movies = getMovieFromApi()
            emit(movies)

            movieDao.insertMovies(movies.mapMovieToMovieEntity())
            genreDao.insertGenres(movies.mapMovieToGenreEntity())
        } else {
            emit(movieDao.getMovies().toMovie())

            val movies = getMovieFromApi()
            movieDao.updateMovies(movies.mapMovieToMovieEntity())
            emit(movies)
        }
    }

    private suspend fun getMovieFromApi() =
        api.getMovies().sortedWith(compareByDescending<Movie> { it.ratings }.thenBy { it.title })

    private suspend fun isDatabaseEmpty(): Boolean = movieDao.getNumberOfRecords() <= 0

    private suspend fun isDetailedMovieIsEmpty(id: Long): Boolean =
        movieDetailedDao.getNumberOfRecords(id) <= 0

    suspend fun loadMovie(id: Long): Flow<Movie> = flow {
        if (isDetailedMovieIsEmpty(id)) {
            api.getMovieById(id).let { movie ->
                emit(movie)

                movieDetailedDao.insertMovieDetails(movie.mapToMovieDetailedEntity())
                actorDao.insertActors(movie.mapMovieToActorEntity())
            }
        } else {
            emit(movieDetailedDao.getMovieDetailed(id).toMovie())

            val movie = api.getMovieById(id)
            movieDetailedDao.updateMovieDetails(movie.mapToMovieDetailedEntity())
        }
    }
}
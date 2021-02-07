package com.tatiana.rodionova.androidacademyproject.data.repository

import android.content.Context
import androidx.work.*
import com.tatiana.rodionova.androidacademyproject.data.db.dao.ActorDao
import com.tatiana.rodionova.androidacademyproject.data.db.dao.GenreDao
import com.tatiana.rodionova.androidacademyproject.data.db.dao.MovieDao
import com.tatiana.rodionova.androidacademyproject.data.db.dao.MovieDetailedDao
import com.tatiana.rodionova.androidacademyproject.data.db.entity.MovieWithGenreCrossRef
import com.tatiana.rodionova.androidacademyproject.data.db.mapper.*
import com.tatiana.rodionova.androidacademyproject.data.network.MovieApi
import com.tatiana.rodionova.androidacademyproject.data.service.MoviesLoaderWorker
import com.tatiana.rodionova.androidacademyproject.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinApiExtension
import java.util.concurrent.TimeUnit

class MovieRepository(
    private val context: Context,
    private val api: MovieApi,
    private val movieDao: MovieDao,
    private val genreDao: GenreDao,
    private val actorDao: ActorDao,
    private val movieDetailedDao: MovieDetailedDao
) {

    suspend fun loadPopularMovies(): Flow<List<Movie>> =
        if (isDatabaseEmpty()) {
            getMoviesFromApiAndUpdateDB()
        } else getMoviesFromDBAndUpdateFromApi()

    private fun getMoviesFromApiAndUpdateDB(): Flow<List<Movie>> = flow {
        val movies = getMovieFromApi()
        emit(movies)

        movieDao.insertMovies(movies.mapMovieToMovieEntity())
        genreDao.insertGenres(movies.mapMovieToGenreEntity())
        movieDao.insertMoviesWithGenres(movies.flatMap { movie ->
            movie.genres.map { genre ->
                MovieWithGenreCrossRef(movieId = movie.id, genreId = genre.id)
            }
        })
    }

    suspend fun lazyDBUpdate() = withContext(Dispatchers.IO) {
        val movies = getMovieFromApi()
        movieDao.insertMovies(movies.mapMovieToMovieEntity())
    }

    @KoinApiExtension
    fun setPeriodicMoviesLoadingTask() {
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()

        val periodicWork =
            PeriodicWorkRequestBuilder<MoviesLoaderWorker>(UPDATE_PERIOD, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            LOAD_MOVIES_TASK,
            ExistingPeriodicWorkPolicy.REPLACE,
            periodicWork
        )
    }

    private fun getMoviesFromDBAndUpdateFromApi(): Flow<List<Movie>> =
        movieDao.getMoviesUntilChanged().map { movieWithGenre ->  movieWithGenre.toMovie() }

    private suspend fun getMovieFromApi(): List<Movie> =
        api.getMovies().sortedWith(compareByDescending<Movie> { it.ratings }.thenBy { it.title })

    private suspend fun isDatabaseEmpty(): Boolean =
        movieDao.getNumberOfRecords() <= 0

    private suspend fun isDetailedMovieIsEmpty(id: Long): Boolean =
        movieDetailedDao.getNumberOfRecords(id) <= 0

    fun loadMovie(id: Long): Flow<Movie> = flow {
        if (isDetailedMovieIsEmpty(id)) {
            api.getMovieById(id).let { movie ->
                emit(movie)

                movieDetailedDao.insertMovieDetails(movie.mapToMovieDetailedEntity())
                actorDao.insertActors(movie.mapMovieToActorEntity())
            }
        } else {
            emit(movieDetailedDao.getMovieDetailed(id).toMovie())

            val movie = api.getMovieById(id)
            movieDetailedDao.insertMovieDetails(movie.mapToMovieDetailedEntity())
        }
    }

    companion object {

        const val LOAD_MOVIES_TASK = "load movies"

        const val UPDATE_PERIOD = 8L
    }
}
package com.tatiana.rodionova.androidacademyproject.data.network

import com.tatiana.rodionova.androidacademyproject.model.*
import io.ktor.client.*
import io.ktor.client.request.*

class MovieApi(private val httpClient: HttpClient) {

    suspend fun getMovies(): List<Movie> {
        val movies = httpClient.get<JsonResponseMovie>("${BASE_URL}movie/top_rated").results
        val config = httpClient.get<JsonImage>("${BASE_URL}configuration")

        return movies.map { jsonMovie ->
            val details = httpClient.getMovieDetailsJson(jsonMovie.id)
            Movie(
                id = jsonMovie.id,
                title = jsonMovie.title,
                poster = config.images.base_url + config.images.poster_sizes[3] + jsonMovie.posterPicture,
                ratings = jsonMovie.ratings,
                numberOfRatings = details.vote_count,
                minimumAge = if (jsonMovie.adult) 16 else 13,
                runtime = details.runtime,
                genres = details.genres.map { genre -> Genre(genre.id, genre.name) },
                actors = listOf()
            )
        }
    }

    suspend fun getMovieById(id: Long): Movie? {
        val details = httpClient.getMovieDetailsJson(id)
        val config = httpClient.get<JsonImage>("${BASE_URL}configuration")

        return Movie(
            id = details.id.toLong(),
            title = details.title,
            overview = details.overview,
            poster = config.images.base_url + config.images.poster_sizes[5] + details.poster_path,
            backdrop = config.images.base_url + config.images.backdrop_sizes[2] + details.backdrop_path,
            ratings = details.vote_average.toFloat(),
            numberOfRatings = details.vote_count,
            minimumAge = if (details.adult) 16 else 13,
            runtime = details.runtime,
            genres = details.genres.map { genre -> Genre(genre.id, genre.name) },
            actors = details.credits.cast.take(10).map { actor ->
                Actor(
                    id = actor.id,
                    name = actor.name,
                    picture = actor.profile_path?.let { path -> config.images.base_url + config.images.profile_sizes[2] + path }
                )
            }
        )
    }

    private suspend fun HttpClient.getMovieDetailsJson(id: Long) =
        get<MovieDetailsJson>("${BASE_URL}movie/$id?append_to_response=credits")

    companion object {
        private val BASE_URL = "https://api.themoviedb.org/3/"
    }
}
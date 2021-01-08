package com.tatiana.rodionova.androidacademyproject.model

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

private val jsonFormat = Json { ignoreUnknownKeys = true }

@Serializable
private class JsonGenre(val id: Int, val name: String)

@Serializable
private class JsonActor(
    val id: Int,
    val name: String,
    @SerialName("profile_path")
    val profilePicture: String
)

@Serializable
private class JsonMovie(
    val id: Int,
    val title: String,
    @SerialName("poster_path")
    val posterPicture: String,
    @SerialName("backdrop_path")
    val backdropPicture: String,
    val runtime: Int,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    val actors: List<Int>,
    @SerialName("vote_average")
    val ratings: Float,
    @SerialName("vote_count")
    val votesCount: Int,
    val overview: String,
    val adult: Boolean
)

private suspend fun loadGenres(context: Context): List<Genre> = withContext(Dispatchers.IO) {
    parseGenres(readAssetFileToString(context, "genres.json"))
}

internal fun parseGenres(data: String): List<Genre> =
    jsonFormat
        .decodeFromString<List<JsonGenre>>(data)
        .map { jsonGenre -> Genre(id = jsonGenre.id, name = jsonGenre.name) }

private fun readAssetFileToString(context: Context, fileName: String): String =
    context.assets.open(fileName).bufferedReader().readText()

private suspend fun loadActors(context: Context): List<Actor> = withContext(Dispatchers.IO) {
    parseActors(readAssetFileToString(context, "people.json"))
}

internal fun parseActors(data: String): List<Actor> =
    jsonFormat
        .decodeFromString<List<JsonActor>>(data)
        .map { jsonActor ->
            Actor(
                id = jsonActor.id,
                name = jsonActor.name,
                picture = jsonActor.profilePicture
            )
        }

@Suppress("unused")
internal suspend fun loadMovies(context: Context): List<Movie> = withContext(Dispatchers.IO) {
        val genresMap = loadGenres(context)
        val actorsMap = loadActors(context)

        val data = readAssetFileToString(context, "data.json")
        parseMovies(data, genresMap, actorsMap)
    }

internal fun parseMovies(
    data: String,
    genres: List<Genre>,
    actors: List<Actor>
): List<Movie> {
    val genresMap = genres.associateBy { it.id }
    val actorsMap = actors.associateBy { it.id }

    val jsonMovies = jsonFormat.decodeFromString<List<JsonMovie>>(data)

    return jsonMovies.map { jsonMovie ->
        @Suppress("unused")
        Movie(
            id = jsonMovie.id,
            title = jsonMovie.title,
            overview = jsonMovie.overview,
            poster = jsonMovie.posterPicture,
            backdrop = jsonMovie.backdropPicture,
            ratings = jsonMovie.ratings,
            numberOfRatings = jsonMovie.votesCount,
            minimumAge = if (jsonMovie.adult) 16 else 13,
            runtime = jsonMovie.runtime,
            genres = jsonMovie.genreIds.map { id ->
                genresMap[id] ?: throw IllegalArgumentException("Genre not found")
            },
            actors = jsonMovie.actors.map { id ->
                actorsMap[id] ?: throw IllegalArgumentException("Actor not found")
            }
        )
    }
}
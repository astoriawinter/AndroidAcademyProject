package com.tatiana.rodionova.androidacademyproject.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class JsonGenre(val id: Int, val name: String)

@Serializable
data class Image(
    val base_url: String,
    val secure_base_url: String,
    val backdrop_sizes: List<String>,
    val logo_sizes: List<String>,
    val poster_sizes: List<String>,
    val profile_sizes: List<String>,
    val still_sizes: List<String>
)

@Serializable
data class MovieDetailsJson(
    val adult: Boolean,
    val backdrop_path: String,
    val budget: Int,
    val genres: List<JsonGenre>,
    val homepage: String,
    val id: Int,
    val imdb_id: String?,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    val credits: Credits
)

@Serializable
data class Credits(
    val cast: List<Cast>
)

@Serializable
data class Cast(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val known_for_department: String,
    val name: String,
    val original_name: String,
    val popularity: Double,
    val profile_path: String?,
    val cast_id: Int,
    val character: String,
    val credit_id: String,
    val order: Int
)

@Serializable
data class JsonImage(
    val images: Image,
    val change_keys: List<String>
)

@Serializable
data class JsonResponseMovie(
    @SerialName("page") val page: Int,
    @SerialName("results") val results: List<JsonMovie>,
    @SerialName("total_pages") val total_pages: Int,
    @SerialName("total_results") val total_results: Int
)

@Serializable
class JsonMovie(
    val id: Long,
    val title: String,
    @SerialName("poster_path")
    val posterPicture: String,
    @SerialName("vote_average")
    val ratings: Float,
    val adult: Boolean
)
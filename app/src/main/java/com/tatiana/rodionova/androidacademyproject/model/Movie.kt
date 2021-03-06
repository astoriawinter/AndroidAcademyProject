package com.tatiana.rodionova.androidacademyproject.model

data class Movie(
    val id: Long,
    val title: String,
    val overview: String? = null,
    val poster: String?,
    val backdrop: String? = null,
    val ratings: Float,
    val numberOfRatings: Int,
    val minimumAge: Int,
    val runtime: Int,
    val genres: List<Genre>,
    val actors: List<Actor> = listOf()
) {

    companion object {
        fun Float.calculateRating(): Float = this / 2

        fun List<Genre>.split() = joinToString(separator = ", ") { it.name }
    }
}

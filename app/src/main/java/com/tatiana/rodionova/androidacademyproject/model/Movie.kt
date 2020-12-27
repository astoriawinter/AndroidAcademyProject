package com.tatiana.rodionova.androidacademyproject.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
        val id: Long,
        val title: String,
        val overview: String,
        val poster: String,
        val backdrop: String,
        val ratings: Float,
        val numberOfRatings: Int,
        val minimumAge: Int,
        val runtime: Int,
        val genres: List<Genre>,
        val actors: List<Actor>
) : Parcelable {

        companion object {
                fun Float.calculateRating(): Float = this / 2

                fun List<Genre>.split() = joinToString(separator = ", ") { it.name }
        }
}

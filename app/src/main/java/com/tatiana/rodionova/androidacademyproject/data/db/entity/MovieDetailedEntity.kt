package com.tatiana.rodionova.androidacademyproject.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class MovieDetailedEntity(
    @PrimaryKey
    val movieId: Long,
    val title: String,
    val overview: String? = null,
    val backdrop: String? = null,
    val ratings: Float,
    val numberOfRatings: Int,
    val minimumAge: Int,
    val runtime: Int
)
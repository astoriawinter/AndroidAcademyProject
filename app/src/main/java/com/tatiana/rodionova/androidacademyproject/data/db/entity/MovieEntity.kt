package com.tatiana.rodionova.androidacademyproject.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class MovieEntity(
    @PrimaryKey
    val id: Long,
    val title: String,
    val overview: String? = null,
    val poster: String?,
    val ratings: Float,
    val numberOfRatings: Int,
    val minimumAge: Int,
    val runtime: Int
)
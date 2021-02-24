package com.tatiana.rodionova.androidacademyproject.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class GenreEntity(
    @PrimaryKey
    val id: Long,
    val movieId: Long,
    val name: String
)
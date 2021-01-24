package com.tatiana.rodionova.androidacademyproject.data.db.entity

import androidx.room.Embedded
import androidx.room.Relation

class MovieWithGenre(
    @Embedded
    val movie: MovieEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "movieId"
    )
    val genres: List<GenreEntity>
)
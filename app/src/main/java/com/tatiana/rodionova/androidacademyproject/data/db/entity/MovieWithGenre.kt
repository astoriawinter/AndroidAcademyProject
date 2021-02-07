package com.tatiana.rodionova.androidacademyproject.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

data class MovieWithGenre(
    @Embedded
    val movie: MovieEntity,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "genreId",
        associateBy = Junction(MovieWithGenreCrossRef::class)
    )
    val genres: List<GenreEntity>
)

@Entity(primaryKeys = ["movieId", "genreId"])
data class MovieWithGenreCrossRef(
    val movieId: Long,
    val genreId: Long
)
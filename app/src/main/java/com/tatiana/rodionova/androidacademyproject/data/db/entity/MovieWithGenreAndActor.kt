package com.tatiana.rodionova.androidacademyproject.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

data class MovieWithGenreAndActor(
    @Embedded
    val movie: MovieDetailedEntity,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "genreId",
        associateBy = Junction(MovieWithGenreCrossRef::class)
    )
    val genres: List<GenreEntity>,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "movieId"
    )
    val actors: List<ActorEntity>
)
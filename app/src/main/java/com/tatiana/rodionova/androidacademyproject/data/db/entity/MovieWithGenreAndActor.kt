package com.tatiana.rodionova.androidacademyproject.data.db.entity

import androidx.room.Embedded
import androidx.room.Relation

class MovieWithGenreAndActor(
    @Embedded
    val movie: MovieDetailedEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "movieId"
    )
    val genres: List<GenreEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "movieId"
    )
    val actors: List<ActorEntity>
)
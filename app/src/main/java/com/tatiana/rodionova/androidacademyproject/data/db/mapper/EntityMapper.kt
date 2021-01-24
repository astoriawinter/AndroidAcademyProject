package com.tatiana.rodionova.androidacademyproject.data.db.mapper

import com.tatiana.rodionova.androidacademyproject.data.db.entity.*
import com.tatiana.rodionova.androidacademyproject.model.Actor
import com.tatiana.rodionova.androidacademyproject.model.Genre
import com.tatiana.rodionova.androidacademyproject.model.Movie

fun List<Movie>.mapMovieToMovieEntity(): List<MovieEntity> =
    map { movie -> movie.mapToMovieEntity() }

fun Movie.mapToMovieEntity() =
    MovieEntity(
        id, title, overview, poster, ratings, numberOfRatings, minimumAge, runtime
    )

fun Movie.mapToMovieDetailedEntity() =
    MovieDetailedEntity(
        id, title, overview, backdrop, ratings, numberOfRatings, minimumAge, runtime
    )

fun List<Movie>.mapMovieToGenreEntity(): List<GenreEntity> =
    flatMap { movie ->
        movie.genres.map { genre -> genre.toEntity(movie.id) }
    }

fun Movie.mapMovieToActorEntity(): List<ActorEntity> =
    actors.map { actor -> actor.toEntity(id) }

fun Genre.toEntity(movieId: Long): GenreEntity =
    GenreEntity(id, movieId, name)

fun GenreEntity.toGenre(): Genre =
    Genre(id, name)

fun Actor.toEntity(movieId: Long): ActorEntity =
    ActorEntity(id, movieId, name, picture)

fun ActorEntity.toActor(): Actor =
    Actor(id, name, picture)

fun List<MovieWithGenre>.toMovie(): List<Movie> =
    map { movieWithGenre -> movieWithGenre.toMovie() }

fun MovieWithGenre.toMovie() =
    Movie(
        movie.id,
        movie.title,
        movie.overview,
        movie.poster,
        null,
        movie.ratings,
        movie.numberOfRatings,
        movie.minimumAge,
        movie.runtime,
        genres.map { genreEntity -> genreEntity.toGenre() }
    )

fun MovieWithGenreAndActor.toMovie() =
    Movie(
        movie.id,
        movie.title,
        movie.overview,
        null,
        movie.backdrop,
        movie.ratings,
        movie.numberOfRatings,
        movie.minimumAge,
        movie.runtime,
        genres.map { genreEntity -> genreEntity.toGenre() },
        actors.map { actorEntity -> actorEntity.toActor() }
    )

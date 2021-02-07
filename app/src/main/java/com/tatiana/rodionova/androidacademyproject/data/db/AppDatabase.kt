package com.tatiana.rodionova.androidacademyproject.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tatiana.rodionova.androidacademyproject.data.db.dao.ActorDao
import com.tatiana.rodionova.androidacademyproject.data.db.dao.GenreDao
import com.tatiana.rodionova.androidacademyproject.data.db.dao.MovieDao
import com.tatiana.rodionova.androidacademyproject.data.db.dao.MovieDetailedDao
import com.tatiana.rodionova.androidacademyproject.data.db.entity.*

@Database(
    entities = [MovieEntity::class, MovieDetailedEntity::class, GenreEntity::class, ActorEntity::class, MovieWithGenreCrossRef::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun genreDao(): GenreDao

    abstract fun actorDao(): ActorDao

    abstract fun movieDetailedDao(): MovieDetailedDao
}
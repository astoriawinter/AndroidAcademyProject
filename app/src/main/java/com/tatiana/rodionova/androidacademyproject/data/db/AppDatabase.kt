package com.tatiana.rodionova.androidacademyproject.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tatiana.rodionova.androidacademyproject.data.db.dao.ActorDao
import com.tatiana.rodionova.androidacademyproject.data.db.dao.GenreDao
import com.tatiana.rodionova.androidacademyproject.data.db.dao.MovieDao
import com.tatiana.rodionova.androidacademyproject.data.db.dao.MovieDetailedDao
import com.tatiana.rodionova.androidacademyproject.data.db.entity.ActorEntity
import com.tatiana.rodionova.androidacademyproject.data.db.entity.GenreEntity
import com.tatiana.rodionova.androidacademyproject.data.db.entity.MovieDetailedEntity
import com.tatiana.rodionova.androidacademyproject.data.db.entity.MovieEntity

@Database(
    entities = [MovieEntity::class, MovieDetailedEntity::class, GenreEntity::class, ActorEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun genreDao(): GenreDao

    abstract fun actorDao(): ActorDao

    abstract fun movieDetailedDao(): MovieDetailedDao
}
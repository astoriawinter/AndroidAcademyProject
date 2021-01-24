package com.tatiana.rodionova.androidacademyproject.data.db.dao

import androidx.room.*
import com.tatiana.rodionova.androidacademyproject.data.db.entity.MovieDetailedEntity
import com.tatiana.rodionova.androidacademyproject.data.db.entity.MovieWithGenreAndActor

@Dao
interface MovieDetailedDao {

    @Insert
    suspend fun insertMovieDetails(movie: MovieDetailedEntity)

    @Update
    suspend fun updateMovieDetails(movie: MovieDetailedEntity)

    @Query("SELECT COUNT(*) from moviedetailedentity WHERE id = :id AND backdrop IS NOT NULL")
    suspend fun getNumberOfRecords(id: Long): Long

    @Transaction
    @Query("SELECT * from moviedetailedentity WHERE id = :id")
    suspend fun getMovieDetailed(id: Long): MovieWithGenreAndActor
}
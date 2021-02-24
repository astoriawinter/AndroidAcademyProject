package com.tatiana.rodionova.androidacademyproject.data.db.dao

import androidx.room.*
import com.tatiana.rodionova.androidacademyproject.data.db.entity.MovieEntity
import com.tatiana.rodionova.androidacademyproject.data.db.entity.MovieWithGenre

@Dao
interface MovieDao {
    @Insert
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Update
    suspend fun updateMovies(movies: List<MovieEntity>)

    @Query("SELECT COUNT(*) from movieentity")
    suspend fun getNumberOfRecords(): Int

    @Transaction
    @Query("SELECT * from movieentity ORDER BY ratings DESC, title ASC")
    suspend fun getMovies(): List<MovieWithGenre>
}
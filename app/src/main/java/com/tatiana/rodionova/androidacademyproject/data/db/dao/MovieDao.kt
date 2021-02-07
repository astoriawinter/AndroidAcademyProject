package com.tatiana.rodionova.androidacademyproject.data.db.dao

import androidx.room.*
import com.tatiana.rodionova.androidacademyproject.data.db.entity.MovieEntity
import com.tatiana.rodionova.androidacademyproject.data.db.entity.MovieWithGenre
import com.tatiana.rodionova.androidacademyproject.data.db.entity.MovieWithGenreCrossRef
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoviesWithGenres(moviesWithGenres: List<MovieWithGenreCrossRef>)

    @Query("SELECT COUNT(*) from movieentity")
    suspend fun getNumberOfRecords(): Int

    @Transaction
    @Query("SELECT * from movieentity ORDER BY ratings DESC, title ASC")
    fun getMoviesWithGenre(): Flow<List<MovieWithGenre>>

    fun getMoviesUntilChanged() = getMoviesWithGenre().distinctUntilChanged()
}
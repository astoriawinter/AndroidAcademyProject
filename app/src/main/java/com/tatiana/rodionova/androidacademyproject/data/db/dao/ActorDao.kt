package com.tatiana.rodionova.androidacademyproject.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.tatiana.rodionova.androidacademyproject.data.db.entity.ActorEntity

@Dao
interface ActorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActors(genres: List<ActorEntity>)
}
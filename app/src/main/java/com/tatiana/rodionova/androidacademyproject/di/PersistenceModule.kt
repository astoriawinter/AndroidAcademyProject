package com.tatiana.rodionova.androidacademyproject.di

import android.content.Context
import androidx.room.Room
import com.tatiana.rodionova.androidacademyproject.data.db.AppDatabase
import org.koin.dsl.module

val persistenceModule = module {
    single { getRoom(get()) }
    single { get<AppDatabase>().actorDao() }
    single { get<AppDatabase>().movieDao() }
    single { get<AppDatabase>().genreDao() }
    single { get<AppDatabase>().movieDetailedDao() }
}

private fun getRoom(context: Context) =
    Room.databaseBuilder(context, AppDatabase::class.java, "database")
        .fallbackToDestructiveMigration()
        .build()
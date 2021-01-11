package com.tatiana.rodionova.androidacademyproject.di

import com.tatiana.rodionova.androidacademyproject.data.MovieRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { MovieRepository(get()) }
}
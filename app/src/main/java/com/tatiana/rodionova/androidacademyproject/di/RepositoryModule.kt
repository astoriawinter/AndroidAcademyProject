package com.tatiana.rodionova.androidacademyproject.di

import com.tatiana.rodionova.androidacademyproject.data.repository.MovieRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { MovieRepository(get(), get(), get(), get(), get(), get()) }
}
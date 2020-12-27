package com.tatiana.rodionova.androidacademyproject.di

import com.tatiana.rodionova.androidacademyproject.ui.movie_details.MovieDetailsViewModel
import com.tatiana.rodionova.androidacademyproject.ui.movies_list.MovieListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MovieListViewModel(get()) }
    viewModel { MovieDetailsViewModel(get()) }
}
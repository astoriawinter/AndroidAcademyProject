package com.tatiana.rodionova.androidacademyproject.data.service

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.tatiana.rodionova.androidacademyproject.data.repository.MovieRepository
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
class MoviesLoaderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params), KoinComponent {

    override suspend fun doWork(): Result {
        val movieRepository: MovieRepository by inject()

        return try {
            movieRepository.lazyDBUpdate()

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}

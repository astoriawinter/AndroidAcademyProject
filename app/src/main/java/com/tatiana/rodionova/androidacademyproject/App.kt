package com.tatiana.rodionova.androidacademyproject

import android.app.Application
import com.tatiana.rodionova.androidacademyproject.di.networkModule
import com.tatiana.rodionova.androidacademyproject.di.persistenceModule
import com.tatiana.rodionova.androidacademyproject.di.repositoryModule
import com.tatiana.rodionova.androidacademyproject.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(viewModelModule, repositoryModule, networkModule, persistenceModule)
        }
    }
}
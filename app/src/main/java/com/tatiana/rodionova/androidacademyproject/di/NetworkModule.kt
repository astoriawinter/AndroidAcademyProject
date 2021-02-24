package com.tatiana.rodionova.androidacademyproject.di

import com.tatiana.rodionova.androidacademyproject.BuildConfig
import com.tatiana.rodionova.androidacademyproject.data.network.MovieApi
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import okhttp3.Interceptor
import okhttp3.Response
import org.koin.dsl.module

val networkModule = module {
    single { httpClient }
    single { MovieApi(get()) }
}

private val httpClient: HttpClient =
    HttpClient(OkHttp) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
                isLenient = true
            })
            expectSuccess = true
        }
        engine { addInterceptor(ApiHeaderInterceptor()) }
    }

class ApiHeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .build()
        val request = chain.request().newBuilder().url(url).build()
        return chain.proceed(request)
    }
}
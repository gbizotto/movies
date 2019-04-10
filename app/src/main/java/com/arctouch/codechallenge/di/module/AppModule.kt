package com.arctouch.codechallenge.di.module

import com.arctouch.codechallenge.BuildConfig
import com.arctouch.codechallenge.MoviesApplication
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule(private val application: MoviesApplication) {

    @Provides
    @Singleton
    fun provideApi(): TmdbApi {
        val clientBuilder = OkHttpClient.Builder()
        if (BuildConfig.BUILD_TYPE == "debug") {
            val loggingInterceptor = HttpLoggingInterceptor()
            // set your desired log level
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            // add logging as last interceptor
            clientBuilder.addInterceptor(loggingInterceptor)
        }

        return Retrofit.Builder()
                .baseUrl(application.getString(R.string.tmdb_base_url))
                .client(clientBuilder.build())
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(TmdbApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieImageUrlBuilder(@Named("apikey") apiKey: String): MovieImageUrlBuilder {
        return MovieImageUrlBuilder(apiKey)
    }

    @Provides
    @Singleton
    @Named("apikey")
    fun provideApiKey(): String {
        return application.getString(R.string.tmdb_api_key)
    }
}
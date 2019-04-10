package com.arctouch.codechallenge.di.module

import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
object AppModule {

//    @JvmStatic
//    @Provides
//    @Singleton
//    fun provideApi(application: MoviesApplication): TmdbApi {
//        return Retrofit.Builder()
//                .baseUrl(application.getString(R.string.tmdb_base_url))
//                .client(OkHttpClient.Builder().build())
//                .addConverterFactory(MoshiConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build()
//                .create(TmdbApi::class.java)
//    }


    @JvmStatic
    @Provides
    @Singleton
    fun provideApi(): TmdbApi {
        val clientBuilder = OkHttpClient.Builder()

        val loggingInterceptor = HttpLoggingInterceptor()

        // set your desired log level
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        // add logging as last interceptor
        clientBuilder.addInterceptor(loggingInterceptor)


        return Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .client(clientBuilder.build())
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(TmdbApi::class.java)
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideMovieImageUrlBuilder(): MovieImageUrlBuilder {
        return MovieImageUrlBuilder()
    }
}
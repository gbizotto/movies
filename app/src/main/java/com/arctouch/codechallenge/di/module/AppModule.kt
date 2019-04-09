package com.arctouch.codechallenge.di.module

import com.arctouch.codechallenge.api.TmdbApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
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
        return Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .client(OkHttpClient.Builder().build())
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(TmdbApi::class.java)
    }
}
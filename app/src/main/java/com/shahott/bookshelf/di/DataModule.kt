package com.shahott.bookshelf.di

import com.shahott.bookshelf.data.remote.RemoteData
import com.shahott.bookshelf.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun getOkHttpClient(
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.apply {
            retryOnConnectionFailure(true)
            readTimeout(1, TimeUnit.MINUTES)
            connectTimeout(1, TimeUnit.MINUTES)
            writeTimeout(5, TimeUnit.MINUTES)
            addInterceptor(logging)
        }.build()

        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun getMoshi(): MoshiConverterFactory {
        return MoshiConverterFactory.create()
    }

    @Provides
    @Singleton
    fun getRetrofit(okHttpClient: OkHttpClient, moshi: MoshiConverterFactory): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(moshi)
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(
        retrofit: Retrofit,
    ): RemoteData {
        val api = retrofit.create(RemoteData::class.java)
        return api
    }

}
package com.app.productapp.di

import com.app.productapp.BuildConfig
import com.app.productapp.network.Utils
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory {
        val gson = Gson()
        gson.serializeNulls()
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(logging)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, converterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(Utils.BASE_URL).addConverterFactory(converterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(client)
        }.build()
    }

}
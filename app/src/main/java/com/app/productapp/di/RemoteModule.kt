package com.app.productapp.di

import com.app.productapp.network.api.ProductInterface
import com.app.productapp.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Singleton
    @Provides
    fun provideWeatherApi(retrofit: Retrofit): ProductInterface {
        return retrofit.create(ProductInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideProductRepository(api: ProductInterface): ProductRepository {
        return ProductRepository(api)
    }
}
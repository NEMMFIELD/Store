package com.example.store.di

import android.content.SharedPreferences
import com.example.basket.BasketRepository
import com.example.basket.BasketRepositoryImpl
import com.example.basket.network.BasketApi
import com.example.productdetails.data.ProductDetailsRepository
import com.example.productdetails.data.ProductDetailsRepositoryImpl
import com.example.productdetails.data.network.ProductDetailsApi
import com.example.store.model.Repository
import com.example.store.model.RepositoryImpl
import com.example.mainscreen.data.network.PhonesApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://run.mocky.io/v3/"

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi): PhonesApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
        .build()
        .create(PhonesApi::class.java)

    @Provides
    @Singleton
    fun provideDetailsRetrofit(moshi: Moshi): ProductDetailsApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
        .build()
        .create(ProductDetailsApi::class.java)

    @Provides
    @Singleton
    fun provideRepository(phonesApi: PhonesApi, sharedPreferences: SharedPreferences): Repository =
        RepositoryImpl(phonesApi, sharedPreferences)

    @Provides
    @Singleton
    fun provideProductDetailsRepository(phonesDetailsApi: ProductDetailsApi): ProductDetailsRepository =
        ProductDetailsRepositoryImpl(phonesDetailsApi)

    @Provides
    @Singleton
    fun provideBasketRetrofit(moshi: Moshi): BasketApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
        .build()
        .create(BasketApi::class.java)

    @Provides
    @Singleton
    fun provideBasketRepository(basketApi: BasketApi): BasketRepository =
        BasketRepositoryImpl(basketApi)

}
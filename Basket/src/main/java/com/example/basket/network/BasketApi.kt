package com.example.basket.network

import com.example.store.model.BasketResponse
import retrofit2.http.GET

interface BasketApi {
    @GET("53539a72-3c5f-4f30-bbb1-6ca10d42c149")
    suspend fun getBasket(): BasketResponse
}
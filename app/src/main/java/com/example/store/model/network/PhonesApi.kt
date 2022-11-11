package com.example.store.model.network

import com.example.store.model.Response
import com.example.store.model.ResponseDetails
import retrofit2.http.GET

interface PhonesApi {
    @GET("654bd15e-b121-49ba-a588-960956b15175")
    suspend fun getPhones(): Response

    @GET("6c14c560-15c6-4248-b9d2-b4508df7d4f5")
    suspend fun getProductDetails(): ResponseDetails

}
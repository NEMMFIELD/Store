package com.example.mainscreen.data.network

import com.example.mainscreen.data.Response
import retrofit2.http.GET

interface PhonesApi {
    @GET("654bd15e-b121-49ba-a588-960956b15175")
    suspend fun getPhones(): Response

}
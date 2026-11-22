package com.example.currencyconverterapp.data.remote

import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyApiService {
    @GET("currencies.json")
    suspend fun getCurrencies(): Map<String, String>

    @GET("currencies/{currency}.json")
    suspend fun getRates(
        @Path("currency") currency: String
    ): JsonObject
}
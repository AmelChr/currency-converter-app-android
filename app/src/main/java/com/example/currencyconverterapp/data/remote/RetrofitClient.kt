package com.example.currencyconverterapp.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL =
        "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/"

    val currencyService: CurrencyApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CurrencyApiService::class.java)
}
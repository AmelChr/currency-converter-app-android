package com.example.currencyconverterapp.data

import com.example.currencyconverterapp.data.remote.CurrencyApiService
import java.math.BigDecimal

class CurrencyRepository(
    private val service: CurrencyApiService
) {
    suspend fun getCurrencies(): Map<String, String> {
        return service.getCurrencies()
    }
    suspend fun getRates(baseCurrency: String): Map<String, BigDecimal> {
        return service.getRates(baseCurrency)
            .getAsJsonObject(baseCurrency)
            .entrySet()
            .associate { (code, rate) -> code to rate.asBigDecimal }
    }
}
package com.example.currencyconverterapp.presentation

import java.math.BigDecimal

data class CurrencyUiState(
    val currencies: Map<String, String> = emptyMap(),
    val fromCurrency: String = "eur",
    val toCurrency: String = "cad",
    val amount: String = "",
    val result: BigDecimal? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
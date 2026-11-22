package com.example.currencyconverterapp.di

import com.example.currencyconverterapp.data.CurrencyRepository
import com.example.currencyconverterapp.data.remote.RetrofitClient
import com.example.currencyconverterapp.presentation.CurrencyViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single { RetrofitClient.currencyService }
    single { CurrencyRepository(get()) }
    viewModelOf(::CurrencyViewModel)

}
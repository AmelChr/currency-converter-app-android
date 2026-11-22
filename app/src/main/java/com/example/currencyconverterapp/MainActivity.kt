package com.example.currencyconverterapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.currencyconverterapp.presentation.CurrencyScreen
import com.example.currencyconverterapp.presentation.CurrencyViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val currencyViewModel: CurrencyViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrencyScreen(viewModel = currencyViewModel)
        }
    }
}

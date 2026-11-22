package com.example.currencyconverterapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.currencyconverterapp.data.CurrencyRepository
import com.example.currencyconverterapp.data.remote.RetrofitClient
import com.example.currencyconverterapp.ui.theme.CurrencyConverterAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO just to test the service , to be modified
        lifecycleScope.launch {
            val repository = CurrencyRepository(
                RetrofitClient.currencyService
            )

            try {
                val currencies = repository.getCurrencies()

                Log.d("test curriences", "All currencies: $currencies")

                val rates = repository.getRates("eur")
                Log.d("test get rates", "eur to cad: ${rates["cad"]}")

            } catch (e: Exception) {
                Log.e("test failed", "Failed to fetch currencies", e)
            }
        }
        enableEdgeToEdge()
        setContent {}
    }
}

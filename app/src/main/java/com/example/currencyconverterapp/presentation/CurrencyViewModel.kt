package com.example.currencyconverterapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.data.CurrencyRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import kotlin.coroutines.cancellation.CancellationException

class CurrencyViewModel(private val repository: CurrencyRepository) : ViewModel() {

    private var rates: Map<String, BigDecimal> = emptyMap()
    private var ratesJob: Job? = null

    private val _uiState = MutableStateFlow(CurrencyUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadCurrencies()
        loadRates(_uiState.value.fromCurrency)
    }

    private fun loadCurrencies() {
        viewModelScope.launch {
            try {
                val currencies = repository.getCurrencies()

                _uiState.update {
                    it.copy(currencies = currencies)
                }

            } catch (e: CancellationException) {
                throw e
            } catch (_: Exception) {
                _uiState.update {
                    it.copy(error = "Failed to load currencies")
                }
            }
        }
    }

    private fun loadRates(currency: String) {
        ratesJob?.cancel()

        _uiState.update { it.copy(isLoading = true, error = null) }

        ratesJob = viewModelScope.launch {
            try {
                val newRates = repository.getRates(currency)

                if (_uiState.value.fromCurrency == currency) {
                    rates = newRates
                    _uiState.update { it.copy(isLoading = false) }
                    convert()
                }
            } catch (e: CancellationException) {
                throw e
            } catch (_: Exception) {
                if (_uiState.value.fromCurrency == currency) {
                    _uiState.update {
                        it.copy(isLoading = false, result = null, error = "Failed to load rates")
                    }
                }
            }
        }
    }

    fun onAmountChanged(amount: String) {
        _uiState.update {
            it.copy(amount = amount)
        }
        convert()
    }

    fun onToCurrencySelected(currency: String) {
        _uiState.update {
            it.copy(toCurrency = currency)
        }
        convert()
    }

    private fun convert() {
        val state = _uiState.value
        val amount = state.amount.trim().replace(',', '.').toBigDecimalOrNull()
        val rate = rates[state.toCurrency]

        val result = if (amount != null && rate != null) {
            amount.multiply(rate)
        } else {
            null
        }

        _uiState.update {
            it.copy(result = result)
        }
    }

    fun onFromCurrencySelected(currency: String) {
        if (_uiState.value.fromCurrency == currency) return
        _uiState.update {
            it.copy(
                fromCurrency = currency,
                result = null,
                error = null
            )
        }

        rates = emptyMap()
        loadRates(currency)
    }

    fun swapCurrencies() {
        val state = _uiState.value

        _uiState.update {
            it.copy(
                fromCurrency = state.toCurrency,
                toCurrency = state.fromCurrency,
                result = null,
                error = null
            )
        }

        rates = emptyMap()
        loadRates(state.toCurrency)
    }

    fun retry() {
        if (_uiState.value.currencies.isEmpty()) {
            loadCurrencies()
        }

        rates = emptyMap()
        loadRates(_uiState.value.fromCurrency)
    }
}
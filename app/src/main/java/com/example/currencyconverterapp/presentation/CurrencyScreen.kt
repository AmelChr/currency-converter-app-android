package com.example.currencyconverterapp.presentation

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.presentation.component.CurrencyRow

@Composable
fun CurrencyScreen(
    viewModel: CurrencyViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    // Sorted once when the list arrives, not on every keystroke
    val currenciesList = remember(state.currencies) { state.currencies.keys.sorted() }

    val focusManager = LocalFocusManager.current
    Box(
        modifier = modifier.fillMaxSize()
            .pointerInput(Unit) { detectTapGestures { focusManager.clearFocus() } }
            .imePadding(),
        contentAlignment = Alignment.Center
    ) {
        CurrencyConverterContent(
            currenciesList = currenciesList,
            fromCurrency = state.fromCurrency,
            toCurrency = state.toCurrency,
            amount = state.amount,
            result = state.result?.let { formatAmount(it, state.toCurrency) }.orEmpty(),
            isLoading = state.isLoading,
            error = state.error,
            onFromCurrencySelected = viewModel::onFromCurrencySelected,
            onToCurrencySelected = viewModel::onToCurrencySelected,
            onAmountChange = viewModel::onAmountChanged,
            onSwap = viewModel::swapCurrencies,
            onRetry = viewModel::retry,
        )
    }
}

@Composable
fun CurrencyConverterContent(
    currenciesList: List<String>,
    fromCurrency: String,
    toCurrency: String,
    amount: String,
    result: String,
    isLoading: Boolean,
    error: String?,
    onFromCurrencySelected: (String) -> Unit,
    onToCurrencySelected: (String) -> Unit,
    onAmountChange: (String) -> Unit,
    onSwap: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(30.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Text(
            text = stringResource(R.string.currency_converter_title),
            style = MaterialTheme.typography.headlineSmall,
        )

        CurrencyRow(
            options = currenciesList,
            selectedCurrency = fromCurrency,
            onCurrencySelected = onFromCurrencySelected,
            amount = amount,
            label = stringResource(R.string.amount),
            onAmountChange = onAmountChange,
        )

        OutlinedIconButton(
            modifier = Modifier.align(Alignment.Start),
            onClick = onSwap,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_swipe),
                contentDescription = "swipe",
            )
        }

        CurrencyRow(
            options = currenciesList,
            selectedCurrency = toCurrency,
            onCurrencySelected = onToCurrencySelected,
            amount = result,
            label = stringResource(R.string.converted_amount),
            readOnly = true,
            isLoading = isLoading,
        )

        error?.let {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                )
                Button(onClick = onRetry) {
                    Text(stringResource(R.string.retry))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CurrencyConverterContentPreview() {
    CurrencyConverterContent(
        currenciesList = listOf("eur", "cad"),
        fromCurrency = "eur",
        toCurrency = "cad",
        amount = "10",
        result = "10.83",
        isLoading = false,
        error = null,
        onFromCurrencySelected = {},
        onToCurrencySelected = {},
        onAmountChange = {},
        onSwap = {},
        onRetry = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun CurrencyConverterContentErrorPreview() {
    CurrencyConverterContent(
        currenciesList = listOf("eur", "cad"),
        fromCurrency = "eur",
        toCurrency = "cad",
        amount = "",
        result = "",
        isLoading = false,
        error = "Failed to load rates",
        onFromCurrencySelected = {},
        onToCurrencySelected = {},
        onAmountChange = {},
        onSwap = {},
        onRetry = {},
    )
}
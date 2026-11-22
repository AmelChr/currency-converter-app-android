package com.example.currencyconverterapp.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CurrencyRow(
    options: List<String>,
    selectedCurrency: String,
    onCurrencySelected: (String) -> Unit,
    amount: String,
    label: String,
    modifier: Modifier = Modifier,
    onAmountChange: (String) -> Unit = {},
    readOnly: Boolean = false,
    isLoading: Boolean = false,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CurrencyDropdownMenu(
            options = options,
            selectedCurrency = selectedCurrency,
            onOptionSelected = onCurrencySelected,
        )

        OutlinedTextField(
            value = amount,
            onValueChange = onAmountChange,
            readOnly = readOnly,
            singleLine = true,
            label = { Text(label) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            trailingIcon = if (isLoading) {
                { CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp) }
            } else {
                null
            },
            modifier = Modifier
                .weight(1f)
                .focusProperties { canFocus = !readOnly },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CurrencyRowPreview() {
    CurrencyRow(
        options = listOf("eur", "cad"),
        selectedCurrency = "eur",
        onCurrencySelected = {},
        label = "label",
        amount = "10",
        onAmountChange = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun CurrencyRowLoadingPreview() {
    CurrencyRow(
        options = listOf("eur", "cad"),
        selectedCurrency = "cad",
        onCurrencySelected = {},
        amount = "",
        label = "label",
        readOnly = true,
        isLoading = true,
    )
}
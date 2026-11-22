package com.example.currencyconverterapp.presentation

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

// Enough for crypto, Bitcoin goes down to 8 decimals
private const val MAX_FRACTION_DIGITS = 8

/** Turns the converted amount into text for the screen **/
fun formatAmount(
    value: BigDecimal,
    currencyCode: String,
    locale: Locale = Locale.getDefault(),
): String {
    // For a real currency we use its usual decimals.
    // Java doesn't know crypto or gold, so those get a plain number.
    val format = runCatching {
        val currency = Currency.getInstance(currencyCode.uppercase())
        // Gold and silver have no usual decimals, so they go the crypto way
        check(currency.defaultFractionDigits >= 0)

        NumberFormat.getNumberInstance(locale).apply {
            // Java keeps 2 decimals even for yen, so we set them ourselves
            minimumFractionDigits = currency.defaultFractionDigits
            maximumFractionDigits = currency.defaultFractionDigits
        }
    }.getOrElse {
        NumberFormat.getNumberInstance(locale).apply {
            maximumFractionDigits = MAX_FRACTION_DIGITS
        }
    }

    // A tiny amount would show as 0,00, so we give it more decimals
    val roundsToZero = value.signum() != 0 &&
            value.setScale(format.maximumFractionDigits, RoundingMode.HALF_EVEN).signum() == 0
    if (roundsToZero) {
        format.maximumFractionDigits = MAX_FRACTION_DIGITS
    }

    return format.format(value)
}
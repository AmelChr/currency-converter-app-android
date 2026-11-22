package com.example.currencyconverterapp.presentation

import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal
import java.util.Locale

class AmountFormatterTest {


    @Test
    fun `formats with the currency's decimals`() {
        assertEquals("10.83", formatAmount(BigDecimal("10.8323456"), "usd", Locale.US))
    }

    @Test
    fun `uses zero decimals for yen`() {
        assertEquals("1,630", formatAmount(BigDecimal("1630.42"), "jpy", Locale.US))
    }

    @Test
    fun `falls back to eight decimals for unknown codes`() {
        assertEquals("0.00001123", formatAmount(BigDecimal("0.0000112345"), "btc", Locale.US))
    }
    @Test
    fun `shows more decimals when rounding would hide a small amount`() {
        assertEquals("0.00005801", formatAmount(BigDecimal("0.0000580123"), "eur", Locale.US))
    }

    @Test
    fun `keeps the currency decimals for zero`() {
        assertEquals("0.00", formatAmount(BigDecimal.ZERO, "eur", Locale.US))
    }

    @Test
    fun `follows German number format`() {
        assertEquals("1.234,56", formatAmount(BigDecimal("1234.56"), "eur", Locale.GERMANY))
    }

}
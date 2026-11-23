# Currency Converter

Small Android app converting currencies, type an amount
and it shows the converted value. Rates come from
https://github.com/fawazahmed0/exchange-api

## Stack

Kotlin, Jetpack Compose, ViewModel with StateFlow, coroutines, MVVM, Retrofit with Gson, and Koin for DI.

The code is split into `data` (API + repository) and `presentation` (screen, ViewModel, formatting).
I didn't add a domain layer, it felt too much for this simple case.

## Decisions

`BigDecimal` used for all calculations, so there are no rounding surprises like with `Double`.
The rates are read directly as `BigDecimal` from the JSON.

The ViewModel keeps the exact result and I only round it when displaying it.
Each currency gets its own number of decimals (2 for EUR, 0 for JPY), and the format follows
the phone language, so you get "1.234,56" in German and "1,234.56" in English.

Crypto codes like BTC are not known by Java's `getNumberInstance()`, so they just show as a number with up to 8 decimals.
Very small amounts also get more decimals, otherwise they would show `0,00`.

If you switch the source currency quickly, the previous request gets cancelled, so an old response
can't overwrite the new rates.

## Tests

There are unit tests for the formatting (`AmountFormatterTest`): decimals per currency,
crypto, very small amounts, zero, German format.

## Kept the task at around 3 hours, If I had more time

- Show currency names in the list and add a search
- Write repository tests with a fake service, and ViewModel tests
  

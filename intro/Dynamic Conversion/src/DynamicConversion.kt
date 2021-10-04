val currencies = mapOf(
    "RON" to "EUR" to 0.2,
    "RON" to "USD" to 0.25,
    "RON" to "GBP" to 0.17,
    "EUR" to "RON" to 5.0,
    "EUR" to "USD" to 1.21,
    "EUR" to "GBP" to 0.86,
    "USD" to "RON" to 4.0,
    "USD" to "EUR" to 0.83,
    "USD" to "GBP" to 0.71,
    "GBP" to "RON" to 5.88,
    "GBP" to "EUR" to 1.16,
    "GBP" to "USD" to 1.41
)

fun convert(fromCurrency: String, toCurrency: String, amount: Double): Double {
    val rate = if (fromCurrency == toCurrency) 1.0 else currencies[fromCurrency to toCurrency]
    requireNotNull(rate) { "Unknown conversion rate" }

    return amount * rate
}
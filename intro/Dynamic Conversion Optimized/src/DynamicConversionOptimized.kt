val currencies = mapOf(
    "RON" to "EUR" to 0.2,
    "RON" to "USD" to 0.25,
    "RON" to "GBP" to 0.17,
    "EUR" to "USD" to 1.21,
    "EUR" to "GBP" to 0.86,
    "USD" to "GBP" to 0.71
)

fun convert(fromCurrency: String, toCurrency: String, amount: Double): Double {
    val directRate = if (fromCurrency == toCurrency) 1.0 else currencies[fromCurrency to toCurrency]
    val inverseRate = currencies[toCurrency to fromCurrency]
    val rate = directRate ?: if (inverseRate != null) 1 / inverseRate else null
    requireNotNull(rate) { "Unknown conversion rate" }

    return amount * rate
}

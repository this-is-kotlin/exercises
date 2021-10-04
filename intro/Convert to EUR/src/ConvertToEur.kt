fun convertToEur(currency: String, amount: Double): Double = when (currency) {
    "RON" -> amount * 0.2
    "USD" -> amount * 0.8
    "GBP" -> amount * 1.2
    "EUR" -> amount
    else -> throw IllegalArgumentException("Unrecognized currency!")
}

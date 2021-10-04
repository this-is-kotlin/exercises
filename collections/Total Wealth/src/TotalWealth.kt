import java.math.BigDecimal

data class Account(
    val iban: String,
    val product: String,
    val currency: String,
    val balance: BigDecimal
)

fun List<Account>.totalWealth(defaultCurrency: String): BigDecimal =
    this.map { convert(it.currency, defaultCurrency, it.balance) }
        .sum()

fun List<BigDecimal>.sum(): BigDecimal = this.fold(BigDecimal.ZERO) { sum, x -> sum + x }

val currencies = mapOf(
    "RON" to "EUR" to "0.2".toBigDecimal(),
    "RON" to "USD" to "0.25".toBigDecimal(),
    "RON" to "GBP" to "0.17".toBigDecimal(),
    "EUR" to "USD" to "1.21".toBigDecimal(),
    "EUR" to "GBP" to "0.86".toBigDecimal(),
    "USD" to "GBP" to "0.71".toBigDecimal()
)

fun convert(fromCurrency: String, toCurrency: String, amount: BigDecimal): BigDecimal = when {
    fromCurrency == toCurrency -> amount
    fromCurrency to toCurrency in currencies -> amount * currencies[fromCurrency to toCurrency]!!
    toCurrency to fromCurrency in currencies -> amount / currencies[toCurrency to fromCurrency]!!
    else -> throw IllegalArgumentException("Unknown conversion rate")
}

/**
 * Example usage
 *
 * Should print 576015.25
 */
fun main() {
    val savings = Account("AM45ONXX1234567812345678", "Savings", "USD", "12500".toBigDecimal())
    val current = Account("RE45ONXX1234567812345673", "Current", "EUR", "105203.05".toBigDecimal())

    val accounts = listOf(current, savings)

    println(accounts.totalWealth("RON"))
}
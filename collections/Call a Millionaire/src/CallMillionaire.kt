import java.math.BigDecimal

data class Client(val firstName: String, val lastName: String)
data class Account(
    val owner: Client,
    val iban: String,
    val product: String,
    val currency: String,
    val balance: BigDecimal
)

fun List<Account>.millionaire(): Client? =
    this.groupBy { it.owner }
        .asSequence()
        .firstOrNull { (_, accounts) -> accounts.isMillionaireIn("USD") }
        ?.key


val ONE_MILLION: BigDecimal = 1_000_000.toBigDecimal()

private fun List<Account>.isMillionaireIn(defaultCurrency: String): Boolean =
    this.asSequence()
        .onEach { println(it) }
        .map { convert(it.currency, defaultCurrency, it.balance) }
        .runningFold(BigDecimal.ZERO) { sum, x -> sum + x }
        .any { it >= ONE_MILLION }

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
 * Should print Client(firstName=Jeff, lastName=Bezos) or Client(firstName=Elon, lastName=Musk)
 */
fun main() {
    val jeff = Client("Jeff", "Bezos")
    val jeffSavings = Account(jeff, "AM45ONXX1234567812345678", "Savings", "USD", BigDecimal(150_000_000_000))
    val jeffDebit = Account(jeff, "AM45ONXX1234567812345678", "Current", "USD", BigDecimal(62_400_000_000))
    val elon = Client("Elon", "Musk")
    val elonSavings = Account(elon, "SP43XXXX1234567812345678", "Savings", "RON", BigDecimal(651_200_000_000))

    val accounts = listOf(elonSavings, jeffSavings, jeffDebit)

    println(accounts.millionaire())
}

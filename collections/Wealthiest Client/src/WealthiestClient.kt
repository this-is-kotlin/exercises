import java.math.BigDecimal
import kotlin.contracts.contract

data class Client(val firstName: String, val lastName: String)
data class Account(
    val owner: Client,
    val iban: String,
    val product: String,
    val currency: String,
    val balance: BigDecimal
)

val List<Account>.wealthiest: Client?
    get() = this.groupBy { it.owner }
        .mapValues { (_, accounts) -> accounts.totalWealth("EUR") }
        .maxByOrNull { (_, total) -> total }?.key

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
 * Should print Client(firstName=Jeff, lastName=Bezos)
 */
fun main() {
    val jeff = Client("Jeff", "Bezos")
    val jeffSavings = Account(jeff, "AM45ONXX1234567812345678", "Savings", "USD", BigDecimal(150_000_000_000))
    val jeffDebit = Account(jeff, "AM45ONXX1234567812345678", "Current", "USD", BigDecimal(62_400_000_000))
    val elon = Client("Elon", "Musk")
    val elonSavings = Account(elon, "SP43XXXX1234567812345678", "Savings", "RON", BigDecimal(651_200_000_000))

    val accounts = listOf(jeffDebit, elonSavings, jeffSavings)

    println(accounts.wealthiest)
}

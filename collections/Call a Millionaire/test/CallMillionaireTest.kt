import org.junit.jupiter.api.Test
import java.math.BigDecimal
import kotlin.system.measureTimeMillis
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CallMillionaireTest {

    @Test
    fun `should return null if the list is empty`() {
        assertNull(emptyList<Account>().millionaire())
    }

    @Test
    fun `should return null if there are no millionaires`() {
        val jeff = Client("Jeff", "Bezos")
        val jeffSavings = Account(jeff, "AM45ONXX1234567812345678", "Savings", "USD", "100503.3".toBigDecimal())
        val jeffDebit = Account(jeff, "AM45ONXX1234567812345678", "Current", "EUR", "700000".toBigDecimal())

        val accounts = listOf(jeffSavings, jeffDebit)

        assertNull(accounts.millionaire())
    }

    @Test
    fun `should return a millionaire`() {
        val jeff = Client("Jeff", "Bezos")
        val jeffSavings = Account(jeff, "AM45ONXX1234567812345678", "Savings", "USD", "100503.3".toBigDecimal())
        val jeffDebit = Account(jeff, "AM45ONXX1234567812345678", "Current", "EUR", "800000".toBigDecimal())

        val accounts = listOf(jeffSavings, jeffDebit)

        assertEquals(jeff, accounts.millionaire())
    }

    @Test
    fun `should be optimized`() {
        val jeff = Client("Jeff", "Bezos")
        val elon = Client("Elon", "Musk")
        val jeffsAccounts =
            generateSequence { Account(jeff, "AM45ONXX1234567812345678", "Current", "USD", "1.0".toBigDecimal()) }
                .take(1_000_000).toList()
        val elonsAccount = Account(elon, "AM45ONXX1234567812345678", "Current", "USD", "1000001".toBigDecimal())
        val accounts = listOf(elonsAccount) + jeffsAccounts

        val referenceExecutionTime = measureTimeMillis {
            accounts.millionaireReference()
        }
        val minExpectedImprovement = referenceExecutionTime - referenceExecutionTime * 0.3

        val time = measureTimeMillis {
            accounts.millionaire()
        }

        assertTrue(
            time < minExpectedImprovement - minExpectedImprovement * 0.3,
            "execution time should be under $minExpectedImprovement ms, actual: $time"
        )
    }

    fun List<Account>.millionaireReference(): Client? =
        this.groupBy { it.owner }
            .map { (client, accounts) -> client to accounts.totalWealth("EUR") }
            .find { (_, wealth) -> wealth > 1_000_000.0.toBigDecimal() }
            ?.first

    fun List<Account>.totalWealth(defaultCurrency: String): BigDecimal =
        this.map { convert(it.currency, defaultCurrency, it.balance) }
            .sum()

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

    fun List<BigDecimal>.sum(): BigDecimal = this.fold(BigDecimal.ZERO) { sum, x -> sum + x }

}
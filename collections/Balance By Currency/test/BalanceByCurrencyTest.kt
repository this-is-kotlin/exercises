import kotlin.test.Test
import kotlin.test.assertEquals

class BalanceByCurrencyTest {

    @Test
    fun `should return empty map if the list is empty`() {
        val accounts = emptyList<Account>()

        assertEquals(emptyMap(), accounts.balanceByCurrency(), "should be an empty map")
    }

    @Test
    fun `should group and sum balances by currency`() {
        val accountEur = Account("RO10XXXX1234567812345671", "Deposit", "EUR", "100.02".toBigDecimal())
        val account2 = accountEur.copy(balance = "2453.12".toBigDecimal())
        val account3 = accountEur.copy(balance = "20.20".toBigDecimal())
        val accountRon = accountEur.copy(currency = "RON", balance = "100.1".toBigDecimal())
        val account4 = accountRon.copy(balance = "-10.7".toBigDecimal())
        val accountGbp = accountRon.copy(currency = "GBP")

        val accounts = listOf(accountEur, account2, account3, accountRon, account4, accountGbp)

        assertEquals(
            mapOf(
                "EUR" to "2573.34".toBigDecimal(),
                "RON" to "89.4".toBigDecimal(),
                "GBP" to "100.1".toBigDecimal()
            ),
            accounts.balanceByCurrency()
        )
    }
}
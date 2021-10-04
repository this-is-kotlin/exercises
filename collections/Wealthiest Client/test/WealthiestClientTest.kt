import org.junit.jupiter.api.Test
import java.math.BigDecimal
import kotlin.test.assertEquals
import kotlin.test.assertNull

class WealthiestClientTest {

    @Test
    fun `should return null for empty list`() {
        assertNull(emptyList<Account>().wealthiest)
    }

    @Test
    fun `should sum account balances`() {
        val jeff = Client("Jeff", "Bezos")
        val jeffSavings = Account(jeff, "AM45ONXX1234567812345678", "Savings", "USD", BigDecimal(150_000_000_000))
        val jeffDebit = Account(jeff, "AM45ONXX1234567812345678", "Current", "USD", BigDecimal(62_400_000_000))
        val elon = Client("Elon", "Musk")
        val elonSavings = Account(elon, "SP43XXXX1234567812345678", "Savings", "USD", BigDecimal(162_800_000_000))

        val accounts = listOf(jeffDebit, elonSavings, jeffSavings)

        assertEquals(jeff, accounts.wealthiest)
    }

    @Test
    fun `should handle different currencies`() {
        val jeff = Client("Jeff", "Bezos")
        val jeffSavings = Account(jeff, "AM45ONXX1234567812345678", "Savings", "USD", BigDecimal(150_000_000_000))
        val jeffDebit = Account(jeff, "AM45ONXX1234567812345678", "Current", "USD", BigDecimal(62_400_000_000))
        val elon = Client("Elon", "Musk")
        val elonSavings = Account(elon, "SP43XXXX1234567812345678", "Savings", "RON", BigDecimal(651_200_000_000))

        val accounts = listOf(jeffDebit, elonSavings, jeffSavings)

        assertEquals(jeff, accounts.wealthiest)
    }

}
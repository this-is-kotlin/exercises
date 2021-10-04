import org.junit.jupiter.api.Test
import java.math.BigDecimal
import kotlin.test.assertEquals

class TotalWealthTest {

    @Test
    fun `should be zero for empty account list`() {
        assertEquals(BigDecimal.ZERO, emptyList<Account>().totalWealth("RON"))
        assertEquals(BigDecimal.ZERO, emptyList<Account>().totalWealth("EUR"))
        assertEquals(BigDecimal.ZERO, emptyList<Account>().totalWealth("USD"))
        assertEquals(BigDecimal.ZERO, emptyList<Account>().totalWealth("GBP"))
    }

    @Test
    fun `should convert and sum to the default currency`() {
        val savings = Account("AM45ONXX1234567812345678", "Savings", "USD", "12500".toBigDecimal())
        val current = Account("RE45ONXX1234567812345673", "Current", "EUR", "105203.05".toBigDecimal())
        val credit = Account("RE45ONXX1234567812345673", "Credit", "GBP", "-1003.09".toBigDecimal())

        val accounts = listOf(current, savings, credit)
        assertEquals("570114.72".toBigDecimal(), accounts.totalWealth("RON"))
    }

}
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertSame

class Tests {
    @Test
    fun `should retry for a number of times and succeed`() {
        val account =
            Account("NL77BBBB1234567812345678", "Current Account", "EUR", "100".toBigDecimal())

        val updatedAccount = retry(1000) {
            fail(90)
            account.deposit("3.5".toBigDecimal())
        }

        assertEquals("103.5".toBigDecimal(), updatedAccount.balance)
    }

    @Test
    fun `should retry for a number of times and fail`() {
        val account =
            Account("NL77BBBB1234567812345678", "Current Account", "EUR", "100".toBigDecimal())

        assertFailsWith<IllegalStateException> {
            retry(100) {
                fail(100)
                account.deposit("3.5".toBigDecimal())
            }
        }
    }

    @Test
    fun `should count the number of retries`() {
        val account = Account("NL77BBBB1234567812345678", "Current Account", "EUR", "100".toBigDecimal())

        var count = 0
        val updatedAccount = retry(1000) {
            count += 1
            fail(90)
            assertEquals(count, it, "Retry count mismatch")
            account
        }

        assertSame(account, updatedAccount)
    }
}
import org.junit.jupiter.api.Test
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

fun fail(probability: Int) {
    if (probability > 0 && Random.nextInt(100) <= probability)
        throw IllegalStateException("simulated side effect exception")
}

class RetryTest {
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
            retry(2) {
                fail(100)
                account.deposit("3.5".toBigDecimal())
            }
        }
    }
}



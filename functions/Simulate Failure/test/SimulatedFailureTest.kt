import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class SimulatedFailureTest {
    @Test
    fun `should never fail for 0 probability`() {
        Assertions.assertDoesNotThrow {
            (0..1000).forEach { _ ->
                fail(0)
            }
        }
    }

    @Test
    fun `should always fail for 100 probability`() {
        (0..1000).forEach { _ ->
            val exception = assertFailsWith<IllegalStateException> {
                fail(100)
            }
            assertEquals("simulated side effect exception", exception.message)
        }
    }

    @Test
    fun `should use the probability to determine number of failures`() {
        val percent = 70
        val numberOfFailures = (0..10000).map {
            try {
                fail(percent)
                0
            } catch (e: IllegalStateException) {
                1
            }
        }.sum() / 100

        val result = numberOfFailures >= percent - 5 && numberOfFailures <= percent + 5
        assertTrue(result, "Expected number of failure to be around $percent but was $numberOfFailures")
    }
}

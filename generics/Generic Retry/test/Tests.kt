import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class Tests {

    @Test
    fun `should support lists`() {
        val res = retry {
            fail(90)
            listOf(1, 2, 3)
        }

        assertEquals(listOf(1, 2, 3), res)
    }

    @Test
    fun `should rethrow exception`() {
        assertThrows<IllegalStateException> {
            retry(10) {
                fail(100)
            }
        }
    }

    @Test
    fun `should retry 10 times and send the correct count`() {
        val ns = mutableListOf<Int>()

        try {
            retry(10) {
                ns += it
                fail(100)
            }
        } catch (e: Exception) {
        }

        assertEquals((1..10).toList(), ns)
    }
}
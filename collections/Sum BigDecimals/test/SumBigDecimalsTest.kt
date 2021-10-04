import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals

class SumBigDecimalsTest {

    @Test
    fun `should return 0 for empty list`() {
        assertEquals(BigDecimal.ZERO, emptyList<BigDecimal>().sum())
    }

    @Test
    fun `should sum a non empty list`() {
        val ns = listOf("0.1".toBigDecimal(), "0.2".toBigDecimal(), "3.1".toBigDecimal(), "100.3".toBigDecimal())

        assertEquals("103.7".toBigDecimal(), ns.sum())
    }
}
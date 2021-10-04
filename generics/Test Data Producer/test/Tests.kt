import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class Tests {

    @Test
    fun `should fake an Int`() {
        val i: Int = fakeIt()

        assertNotNull(i)
    }

    @Test
    fun `should fake a String`() {
        val s: String = fakeIt()

        assertFalse(s.isBlank(), "should not be blank")
    }

    @Test
    fun `should fake a Double`() {
        val s: Double = fakeIt()

        assertNotNull(s)
    }

    @Test
    fun `a faked nullable String should sometimes be null`() {
        val hasNulls = (0..10000).map { `fakeIt?`<String>() }.any { it == null }

        assertTrue(hasNulls, "expected some null values")
    }

}
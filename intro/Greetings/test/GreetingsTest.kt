import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GreetingsTest {

    @Test
    fun `should work without parameter names`() {
        val result = createGreeting("Bob", "birthday")

        Assertions.assertEquals("Hello, Bob! Have a great birthday!", result)
    }

    @Test
    fun `should work with named parameters`() {
        val result = createGreeting(name = "Bob", occasion = "birthday")

        Assertions.assertEquals("Hello, Bob! Have a great birthday!", result)
    }

    @Test
    fun `should work with named parameters inversed`() {
        val result = createGreeting(occasion = "birthday", name = "Bob")

        Assertions.assertEquals("Hello, Bob! Have a great birthday!", result)
    }

    @Test
    fun `should work with default values`() {
        val result = createGreeting("Bob")

        Assertions.assertEquals("Hello, Bob! Have a great day!", result)
    }
}
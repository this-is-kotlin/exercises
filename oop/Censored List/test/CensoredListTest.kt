import CensoredList.Companion.censoredListOf
import kotlin.test.Test
import kotlin.test.assertEquals

class CensoredListTest {

    @Test
    fun `should censor words in a set`() {
        val words = censoredListOf(
            "a",
            "giant",
            "spaghetti",
            "monster",
            "is",
            "invading",
            "the",
            "earth"
        ) { w -> w in setOf("spaghetti", "monster") }

        assertEquals("[a, giant, ***, ***, is, invading, the, earth]", words.toString())
    }

    @Test
    fun `should censor words starting with char`() {
        val words = censoredListOf("for", "all", "the", "fine", "people") { it.startsWith("f") }

        assertEquals("[***, all, the, ***, people]", words.toString())
    }
}
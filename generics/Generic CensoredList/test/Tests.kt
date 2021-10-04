import CensoredList.Companion.censoredListOf
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Tests {

    @Test
    fun `should censor strings`() {
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
    fun `should disclose strings`() {
        val words = censoredListOf("a", "giant", "spaghetti", "monster", "is", "invading", "the", "earth") { true }

        assertEquals("[a, giant, spaghetti, monster, is, invading, the, earth]", disclose(words))
    }

    @Test
    fun `should censor numbers`() {
        val thereNoZeroesForMe = censoredListOf(-1.0, 0, 2, 1, 0.0, 5) { it.toDouble() == 0.0 }

        assertEquals("[-1.0, ***, 2, 1, ***, 5]", thereNoZeroesForMe.toString())
    }
}
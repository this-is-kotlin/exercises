import CensoredList.Companion.censoredListOf

class CensoredList private constructor(
    private val delegate: List<String>,
    private val predicate: (String) -> Boolean
) :
    List<String> by delegate {
    companion object {
        fun censoredListOf(vararg args: String, predicate: (String) -> Boolean) =
            CensoredList(listOf(*args), predicate)
    }

    override fun toString(): String =
        delegate.map { if (predicate(it)) "***" else it }.toString()
}

/**
 * Example usage.
 *
 * Should print: `[a, giant, ***, ***, is, invading, the, earth]`
 *
 */
fun main() {
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
    println(words)
}

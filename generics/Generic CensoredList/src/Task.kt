import CensoredList.Companion.censoredListOf

class CensoredList<out T> private constructor(
    private val delegate: List<T>,
    private val predicate: (T) -> Boolean
) :
    List<T> by delegate {
    companion object {
        fun <T> censoredListOf(vararg args: T, predicate: (T) -> Boolean) =
            CensoredList(listOf(*args), predicate)
    }

    override fun toString(): String =
        delegate.map { if (predicate(it)) "***" else it.toString() }.toString()
}

fun disclose(censoredList: CensoredList<*>): String =
    censoredList.joinToString(separator = ", ", prefix = "[", postfix = "]")

/**
 * Example usage.
 *
 * Should print:
 *
 * `[a, giant, ***, ***, is, invading, the, earth]`
 *
 * `[-1, ***, 2, 1, ***, 5]`

 * `[-1, 0, 2, 1, 0, 5]`
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

    val youGetNoZeroesFromMe = censoredListOf(-1, 0, 2, 1, 0, 5) { it == 0 }
    println(youGetNoZeroesFromMe)

    println(disclose(youGetNoZeroesFromMe))
}

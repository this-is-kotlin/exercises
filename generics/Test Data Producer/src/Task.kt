import kotlin.random.Random

sealed interface TestDataProducer<out T> {
    fun fakeIt(): T
}

object IntProducer : TestDataProducer<Int> {
    override fun fakeIt(): Int = Random.nextInt()
}

object DoubleProducer : TestDataProducer<Double> {
    override fun fakeIt(): Double = Random.nextDouble()
}

object StringProducer : TestDataProducer<String> {
    private const val CHARS = "abcdefghijklmnopqrstuvwxyz0123456789"

    override fun fakeIt(): String = generateSequence { Random.nextInt(CHARS.length) }
        .map(CHARS::get)
        .take(32)
        .joinToString("")
}

object NullProducer : TestDataProducer<Nothing?> {
    override fun fakeIt(): Nothing? = null
}

inline fun <reified T : Any> fakeIt(): T = faker<T>().fakeIt()

inline fun <reified T : Any> `fakeIt?`(): T? = nullable(faker<T>()).fakeIt()

inline fun <reified T : Any> faker(): TestDataProducer<T> = when (T::class) {
    Int::class -> IntProducer
    Double::class -> DoubleProducer
    String::class -> StringProducer
    else -> throw IllegalArgumentException("Unsupported type")
} as TestDataProducer<T>

fun <T : Any> nullable(faker: TestDataProducer<T>): TestDataProducer<T?> =
    if (Random.nextInt(100) > 10) faker else NullProducer

/**
 * Example usage.
 *
 * Could print:
 *
 * -763257890
 *
 * 7in6h1nf5jxemyapypq7kuvyt8umhkuc
 *
 * null
 *
 * 63w5cxhrgxek0b7npg5j7y9yp7bklobs
 *
 */
fun main() {
    val n: Int = fakeIt()
    println(n)

    val s: String = fakeIt()
    println(s)

    val dn: Double? = `fakeIt?`()
    println(dn)

    val sn: String? = `fakeIt?`()
    println(sn)
}

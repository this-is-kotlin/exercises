import kotlin.random.Random

fun <T> retry(times: Int = 0, f: (Int) -> T): T = run {
    fun retry(count: Int): T =
        if (times == count) f(count)
        else try {
            f(count)
        } catch (e: Exception) {
            retry(count + 1)
        }

    retry(1)
}

fun fail(probability: Int) {
    if (probability > 0 && Random.nextInt(100) <= probability)
        throw IllegalStateException("simulated side effect exception")
}

/**
 * Example usage
 *
 * Example output:
 *
 * try no 1
 *
 * try no 2
 *
 * try no 3
 *
 * the first result is 10
 *
 * try no 1
 *
 * the second result is success
 */
fun main() {
    val n = retry {
        println("try no $it")
        fail(70)
        10
    }
    println("the first result is $n")

    val s: String = retry {
        println("try no $it")
        fail(50)
        "success"
    }
    println("the second result is $s")
}

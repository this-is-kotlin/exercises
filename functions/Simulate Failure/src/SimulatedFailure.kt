// type your solution here
import kotlin.random.Random

fun fail(probability: Int) {
    if (probability > 0 && Random.nextInt(100) <= probability)
        throw IllegalStateException("simulated side effect exception")
}

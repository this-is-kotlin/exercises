// type your solution here
import kotlin.random.Random

fun fail(probability: Int) = check(Random.nextInt(1, 100) > probability) {
    "simulated side effect exception"
}

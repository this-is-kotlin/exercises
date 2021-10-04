import java.math.BigDecimal

fun List<BigDecimal>.sum(): BigDecimal = this.fold(BigDecimal.ZERO) { sum, x -> sum + x }

/**
 * Example usage
 *
 * Should print 6.0
 */
fun main() {
    val ns = listOf("0.1".toBigDecimal(), "0.9".toBigDecimal(), "5.0".toBigDecimal())

    println(ns.sum())
}

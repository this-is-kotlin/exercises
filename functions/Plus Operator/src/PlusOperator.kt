class Fraction(numerator: Int, denominator: Int) {
    val numerator: Int
    val denominator: Int

    init {
        fun gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)
        val gcd = gcd(numerator, denominator)
        this.numerator = numerator / gcd
        this.denominator = denominator / gcd
    }

    //Fraction + Fraction
    operator fun plus(other: Fraction): Fraction = Fraction(
        numerator * other.denominator + other.numerator * denominator,
        denominator * other.denominator
    )

    override fun toString(): String = "$numerator/$denominator"

}

//Int + Fraction
operator fun Int.plus(fraction: Fraction) = Fraction(this, 1) + fraction

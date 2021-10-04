import TermType.*
import TodayProvider.today
import java.math.BigDecimal
import java.time.LocalDate
import java.time.Period


sealed interface Account {
    val iban: String
    val balance: BigDecimal
}

data class SavingsAccount(
    override val iban: String,
    override val balance: BigDecimal,
    val interest: BigDecimal
) : Account

data class CreditAccount(
    override val iban: String,
    override val balance: BigDecimal,
    val creditLimit: BigDecimal,
    val interest: BigDecimal
) : Account

data class CurrentAccount(
    override val iban: String,
    override val balance: BigDecimal
) : Account

object TechnicalAccount : Account {
    override val iban: String = "RO99TECH1234567812345678"
    override var balance: BigDecimal = BigDecimal.ZERO
}

enum class TermType(val months: Int) {
    MONTH(1), QUARTER(3), SEMESTER(6), YEAR(12)
}

data class TermDeposit private constructor(
    override val iban: String,
    override val balance: BigDecimal,
    val term: LocalDate,
    val interest: BigDecimal
) : Account {
    companion object {
        private fun TermType.toTerm() = today() + Period.ofMonths(this.months)
    }

    constructor(iban: String, balance: BigDecimal, termType: TermType, interest: BigDecimal) : this(
        iban,
        balance,
        termType.toTerm(),
        interest
    )
}

fun Account.applyInterest(): Account = when (this) {
    is SavingsAccount -> copy(balance = balance + balance * interest / 100.toBigDecimal())
    is CreditAccount ->
        if (balance >= BigDecimal.ZERO) this
        else copy(balance = balance + balance * interest / 100.toBigDecimal())
    is TermDeposit ->
        if (today() < term) this
        else CurrentAccount(iban, balance + balance * interest / 100.toBigDecimal())
    is CurrentAccount, is TechnicalAccount -> this
}

object TodayProvider {
    fun today(): LocalDate = LocalDate.now()
}

/**
 * Example usage
 *
 * Should print:
 *
 * true
 *
 * 100003.000
 */
fun main() {
    run {
        val account =
            TermDeposit("RO88TERM12345678123445678", 100_000.toBigDecimal(), SEMESTER, "5.2".toBigDecimal())
        val withInterest = account.applyInterest()
        println(account == withInterest)
    }

    run {
        val account = SavingsAccount("RO88TERM12345678123445678", 100_000.toBigDecimal(), "0.003".toBigDecimal())
        val withInterest = account.applyInterest()
        println(withInterest.balance)
    }
}

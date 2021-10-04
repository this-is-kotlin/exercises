import TermType.*
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.Period
import kotlin.test.assertEquals
import kotlin.test.assertSame

class ApplyInterestTest {

    @BeforeEach
    fun setup() {
        unmockkAll()
    }

    @Test
    fun `current account should be unchanged`() {
        val account = CurrentAccount("RO88TERM12345678123445678", 100_000.toBigDecimal())

        val withInterest = account.applyInterest()

        assertEquals(account, withInterest)
    }

    @Test
    fun `technical account should be unchanged`() {
        val withInterest = TechnicalAccount.applyInterest()

        assertSame(TechnicalAccount, withInterest)
    }

    @Test
    fun `savings account should receive interest`() {
        val account = SavingsAccount("RO88TERM12345678123445678", 100_000.toBigDecimal(), "0.002".toBigDecimal())

        val withInterest = account.applyInterest()

        assertEquals("100002.000".toBigDecimal(), withInterest.balance)
    }

    @Test
    fun `credit account should apply interest if credit is used`() {
        val account = CreditAccount(
            "RO88TERM12345678123445678",
            (-1_000).toBigDecimal(),
            10_000.toBigDecimal(),
            "0.01".toBigDecimal()
        )

        val withInterest = account.applyInterest()

        assertEquals("-1000.10".toBigDecimal(), withInterest.balance)
    }

    @Test
    fun `credit account should not apply interest if credit is not used`() {
        val account = CreditAccount(
            "RO88TERM12345678123445678",
            1_000.toBigDecimal(),
            10_000.toBigDecimal(),
            "0.01".toBigDecimal()
        )

        val withInterest = account.applyInterest()

        assertEquals(account, withInterest)
    }

    @Test
    fun `term deposit should be unchanged before month term`() {
        val account = TermDeposit("RO88TERM12345678123445678", 100_000.toBigDecimal(), SEMESTER, "5.2".toBigDecimal())
        wait(Period.ofDays(25))

        val withInterest = account.applyInterest()

        assertEquals(account, withInterest)
    }

    @Test
    fun `term deposit should be converted to current account after month term`() {
        val account = TermDeposit("RO88TERM12345678123445678", 100_000.toBigDecimal(), MONTH, "2.2".toBigDecimal())

        wait(Period.ofMonths(2))

        val withInterest = account.applyInterest()

        assertEquals(CurrentAccount("RO88TERM12345678123445678", "102200.0".toBigDecimal()), withInterest)
    }

    @Test
    fun `term deposit should be unchanged before quarter term`() {
        val account = TermDeposit("RO88TERM12345678123445678", 100_000.toBigDecimal(), QUARTER, "5.2".toBigDecimal())

        wait(Period.ofMonths(2))

        val withInterest = account.applyInterest()

        assertEquals(account, withInterest)
    }

    @Test
    fun `term deposit should be converted to current account after quarter term`() {
        val account = TermDeposit("RO88TERM12345678123445678", 100_000.toBigDecimal(), QUARTER, "5.2".toBigDecimal())

        wait(Period.ofMonths(4))

        val withInterest = account.applyInterest()

        assertEquals(CurrentAccount("RO88TERM12345678123445678", "105200.0".toBigDecimal()), withInterest)
    }

    @Test
    fun `term deposit should be unchanged before semester term`() {
        val account = TermDeposit("RO88TERM12345678123445678", 100_000.toBigDecimal(), SEMESTER, "5.2".toBigDecimal())

        wait(Period.ofMonths(5))

        val withInterest = account.applyInterest()

        assertEquals(account, withInterest)
    }

    @Test
    fun `term deposit should be converted to current account after semester term`() {
        val account = TermDeposit("RO88TERM12345678123445678", 100_000.toBigDecimal(), SEMESTER, "5.2".toBigDecimal())

        wait(Period.ofMonths(7))

        val withInterest = account.applyInterest()

        assertEquals(CurrentAccount("RO88TERM12345678123445678", "105200.0".toBigDecimal()), withInterest)
    }

    @Test
    fun `term deposit should be unchanged before year term`() {
        val account = TermDeposit("RO88TERM12345678123445678", 100_000.toBigDecimal(), YEAR, "5.2".toBigDecimal())

        wait(Period.ofMonths(11))

        val withInterest = account.applyInterest()

        assertEquals(account, withInterest)
    }

    @Test
    fun `term deposit should be converted to current account after year term`() {
        val account = TermDeposit("RO88TERM12345678123445678", 100_000.toBigDecimal(), YEAR, "5.2".toBigDecimal())

        wait(Period.ofMonths(13))

        val withInterest = account.applyInterest()

        assertEquals(CurrentAccount("RO88TERM12345678123445678", "105200.0".toBigDecimal()), withInterest)
    }

    private fun wait(period: Period) {
        mockkObject(TodayProvider)
        every { TodayProvider.today() } returns LocalDate.now() + period
    }

}
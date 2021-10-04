import java.math.BigDecimal

fun deposit(account: Account, amount: BigDecimal): Account = with(account) {
    Account(
        iban,
        product,
        currency,
        balance = balance + amount
    )
}

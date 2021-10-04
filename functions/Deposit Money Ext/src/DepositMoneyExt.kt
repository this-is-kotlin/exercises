import java.math.BigDecimal

fun Account.deposit(amount: BigDecimal): Account = Account(
    iban,
    product,
    currency,
    balance = balance + amount
)

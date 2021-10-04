//add your implementation here
fun retry(times: Int = 0, f: (Int) -> Account): Account = run {
    fun retry(count: Int): Account =
        if (times == count) f(count)
        else try {
            f(count)
        } catch (e: Exception) {
            retry(count + 1)
        }
    retry(1)
}

fun main() {
    val account =
        Account("NL77BBBB1234567812345678", "Current Account", "EUR", "100".toBigDecimal())

    val updatedAccount = retry(2) {
        println("f called $it time(s)")
        fail(50)
        account.deposit("3.5".toBigDecimal())
    }

    println(updatedAccount.balance)
}
class Account(val iban: String, val product: String, val currency: String, val balance: Double) {
    init {
        require(currency.length == 3) { "Currency should be a 3 chars code" }
        require(iban.matches(Regex("[A-Z]{2}\\d{2}[A-Z]{4}\\d{16}"))) { "Invalid IBAN" }
    }
}
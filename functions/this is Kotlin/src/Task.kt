interface ProgrammingLanguage
object Kotlin : ProgrammingLanguage

fun isItKotlin(language: ProgrammingLanguage): Boolean =
    with(language) { this is Kotlin }

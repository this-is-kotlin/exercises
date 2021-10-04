import kotlin.reflect.KProperty

class User(val name: String, password: String) {
    private val mask: Mask = Mask(password)
    val password: String by mask

    internal val actualPassword
        get() = mask.value
}

class Mask(val value: String) {
    operator fun getValue(auditedClass: Any, property: KProperty<*>): String = "***"
}

/**
 * Example usage.
 *
 * Should print:
 *
 * `***`
 *
 * `qwerty1234`
 */
fun main() {
    val user = User("parzival", "qwerty1234")
    println(user.password)
    println(user.actualPassword)
}

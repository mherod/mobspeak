@file:Suppress("unused")

package co.herod.kotlin

fun log(message: Any?) {
    if (verboseLogging) {
        println(message)
    }
}

val verboseLogging: Boolean by lazy {
    System.getenv("VERBOSE_LOGGING") == "true"
}
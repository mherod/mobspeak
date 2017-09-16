package co.herod.adbwrapper

class AdbError(private val errorMessage: String) : RuntimeException() {
    override fun toString(): String = "AdbError{errorMessage='$errorMessage'}"
}

package co.herod.adbwrapper.exceptions

class AdbError(private val errorMessage: String) : RuntimeException() {
    override fun toString(): String = "AdbError{errorMessage='$errorMessage'}"
}

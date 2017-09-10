package co.herod.adbwrapper

/**
 * Created by matthewherod on 04/09/2017.
 */

class AdbError(private val errorMessage: String) : RuntimeException() {
    override fun toString(): String = "AdbError{errorMessage='$errorMessage'}"
}

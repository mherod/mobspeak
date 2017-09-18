package co.herod.adbwrapper.util

object PropHelper {

    fun hasPositiveValue(entry: Map.Entry<String, String>): Boolean =
            entry.value.sanitised().hasPositiveValue()

    private fun String.hasPositiveValue(): Boolean =
            this == "on" || this == "1" || "true" in this

    private fun String.sanitised(): String = trim().toLowerCase()
}

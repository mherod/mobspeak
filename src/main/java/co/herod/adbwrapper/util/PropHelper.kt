package co.herod.adbwrapper.util

object PropHelper {

    fun hasPositiveValue(entry: Map.Entry<String, String>): Boolean {
        return entry.value.hasPositiveValue()
    }

    private fun String.hasPositiveValue(): Boolean {

        val value = trim { it <= ' ' }.toLowerCase()

        return value == "on" || value == "1" || value == "true"
    }

    fun isValidProperty(a: Map.Entry<String, String>): Boolean = a.key.contains(" ")

    fun isKey(entry: Map.Entry<String, String>, key: String): Boolean = entry.key == key

}

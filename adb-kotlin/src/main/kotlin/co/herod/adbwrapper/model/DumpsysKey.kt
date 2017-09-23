@file:Suppress("MemberVisibilityCanPrivate")

package co.herod.adbwrapper.model

enum class DumpsysKey(val key: String) : CharSequence {
    DISPLAY("display"),
    INPUT_METHOD("input_method"),
    ACTIVITY("activity"),
    WINDOW("window"),
    PACKAGE("package");

    override val length: Int
        get() = key.length

    override fun get(index: Int): Char =
            key[index]

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence =
            subSequence(startIndex, endIndex)

    override fun toString(): String {
        return key
    }
}
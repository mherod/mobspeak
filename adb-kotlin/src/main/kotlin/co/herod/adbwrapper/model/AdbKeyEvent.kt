@file:Suppress("MemberVisibilityCanPrivate")

package co.herod.adbwrapper.model

enum class AdbKeyEvent(val keyCode: Int) : CharSequence {
    KEY_EVENT_HOME(3),
    KEY_EVENT_BACK(4),
    KEY_EVENT_POWER(26),
    KEY_EVENT_BACKSPACE(67),
    KEY_EVENT_ESCAPE(111);

    private val keyCodeString: String by lazy { "$keyCode" }

    override val length: Int
        get() = keyCodeString.length

    override fun get(index: Int): Char =
            keyCodeString[index]

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence =
            keyCodeString.subSequence(startIndex, endIndex)

    override fun toString(): String =
            keyCodeString
}
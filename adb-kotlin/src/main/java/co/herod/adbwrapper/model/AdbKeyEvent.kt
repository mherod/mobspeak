package co.herod.adbwrapper.model

enum class AdbKeyEvent(private val keyCode: Int) {
    KEY_EVENT_HOME(3),
    KEY_EVENT_BACK(4),
    KEY_EVENT_POWER(26),
    KEY_EVENT_BACKSPACE(67);

    override fun toString(): String{
        return "$keyCode"
    }
}
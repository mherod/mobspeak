package co.herod.adbwrapper.model

enum class InputType(private val s: String) {
    TAP("tap"),
    TEXT("text"),
    KEY_EVENT("keyevent"),
    SWIPE("swipe");

    override fun toString(): String = s
}
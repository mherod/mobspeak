package co.herod.adbwrapper.model

enum class DumpsysKey(private val key: String) {
    PROPS_DISPLAY("display"),
    PROPS_INPUT_METHOD("input_method"),
    ACTIVITY("activity"),
    WINDOW("window"),
    PACKAGE("package");

    override fun toString(): String{
        return key
    }
}
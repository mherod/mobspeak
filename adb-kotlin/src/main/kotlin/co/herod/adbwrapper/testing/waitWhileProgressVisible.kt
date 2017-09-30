package co.herod.adbwrapper.testing

@Deprecated(
        replaceWith = ReplaceWith("whileUiNodeExists { \"Progress\" in it.uiClass }"),
        message = "Use whileUiNodeExists"
)
fun AdbDeviceTestHelper.waitWhileProgressVisible() = whileUiNodeExists { "Progress" in it.uiClass }
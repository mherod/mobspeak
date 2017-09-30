package co.herod.adbwrapper.ui.screenshots

import co.herod.adbwrapper.ScreenshotHelper
import co.herod.adbwrapper.model.UiNode

fun UiNode.capture() {
    adbDevice?.let { ScreenshotHelper.screenshot(it, bounds) }
}
/*
 * Copyright (c) 2018. Herod
 */

@file:Suppress("unused")

package co.herod.adbwrapper.ui.screenshots

import co.herod.adbwrapper.ScreenshotHelper
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.UiNode

fun UiNode.capture() {
    adbDevice?.let { ScreenshotHelper.screenshot(it, bounds) }
}

fun AdbDevice.capture() {
    ScreenshotHelper.screenshot(this, windowBounds)
}
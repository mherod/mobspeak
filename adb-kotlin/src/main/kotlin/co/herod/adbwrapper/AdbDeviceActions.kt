package co.herod.adbwrapper

import co.herod.adbwrapper.AdbProcesses.tap
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.UiBounds
import co.herod.adbwrapper.model.UiNode
import co.herod.kotlin.ext.blocking
import java.util.concurrent.TimeUnit

fun AdbDevice.tap(uiNode: UiNode): String =
        tap(uiNode.bounds)

fun AdbDevice.tap(c: UiBounds): String =
        tap(c.centreX, c.centreY)

@JvmOverloads
fun AdbDevice.tap(centreX: Int = 0, centreY: Int = 0): String =
        tap(this, centreX, centreY)
                .blocking(2, TimeUnit.SECONDS)

@JvmOverloads
fun AdbDevice.swipe(x1: Int, y1: Int, x2: Int, y2: Int, speed: Int = 500): String =
        AdbProcesses.swipe(this, x1, y1, x2, y2, speed)
                .blocking(2, TimeUnit.SECONDS)

@JvmOverloads
fun AdbDevice.typeText(inputText: String = ""): String =
        AdbProcesses.inputText(this, inputText)
                .blocking(2, TimeUnit.SECONDS)

package co.herod.adbwrapper.device

import co.herod.adbwrapper.AdbCommand
import co.herod.adbwrapper.S.Companion.INPUT
import co.herod.adbwrapper.S.Companion.SHELL
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.InputType
import co.herod.adbwrapper.model.UiBounds
import co.herod.adbwrapper.model.UiNode
import co.herod.adbwrapper.observable
import co.herod.kotlin.ext.blocking
import java.util.concurrent.TimeUnit

@JvmOverloads
fun AdbDevice.tap(centreX: Int = 0, centreY: Int = 0): String =
        AdbCommand.Builder()
                .setDevice(this)
                .setCommand("$SHELL $INPUT ${InputType.TAP} $centreX $centreY".trim())
                .observable()
                .last("") // succeeds silently
                .blocking(5, TimeUnit.SECONDS)

fun AdbDevice.tap(c: UiBounds): String =
        tap(c.centreX, c.centreY)

fun AdbDevice.tap(uiNode: UiNode): String =
        tap(uiNode.bounds)
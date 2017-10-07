package co.herod.adbwrapper.device.input

import co.herod.adbwrapper.AdbCommand
import co.herod.adbwrapper.S.Companion.INPUT
import co.herod.adbwrapper.S.Companion.SHELL
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.InputType
import co.herod.adbwrapper.observable
import co.herod.kotlin.ext.blocking
import java.util.concurrent.TimeUnit

@JvmOverloads
fun AdbDevice.swipe(
        x1: Int,
        y1: Int,
        x2: Int,
        y2: Int,
        speed: Int = 500
): String =
        AdbCommand.Builder()
                .setDevice(this)
                .setCommand(cmdStringSwipe(x1, y1, x2, y2, speed))
                .observable()
                .last("") // succeeds silently
                .blocking(5, TimeUnit.SECONDS)

private fun cmdStringSwipe(
        x1: Int,
        y1: Int,
        x2: Int,
        y2: Int,
        speed: Int
) =
        "$SHELL $INPUT ${InputType.SWIPE} $x1 $y1 $x2 $y2 $speed".trim()
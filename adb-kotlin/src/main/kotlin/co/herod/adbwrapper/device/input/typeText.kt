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
fun AdbDevice.typeText(inputText: String = ""): String =
        AdbCommand.Builder()
                .setDevice(this)
                .setCommand(cmdStringInputTest(inputText))
                .observable()
                .last("") // succeeds silently
                .blocking(5, TimeUnit.SECONDS)

private fun cmdStringInputTest(inputText: String) = "$SHELL $INPUT ${InputType.TEXT} $inputText".trim()
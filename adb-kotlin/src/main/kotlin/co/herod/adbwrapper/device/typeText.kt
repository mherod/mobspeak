package co.herod.adbwrapper.device

import co.herod.adbwrapper.AdbCommand
import co.herod.adbwrapper.INPUT
import co.herod.adbwrapper.SHELL
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.InputType
import co.herod.adbwrapper.observable
import co.herod.kotlin.ext.blocking
import java.util.concurrent.TimeUnit

@JvmOverloads
fun AdbDevice.typeText(inputText: String = ""): String =
        AdbCommand.Builder()
                .setDevice(this)
                .setCommand("${SHELL} ${INPUT} ${InputType.TEXT} $inputText".trim())
                .observable()
                .last("") // succeeds silently
                .blocking(5, TimeUnit.SECONDS)